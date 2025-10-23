package com.paco.aad.application;

public interface CustomService<T> {

    boolean validate(T entity);
}
