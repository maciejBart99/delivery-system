package com.lukasikm.delivery.retail.domain.repository;

import com.lukasikm.delivery.retail.domain.entity.Retail;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RetailRepository extends CassandraRepository<Retail, UUID> {

}
