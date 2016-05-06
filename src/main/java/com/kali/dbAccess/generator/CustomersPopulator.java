package com.kali.dbAccess.generator;

import com.kali.dbAccess.domain.Customer;
import com.kali.dbAccess.generator.providers.EntityProvider;
import com.kali.dbAccess.generator.templates.SimpleDataPopulatorTemplate;
import com.kali.dbAccess.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "customersPopulator")
public class CustomersPopulator extends SimpleDataPopulatorTemplate<Customer> implements DatabasePopulator {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    @Qualifier("randomCustomerProvider")
    private EntityProvider<Customer> entityProvider;

    @Override
    public void persist(Customer item, DataGenerationContext context) {
        context.addCustomer(repository.save(item));
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
}
