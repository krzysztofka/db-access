package com.kali.dbAccess.generator

import com.kali.dbAccess.domain.Customer
import com.kali.dbAccess.generator.providers.EntityProvider
import com.kali.dbAccess.repository.CustomerRepository
import spock.lang.Shared
import spock.lang.Specification

class CustomersPopulatorSpec extends Specification {

    def @Shared populator = new CustomersPopulator()

    def repository = Mock(CustomerRepository)

    EntityProvider<Customer> entityProvider = Mock()

    def setup() {
        populator.setRepository(repository)
        populator.setEntityProvider(entityProvider)
    }

    def "should get customers from provider then persist them using repository and store references in context"() {
        given:
        def quantity = 3;
        def context = Mock(DataGenerationContext)
        def customer =  new Customer([email: 'test@test.com'])
        def id = 3L;

        when:
        populator.populate(context, quantity)

        then:
        quantity * entityProvider.getEntity(context) >> customer
        quantity * repository.save(customer) >> id
        quantity * context.addCustomer(id)
    }
}
