package eu.ubitech.palantir.portal.service.utils;

import org.eclipse.microprofile.opentracing.Traced;

import eu.ubitech.palantir.portal.dto.options.ToOptions;
import eu.ubitech.palantir.portal.dto.options.ToOptionsSort;
import eu.ubitech.palantir.portal.dto.options.ToSort;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtils<T> {

  @Traced
  public List<Order> getSort(CriteriaBuilder cb, Root<T> root, ToOptionsSort sort) {
    List<Order> orders = new ArrayList<>();
    if (sort != null) {
      for (ToSort column : sort.list) {
        if (column.order.equals("desc")) {
          orders.add(cb.desc(root.get(column.field)));
        } else {
          orders.add(cb.asc(root.get(column.field)));
        }
      }
    }
    return orders;
  }

  @Traced
  public Predicate getAndPredicate(CriteriaBuilder cb, List<Predicate> predicates) {
    return (cb.and(predicates.toArray(new Predicate[predicates.size()])));
  }

  @Traced
  public void setQueryPage(Query query, ToOptions options) {
    if (options.size != -1) {
      query.setMaxResults(options.size);
      query.setFirstResult(options.index * options.size);
    }
  }

}
