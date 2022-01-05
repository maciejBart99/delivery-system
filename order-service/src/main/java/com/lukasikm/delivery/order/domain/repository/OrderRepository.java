package com.lukasikm.delivery.order.domain.repository;

import com.lukasikm.delivery.order.domain.entity.Order;
import com.lukasikm.delivery.orderserviceclient.dto.OrderState;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface OrderRepository extends CassandraRepository<Order, UUID> { }
