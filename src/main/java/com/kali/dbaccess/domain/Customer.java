package com.kali.dbaccess.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Customer implements Entity<Long> {

    private Long id;

    private String address;

    private String email;

    private String name;

    public Customer() {}

    public Customer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return new EqualsBuilder()
                .append(id, customer.id)
                .append(address, customer.address)
                .append(email, customer.email)
                .append(name, customer.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(address)
                .append(email)
                .append(name)
                .toHashCode();
    }
}
