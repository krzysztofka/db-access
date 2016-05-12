package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Order;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.generator.templates.BatchDataPopulatorTemplate;
import com.kali.dbaccess.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component(value = "ordersPopulator")
public class OrdersPopulator extends BatchDataPopulatorTemplate<Order> implements DatabasePopulator {

    private static final int BATCH_SIZE = 100;

    private static final Logger logger = LoggerFactory.getLogger(OrdersPopulator.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    @Qualifier("randomOrderProvider")
    private EntityProvider<Order> entityProvider;

    @Override
    protected void persistAll(Collection<Order> items, InMemoryGenerationContext context) {
        repository.saveAll(items);
    }

    @Override
    protected int batchSize() {
        return BATCH_SIZE;
    }

    public void setRepository(OrderRepository repository) {
        this.repository = repository;
    }

    public EntityProvider<Order> getEntityProvider() {
        return entityProvider;
    }

    public void setEntityProvider(EntityProvider<Order> entityProvider) {
        this.entityProvider = entityProvider;
    }
}
