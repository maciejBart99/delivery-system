package com.lukasikm.delivery.auth.domain.repository;

import com.lukasikm.delivery.auth.domain.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {
    Optional<User> getUseByEmail(String email);
}
