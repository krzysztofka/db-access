package com.kali.dbaccess.generator

import com.kali.dbaccess.domain.Product
import com.kali.dbaccess.generator.providers.EntityProvider
import com.kali.dbaccess.repository.ProductRepository
import spock.lang.Specification

class ProductsPopulatorSpec extends Specification {

    def populator = new ProductsPopulator()

    def repository = Mock(ProductRepository)

    EntityProvider<Product> entityProvider = Mock()

    def setup() {
        populator.setRepository(repository)
        populator.setEntityProvider(entityProvider)
    }

    def "should get products from provider then persist them using repository and store references in context"() {
        given:
        def quantity = 3;
        def context = Mock(InMemoryGenerationContext)
        def product =  new Product([name: 'product', 'price': 1000, 'description': 'desc'])

        when:
        populator.populate(context, quantity)

        then:
        quantity * entityProvider.getEntity(context) >> product
        quantity * repository.save(product) >> product
        quantity * context.newProduct(product)
    }
}
