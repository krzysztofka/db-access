package com.kali.dbaccess.domain;

public interface BaseEntity<T> {
    void setId(T id);

    T getId();
}
