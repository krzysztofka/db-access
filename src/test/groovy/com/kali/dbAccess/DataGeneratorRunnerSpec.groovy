package com.kali.dbaccess

import com.kali.dbaccess.generator.InMemoryGenerationContext
import com.kali.dbaccess.generator.DatabasePopulator
import spock.lang.Specification

class DataGeneratorRunnerSpec extends Specification {

    def runner = new DataGeneratorRunner()

    def DatabasePopulator ordersPopulator = Mock(DatabasePopulator)
    def DatabasePopulator customerPopulator = Mock(DatabasePopulator)
    def DatabasePopulator productsPopulator = Mock(DatabasePopulator)

    def setup() {
        runner.setCustomersPopulator(customerPopulator)
        runner.setOrdersPopulator(ordersPopulator)
        runner.setProductsPopulator(productsPopulator)
    }

    def "should execute database populators"() {
        when:
        runner.run()

        then:
        1 * customerPopulator.populate(_ as InMemoryGenerationContext, _ as Integer)
        1 * productsPopulator.populate(_ as InMemoryGenerationContext, _ as Integer)

        then:
        1 * ordersPopulator.populate(_ as InMemoryGenerationContext, _ as Integer)
    }
}
