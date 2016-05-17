package com.kali.dbaccess.repository;

import com.kali.dbaccess.domain.Product;

import java.util.List;

public interface ProductRepository extends Repository<Product, Long> {

    void update(Product entity);

    List<Product> getBestsellerProducts(int limit);
}
