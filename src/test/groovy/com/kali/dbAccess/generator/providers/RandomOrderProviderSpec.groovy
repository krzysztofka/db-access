package com.kali.dbAccess.generator.providers

import com.kali.dbAccess.generator.DataGenerationContext
import spock.lang.Shared
import spock.lang.Specification

class RandomOrderProviderSpec extends Specification {

    def @Shared randomOrderProvider = new RandomOrderProvider();

    def dataGenerationContext = Mock(DataGenerationContext)

    def "should have properties set"() {
        when:
        def order = randomOrderProvider.getEntity(dataGenerationContext);

        then:

        assert order.getId() == null
        assert order.getOrderDate() != null
        assert !order.getItems().isEmpty()
    }

    def "should use products from context"() {
        when:
        def order = randomOrderProvider.getEntity(dataGenerationContext);

        then:
        1 * dataGenerationContext.getRandomCustomer() >> 2L
        assert order.getCustomer().getId() == 2L

    }

    def "should have initialized order items"() {
        when:
        def order = randomOrderProvider.getEntity(dataGenerationContext);
        then:
        (1.._) * dataGenerationContext.getRandomProduct() >> 3L
        order.getItems().each {
            assert it.getProductId() == 3L
            assert it.getQuantity() > 0
            assert it.getOrderId() == null
        }
    }
}
