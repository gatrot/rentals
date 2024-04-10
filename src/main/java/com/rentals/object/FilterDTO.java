package com.rentals.object;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class FilterDTO {
	private String columnName;
	private Object columnValue;
	@Enumerated(EnumType.STRING)
	private OperationType operationType;

	public FilterDTO() {
		super();
	}

	public FilterDTO(String columnName, Object columnValue, OperationType operationType) {
		super();
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.operationType = operationType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

}
