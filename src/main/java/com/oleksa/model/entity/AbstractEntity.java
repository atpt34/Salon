package com.oleksa.model.entity;

public abstract class AbstractEntity<T> {

	T id;

	public AbstractEntity(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
}
