package com.kali.dbAccess.generator

import com.kali.dbAccess.domain.Product
import com.kali.dbAccess.generator.providers.EntityProvider
import com.kali.dbAccess.repository.ProductRepository
import spock.lang.Shared
import spock.lang.Specification

class ProductsPopulatorSpec extends Specification {

    def @Shared populator = new ProductsPopulator()

    def repository = Mock(ProductRepository)

    EntityProvider<Product> entityProvider = Mock()

    def setup() {
        populator.setRepository(repository)
        populator.setEntityProvider(entityProvider)
    }

    def "should get products from provider then persist them using repository and store references in context"() {
        given:
        def quantity = 3;
        def context = Mock(DataGenerationContext)
        def product =  new Product([name: 'product'])
        def id = 3L;

        when:
        populator.populate(context, quantity)

        then:
        quantity * entityProvider.getEntity(context) >> product
        quantity * repository.save(product) >> id
        quantity * context.addProduct(id)
    }
}
