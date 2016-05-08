package com.kali.dbAccess.generator

import com.kali.dbAccess.domain.Order
import com.kali.dbAccess.generator.providers.EntityProvider
import com.kali.dbAccess.generator.templates.BatchProcessor
import com.kali.dbAccess.repository.OrderRepository
import spock.lang.Shared
import spock.lang.Specification

import java.util.function.Consumer

class OrdersPopulatorSpec extends Specification {

    def @Shared populator = new OrdersPopulator()

    def repository = Mock(OrderRepository)

    def batchProcessor = Mock(BatchProcessor)

    def context = Mock(DataGenerationContext)

    EntityProvider<Order> entityProvider = Mock()

    def setup() {
        populator.setEntityProvider(entityProvider)
        populator.setRepository(repository)
        populator.setBatchProcessor(batchProcessor)
    }

    def "should have batch size defined"() {
        expect:
        assert populator.batchSize() == 100
    }

    def "should call batch processor"() {
        given:
        def quantity = 120
        def order = Mock(Order)

        when:
        populator.populate(context, quantity)

        then:
        100 * entityProvider.getEntity(context) >> order
        1 * batchProcessor.process(_ as Collection<Order>, _ as Consumer<Collection<Order>>)

        then:
        20 * entityProvider.getEntity(context) >> order
        1 * batchProcessor.process(_ as Collection<Order>, _ as Consumer<Collection<Order>>)
    }

    def "should call repository to persist all order" () {
        given:
        Collection<Order> orders = [new Order()]

        when:
        populator.persistAll(orders, context)

        then:
        1 * repository.saveAll(orders)
    }
}
