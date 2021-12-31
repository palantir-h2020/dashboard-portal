package eu.ubitech.palantir.portal.service;

import eu.ubitech.palantir.portal.model.Person_;
import eu.ubitech.palantir.portal.dto.EnumDto;
import eu.ubitech.palantir.portal.dto.PersonDto;
import eu.ubitech.palantir.portal.dto.mappers.PersonMapper;
import eu.ubitech.palantir.portal.dto.options.ToSort;
import eu.ubitech.palantir.portal.dto.options.extensions.ToOptionsPerson;
import eu.ubitech.palantir.portal.dto.page.Page;
import eu.ubitech.palantir.portal.dto.page.PageList;
import eu.ubitech.palantir.portal.exceptions.NotFoundAlertException;
import eu.ubitech.palantir.portal.model.Gender;
import eu.ubitech.palantir.portal.model.Person;
import eu.ubitech.palantir.portal.service.utils.ServiceUtils;

import org.eclipse.microprofile.opentracing.Traced;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.dsl.CompositeSortComponentsStep;
import org.hibernate.search.mapper.orm.Search;

@ApplicationScoped
public class PersonService {

  @Inject
  EntityManager em;

  @Inject
  PersonMapper personMapper;

  private static final Logger logger = Logger.getLogger(PersonService.class.getName());

  @Transactional
  @Traced
  public PersonDto getPerson(Long id) {
    return personMapper.toPersonDto(
        (Person) Person.findByIdOptional(id).orElseThrow(() -> new NotFoundAlertException(Person.class.getName())));
  }

