package com.api.CyberStore_API.repositories;

import com.api.CyberStore_API.entities.Order;
import com.api.CyberStore_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {



}

