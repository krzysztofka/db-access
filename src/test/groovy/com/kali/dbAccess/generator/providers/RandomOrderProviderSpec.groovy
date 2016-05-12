package com.kali.dbaccess.generator.providers

import com.kali.dbaccess.domain.Customer
import com.kali.dbaccess.domain.Product
import com.kali.dbaccess.generator.InMemoryGenerationContext
import spock.lang.Specification

import java.util.stream.Collectors
import java.util.stream.LongStream

class RandomOrderProviderSpec extends Specification {

    def randomOrderProvider = new RandomOrderProvider();

    def dataGenerationContext = Mock(InMemoryGenerationContext)

    def "should have properties set"() {
        given:
        def products = [new Product([id: 11])]

        when:
        def order = randomOrderProvider.getEntity(dataGenerationContext);

        then:
        1 * dataGenerationContext.getRandomUniqueProducts(_ as Integer) >> products
        assert order.getId() == null
        assert order.getOrderDate() != null
        assert order.getItems().size() == products.size()
        assert order.getItems().each {assert it.getProduct().equals(products[0])}
    }

    def "should use customer from context"() {
        given:
        def products = [new Product([id: 1L]), new Product([id: 2L])]
        def customer = new Customer(id: 2L)

        when:
        def order = randomOrderProvider.getEntity(dataGenerationContext);

        then:
        1 * dataGenerationContext.getRandomUniqueProducts(_ as Integer) >> products
        1 * dataGenerationContext.getRandomCustomer() >> customer
        assert order.getCustomer().getId() == 2L
    }

    def "should have initialized order items"() {
        when:
        def order = randomOrderProvider.getEntity(dataGenerationContext);

        then:
        1 * dataGenerationContext.getRandomUniqueProducts(_ as Integer) >> {
            LongStream.range(0, it.get(0)).boxed().map({new Product([id: it])}).collect(Collectors.toList())
        }
        order.getItems().each {
            assert it.getProduct().getId() < order.getItems().size()
            assert it.getQuantity() > 0
            assert it.getOrder().getId() == null
        }
    }
}
