package com.kali.dbaccess

import com.kali.dbaccess.generator.DatabasePopulator
import com.kali.dbaccess.generator.GenerationContext
import spock.lang.Specification

import java.util.function.Supplier

class DataGeneratorRunnerSpec extends Specification {

    def runner = new DataGeneratorRunner()

    def DatabasePopulator ordersPopulator = Mock(DatabasePopulator)
    def DatabasePopulator customerPopulator = Mock(DatabasePopulator)
    def DatabasePopulator productsPopulator = Mock(DatabasePopulator)

    def generationContext = Mock(GenerationContext);
    Supplier<GenerationContext> generationContextSupplier = Mock(Supplier)

    def setup() {
        runner.setCustomersPopulator(customerPopulator)
        runner.setOrdersPopulator(ordersPopulator)
        runner.setProductsPopulator(productsPopulator)
        runner.setGenerationContextSupplier(generationContextSupplier)
    }

    def "should execute database populators"() {
        when:
        runner.run()

        then:
        1 * generationContextSupplier.get() >> generationContext
        1 * customerPopulator.populate(generationContext, _ as Integer)
        1 * productsPopulator.populate(generationContext, _ as Integer)

        then:
        1 * ordersPopulator.populate(generationContext, _ as Integer)
    }
}
