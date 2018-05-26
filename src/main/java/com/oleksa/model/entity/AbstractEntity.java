package com.oleksa.model.entity;

/**
 * Class for identificator.
 * 
 * @author atpt34
 *
 * @param <T> type of id
 */
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
