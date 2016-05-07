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
        repository.setSimpleJdbcInsert(simpleJdbcInsert)
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
        when:
        def id = repository.save(customer)

        then:
        1 * simpleJdbcInsert.withTableName("CUSTOMERS") >> simpleJdbcInsert
        1 * simpleJdbcInsert.usingGeneratedKeyColumns("id") >> simpleJdbcInsert
        1 * simpleJdbcInsert.executeAndReturnKey(_ as MapSqlParameterSource) >> Long.valueOf(123)
        assert id == 123
    }
}
