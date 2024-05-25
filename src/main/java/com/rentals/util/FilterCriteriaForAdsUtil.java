package com.rentals.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.rentals.entity.Advertisement;
import com.rentals.object.FilterDTO;

public class FilterCriteriaForAdsUtil {

	public static Specification<Advertisement> columnEqual(List<FilterDTO> filterDTOList) {

		return new Specification<Advertisement>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				filterDTOList.forEach(filter -> {

					Predicate predicate;
					switch (filter.getOperationType()) {
					default:
					case EQUALS:
						Object objColumnValue ;
						String strColumnValue = String.valueOf(filter.getColumnValue()) ;
						if(strColumnValue.equalsIgnoreCase("true") || strColumnValue.equalsIgnoreCase("false")){
							Boolean result = Boolean.parseBoolean(strColumnValue);
							objColumnValue = result ;
						}
						else {
							objColumnValue = strColumnValue ;
						}
						predicate = criteriaBuilder.equal(root.get(filter.getColumnName()), objColumnValue );
						break;

					case BIGGER:
						predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getColumnName()),
								filter.getColumnValue().toString());
						break;

					case SMALLER:
						predicate = criteriaBuilder.lessThanOrEqualTo(root.get(filter.getColumnName()),
								filter.getColumnValue().toString());
						break;
					}

					predicates.add(predicate);

				});

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

			}

		};
	}
}
