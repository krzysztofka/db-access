package com.kali.dbaccess.repository.jpa;

import com.kali.dbaccess.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, JpaOrderRepositoryCustom {

}
