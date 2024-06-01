package com.rentals.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.rentals.entity.Advertisement;
import com.rentals.model.FilterDTO;

public class FilterCriteriaForAdsUtil {

	public static Specification<Advertisement> columnEqual(List<FilterDTO> filterDTOList) {

		return new Specification<Advertisement>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				filterDTOList.forEach(filter -> {
					String columnName = filter.getColumnName();
					String columnValue = String.valueOf(filter.getColumnValue());
					Predicate predicate;

					switch (filter.getOperationType()) {
					default:
					case EQUALS:
						if (RentalsUtil.isBoolean(columnValue)) {
							Boolean result = Boolean.parseBoolean(columnValue);
							predicate = criteriaBuilder.equal(root.get(columnName), result);
						} 
						
						else {
							predicate = criteriaBuilder.equal(root.get(columnName), columnValue);
						}
					
						break;

					case BIGGER:
						Date date = RentalsUtil.isDate(columnValue) ; 
						if (date !=null) {
							predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), date);
						}
						
						else {
							predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), columnValue);
						}
						
						break;

					case SMALLER:
						date = RentalsUtil.isDate(columnValue) ; 
						if (date !=null) {
							predicate = criteriaBuilder.lessThanOrEqualTo(root.get(columnName), date);
						}
						
						else {
							predicate = criteriaBuilder.lessThanOrEqualTo(root.get(columnName), columnValue);
						}
						
						break;
					}

					predicates.add(predicate);

				});

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

			}

		};

	}

	public boolean isDate(String dateStr) {
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm\"");
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean isBoolean(String strColumnValue) {
		return strColumnValue.equalsIgnoreCase("true") || strColumnValue.equalsIgnoreCase("false");
	}
}
