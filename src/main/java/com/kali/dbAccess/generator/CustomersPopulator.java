package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.generator.templates.SimpleDataPopulatorTemplate;
import com.kali.dbaccess.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "customersPopulator")
public class CustomersPopulator extends SimpleDataPopulatorTemplate<Customer> implements DatabasePopulator {

    private static final Logger logger = LoggerFactory.getLogger(CustomersPopulator.class);

    @Autowired
    private CustomerRepository repository;

    @Autowired
    @Qualifier("randomCustomerProvider")
    private EntityProvider<Customer> entityProvider;

    @Override
    public void persist(Customer item, InMemoryGenerationContext context) {
        context.newCustomer(repository.save(item));
    }

    public void setRepository(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntityProvider<Customer> getEntityProvider() {
        return entityProvider;
    }

    public void setEntityProvider(EntityProvider<Customer> entityProvider) {
        this.entityProvider = entityProvider;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