  @Transactional
  @Traced
  public Page<PersonDto> getPersons(ToOptionsPerson options) {
    final long startTime = System.nanoTime();
    CriteriaBuilder cb = em.getCriteriaBuilder();
    // Results
    CriteriaQuery<Person> lcq = cb.createQuery(Person.class);
    Root<Person> root = lcq.from(Person.class);

    // Filter
    List<Predicate> predicates = new ArrayList<Predicate>();
    if (options.lastName != null) {
      predicates.add(cb.equal(root.get(Person_.lastName), options.lastName));
    }

    if (options.gender != null) {
      CriteriaBuilder.In<Gender> inClause = cb.in(root.get(Person_.gender));
      for (String item : options.gender.list) {
        inClause.value(Gender.valueOf(item));
      }
      predicates.add(inClause);
    }

    if (options.birthDateFrom != null) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(Person_.birthDate), options.birthDateFrom));
    }

    if (options.birthDateTo != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get(Person_.birthDate), options.birthDateTo));
    }

    ServiceUtils<Person> serviceUtils = new ServiceUtils<>();
    Predicate predicate = serviceUtils.getAndPredicate(cb, predicates);
    // End Filter

    // Order
    List<Order> orders = serviceUtils.getSort(cb, root, options.sort);
    // Default ordering
    orders.add(cb.asc(root.get(Person_.lastName)));
    // End Order

    lcq.select(root).where(predicate).orderBy(orders);
    Query query = em.createQuery(lcq);

    final List<Person> results = query.setFirstResult(options.size * options.index).setMaxResults(options.size)
        .getResultList();
    // Total count results
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    cq.select(cb.count(cq.from(Person.class)));
    cq.where(predicate);
    Long total = em.createQuery(cq).getSingleResult();
    // End Total count results
    long endTime = System.nanoTime();
    long timeElapsed = endTime - startTime;
    logger.info("Time required to fetch page from RDBMS in milliseconds: " + timeElapsed / 1000000);
    return new Page<>(personMapper.toPersonDto(results), total, options.index, options.size);
  }

  private BooleanPredicateClausesStep buildElasticExpression(SearchPredicateFactory f, ToOptionsPerson options) {
    BooleanPredicateClausesStep retbool = f.bool();
    // filter-item-1
    if ((options.lastName) != null && !options.lastName.trim().equals("")) {
      retbool.must(f.match().field("lastName").matching(options.lastName));
    }

    if (options.birthDateFrom != null) {
      retbool.must(f.range().fields("birthDate").greaterThan(options.birthDateFrom));
    }
    if (options.birthDateTo != null) {
      retbool.must(f.range().fields("birthDate").lessThan(options.birthDateTo));
    }
    if (options.gender != null && !options.gender.list.isEmpty()) {
      BooleanPredicateClausesStep<?> orbool = f.bool();
      for (String str : options.gender.list) {
        // System.out.println("str: " + str);
        orbool.should(f.match().fields("gender").matching(Gender.valueOf(str)));
      }
      retbool.must(orbool);
    }
    return retbool;
  }

  @Transactional
  @Traced
  public Page<PersonDto> getPersonsFromElastic(ToOptionsPerson options) {
    final long startTime = System.nanoTime();
    List<PersonDto> results = new ArrayList<>();
    long total = 0;
    // 1st Step Count total
    SearchResult<Person> res = Search.session(em).search(Person.class).where(f -> {
      return buildElasticExpression(f, options);
    }).fetch(0);
    total = res.total().hitCount();
    // logger.info("Total results: " + total);
    // 2nd Step fetch page
    results = Search.session(em).search(Person.class)
        .select(f -> f.composite(f.field("id", Long.class), f.field("lastName", String.class),
            f.field("firstName", String.class), f.field("gender", Gender.class), f.field("birthDate", LocalDate.class)))
        .where(f -> {
          return buildElasticExpression(f, options);
        }).sort(f -> {
          // build the expression
          CompositeSortComponentsStep<?> composite = f.composite();
          // SortFinalStep<?> finalstep;
          if (options.sort != null && options.sort.list != null && options.sort.list.size() > 0) {
            for (ToSort sortoption : options.sort.list) {
              String field = sortoption.field;
              String order = sortoption.order;
              if (order.equalsIgnoreCase("desc")) {
                composite.add(f.field(field).desc());
              } else {
                composite.add(f.field(field).asc());
              }
            }
          } else {
            // default sort if no sort-option is provided
            composite.add(f.field("lastName").asc());
          }
          return composite;
        }).fetchHits(options.index * options.size, options.size).stream().map(s -> {
          PersonDto dto = new PersonDto();
          dto.setId((Long) s.get(0));
          dto.setLastName((String) s.get(1));
          dto.setFirstName((String) s.get(2));
          Gender gender = (Gender) s.get(3);
          // System.out.println("gender: " + gender);
          String gengerstr = (gender != null) ? gender.getDescription() : "";
          dto.setGender(new EnumDto(gengerstr, gengerstr));
          dto.setBirthDate((LocalDate) s.get(4));
          return dto;
        }).collect(Collectors.toList());
    long endTime = System.nanoTime();
    long timeElapsed = endTime - startTime;
    logger.info("Time required to fetch page from elastic in milliseconds: " + timeElapsed / 1000000);
    return new Page<>(results, total, options.index, options.size);
  }

  @Transactional
  @Traced
  public PersonDto persistOrUpdatePerson(PersonDto personDto) {
    Person person = personMapper.toPerson(personDto);
    if (person.getId() != null) {
      Person oldPerson = (Person) Person.findByIdOptional(person.getId())
          .orElseThrow(() -> new NotFoundAlertException(Person.class.getName()));
      oldPerson.setFirstName(person.getFirstName());
      oldPerson.setLastName(person.getLastName());
      oldPerson.setGender(person.getGender());
      oldPerson.setBirthDate(person.getBirthDate());
      oldPerson.setUpdatedTimestamp(Instant.now());
      oldPerson.setDescription(person.getDescription());
    } else {
      person.persist();
    }
    return personMapper.toPersonDto(person);
  }

  @Transactional
  @Traced
  public void deletePerson(Long id) {
    Person.findByIdOptional(id).ifPresent(person -> {
      person.delete();
    });
  }

  @Transactional
  @Traced
  public PageList<PersonDto> searchPersons(String lastName) {
    // TODO implement
    List<Person> results = Person.findAll().list();
    return new PageList<>(personMapper.toPersonDto(results), results.size());
  }

  @Transactional
  @Traced
  public PageList<EnumDto> getGenders() {
    List<EnumDto> genders = Stream.of(Gender.values()).map(s -> (new EnumDto(s.name(), s.getDescription())))
        .collect(Collectors.toList()); // TODO replace with enum mapper
    return new PageList<>(genders, genders.size());
  }

}
