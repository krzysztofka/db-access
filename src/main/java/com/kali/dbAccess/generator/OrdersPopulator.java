package com.kali.dbAccess.generator;

import com.kali.dbAccess.domain.Order;
import com.kali.dbAccess.generator.providers.EntityProvider;
import com.kali.dbAccess.generator.templates.BatchDataPopulatorTemplate;
import com.kali.dbAccess.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component(value = "ordersPopulator")
public class OrdersPopulator extends BatchDataPopulatorTemplate<Order> implements DatabasePopulator {

    private static final int BATCH_SIZE = 100;

    @Autowired
    private OrderRepository repository;

    @Autowired
    @Qualifier("randomOrderProvider")
    private EntityProvider<Order> entityProvider;

    @Override
    protected void persistAll(Collection<Order> items, DataGenerationContext context) {
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
