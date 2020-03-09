package com.utilities.datatypes;

import java.math.BigDecimal;

public class SourceClass {
	private String name;
	private int age;
	private BigDecimal id;

	public SourceClass(BigDecimal id, Integer age, String name) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.age = age;

	}

	public SourceClass() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

}
