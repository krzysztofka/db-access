package com.kali.dbAccess.repository.jdbc

import com.kali.dbAccess.domain.Product
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import spock.lang.Shared
import spock.lang.Specification

class JdbcProductRepositorySpec extends Specification {

    def @Shared repository = new JdbcProductRepository()

    def @Shared product = new Product([name: 'Name', description: 'Some desc', price: 100])

    def simpleJdbcInsert = Mock(SimpleJdbcInsert)

    def setup() {
        repository.setSimpleJdbcInsert(simpleJdbcInsert)
    }

    def "should map product to table columns"() {
        when:
        def paramsMap = repository.toParameters(product)

        then:
        assert product.getName().equals(paramsMap.get('name'))
        assert product.getDescription().equals(paramsMap.get('description'))
        assert product.getPrice().equals(paramsMap.get('price'))
        assert !paramsMap.containsKey('id')
    }

   def "should init and execute simple jdbc insert when saving new product"() {
        when:
        def id = repository.save(product)

        then:
        1 * simpleJdbcInsert.withTableName("PRODUCTS") >> simpleJdbcInsert
        1 * simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
        1 * simpleJdbcInsert.executeAndReturnKey(_ as MapSqlParameterSource) >> Long.valueOf(12)
        assert id == 12
    }
}
