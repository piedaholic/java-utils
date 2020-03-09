package com.utilities.design;

public class ParentClass {
	private String id;
	private EmbeddableClass embeddableClass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmbeddableClass getEmbeddableClass() {
		return embeddableClass;
	}

	public void setEmbeddableClass(EmbeddableClass embeddableClass) {
		this.embeddableClass = embeddableClass;
	}
}
