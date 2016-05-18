package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from com.kali.dbaccess.domain.Product p, com.kali.dbaccess.domain.OrderItem i " +
            " where p.id = i.productId group by p order by sum(i.quantity) desc")
    List<Product> getBestsellerProducts(Pageable pageable);
}
