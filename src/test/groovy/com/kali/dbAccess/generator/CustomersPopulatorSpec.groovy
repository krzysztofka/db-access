package com.kali.dbaccess.generator

import com.kali.dbaccess.domain.Customer
import com.kali.dbaccess.generator.providers.EntityProvider
import com.kali.dbaccess.repository.CustomerRepository
import spock.lang.Specification

class CustomersPopulatorSpec extends Specification {

    def populator = new CustomersPopulator()

    def repository = Mock(CustomerRepository)

    EntityProvider<Customer> entityProvider = Mock()

    def setup() {
        populator.setRepository(repository)
        populator.setEntityProvider(entityProvider)
    }

    def "should get customers from provider then persist them using repository and store references in context"() {
        given:
        def quantity = 3;
        def context = Mock(GenerationContext)
        def customer =  new Customer([email: 'test@test.com'])
        def id = 3L;

        when:
        populator.populate(context, quantity)

        then:
        quantity * entityProvider.getEntity(context) >> customer
        quantity * repository.save(customer) >> {
            customer.setId(id);
            return customer;
        }
    }
}
