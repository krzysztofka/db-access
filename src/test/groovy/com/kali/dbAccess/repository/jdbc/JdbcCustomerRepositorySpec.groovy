package com.kali.dbAccess.repository.jdbc

import com.kali.dbAccess.domain.Customer
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import spock.lang.Shared
import spock.lang.Specification

class JdbcCustomerRepositorySpec extends Specification {

    def @Shared repository = new JdbcCustomerRepository()

    def @Shared customer = new Customer([email: 'test@test.com', address: 'Some address'])

    def simpleJdbcInsert = Mock(SimpleJdbcInsert)

    def setup() {
        simpleJdbcInsert.withTableName("CUSTOMERS") >> simpleJdbcInsert
        simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
        repository.setSimpleJdbcInsert(simpleJdbcInsert)
    }

    def "should init jdbc insert"() {
        when:
        repository.setSimpleJdbcInsert(simpleJdbcInsert)

        then:
        1 * simpleJdbcInsert.withTableName("CUSTOMERS") >> simpleJdbcInsert
        1 * simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
    }

    def "should map customer to table columns"() {
        when:
        def paramsMap = repository.toParameters(customer)

        then:
        assert customer.getAddress().equals(paramsMap.get('address'))
        assert customer.getEmail().equals(paramsMap.get('email'))
        assert !paramsMap.containsKey('id')
    }

    def "should init and execute simple jdbc insert when saving new customer"() {
        given:
        def id = Long.valueOf(123)

        when:
        def result = repository.save(customer)

        then:
        1 * simpleJdbcInsert.executeAndReturnKey(_ as Map) >> id
        assert result.equals(id)
    }
}
