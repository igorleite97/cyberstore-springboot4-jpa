package com.api.CyberStore_API.repositories;

import com.api.CyberStore_API.entities.OrderItem;
import com.api.CyberStore_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {



}

