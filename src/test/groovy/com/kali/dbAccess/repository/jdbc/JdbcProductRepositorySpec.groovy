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
        simpleJdbcInsert.withTableName("PRODUCTS") >> simpleJdbcInsert
        simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
        repository.setSimpleJdbcInsert(simpleJdbcInsert)
    }

    def "should init jdbc insert"() {
        when:
        repository.setSimpleJdbcInsert(simpleJdbcInsert)

        then:
        1 * simpleJdbcInsert.withTableName("PRODUCTS") >> simpleJdbcInsert
        1 * simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
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
        given:
        def id = Long.valueOf(12)
        simpleJdbcInsert.executeAndReturnKey(_ as Map) >> id

        when:
        def result = repository.save(product)

        then:
        assert id.equals(result)
    }
}
