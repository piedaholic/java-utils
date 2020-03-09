package com.utilities.design;

public class ChildClass extends ParentClass implements RootInterface {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		// this.id = id;
		this.id = this.id == null ? id : this.id;
	}

}
