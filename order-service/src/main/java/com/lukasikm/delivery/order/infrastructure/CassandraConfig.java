package com.lukasikm.delivery.order.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Set;

@Configuration
@EnableCassandraRepositories(basePackages = "com.lukasikm.delivery.order.domain.entity")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${order.cassandra.cp}")
    private String cassandraContactPoint;

    @Value("${order.cassandra.port}")
    private int cassandraPort;

    @Value("${order.cassandra.keyspace}")
    private String cassandraKeySpace;

    @Override
    protected String getKeyspaceName() {
        return cassandraKeySpace;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster =
                new CassandraClusterFactoryBean();
        cluster.setContactPoints(cassandraContactPoint);
        cluster.setPort(cassandraPort);
        return cluster;
    }

    @Bean
    public CassandraOperations cassandraOperations() {
        return new CassandraTemplate(session().getObject());
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.lukasikm.delivery.order.domain.entity"};
    }

    @Override
    protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
        return CassandraEntityClassScanner.scan(getEntityBasePackages());
    }
}