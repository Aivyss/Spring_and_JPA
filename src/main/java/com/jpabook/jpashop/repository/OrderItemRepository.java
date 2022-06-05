package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemCustomRepository {

}
