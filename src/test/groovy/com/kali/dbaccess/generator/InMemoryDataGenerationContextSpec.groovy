package com.kali.dbaccess.generator

import com.kali.dbaccess.domain.Customer
import com.kali.dbaccess.domain.Product
import spock.lang.Specification

class InMemoryDataGenerationContextSpec extends Specification {

    def context = new InMemoryGenerationContext();

    def products = [new Product([id: 1]), new Product([id: 2]), new Product([id: 3]), new Product([id: 4])]

    def "Should provide uniqie products"() {
        given:
        context.newProducts(products)

        when:
        def result = context.getRandomUniqueProducts(products.size()) as Set

        then:
        assert result.size() == products.size()
    }

    def "Should throw illegal argument exception when requesting more unique products then available"() {
        given:
        context.newProducts(products)

        when:
        context.getRandomUniqueProducts(products.size() + 1)

        then:
        thrown IllegalArgumentException
    }

    def "Should provide one customer"() {
        given:
        Set<Customer> customers = [new Customer([id: 1]), new Customer([id: 2]), new Customer([id: 3])]
        context.newCustomers(customers)

        when:
        def customer = context.getRandomCustomer();

        then:
        assert customers.contains(customer)

    }
}
