package com.kali.dbaccess.generator;

import com.kali.dbaccess.domain.Customer;
import com.kali.dbaccess.generator.providers.EntityProvider;
import com.kali.dbaccess.generator.templates.SimpleDataPopulatorTemplate;
import com.kali.dbaccess.repository.CustomerRepository;
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
    protected void persist(Customer item) {
        repository.save(item);
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
