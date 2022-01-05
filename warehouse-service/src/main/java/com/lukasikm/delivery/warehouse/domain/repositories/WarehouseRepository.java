package com.lukasikm.delivery.warehouse.domain.repositories;

import com.lukasikm.delivery.warehouse.domain.entity.Warehouse;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends CassandraRepository<Warehouse, UUID> {
}
