package eu.palantir.portal.service.utils;

import eu.palantir.portal.dto.options.ToOptions;
import eu.palantir.portal.dto.options.ToOptionsSort;
import eu.palantir.portal.dto.options.ToSort;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtils<T> {

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

    public Predicate getAndPredicate(CriteriaBuilder cb, List<Predicate> predicates) {
        return (cb.and(predicates.toArray(new Predicate[predicates.size()])));
    }

    public void setQueryPage(Query query, ToOptions options) {
        if (options.size != -1) {
            query.setMaxResults(options.size);
            query.setFirstResult(options.index * options.size);
        }
    }

}
