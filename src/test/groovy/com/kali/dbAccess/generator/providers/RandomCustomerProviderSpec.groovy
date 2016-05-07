package com.kali.dbAccess.generator.providers

import com.kali.dbAccess.generator.DataGenerationContext
import spock.lang.*

import static org.hamcrest.Matchers.*

class RandomCustomerProviderSpec extends Specification {

    def @Shared randomCustomerProvider = new RandomCustomerProvider();

    def "should provide valid customer"() {
        given:
        def customer = randomCustomerProvider.getEntity(new DataGenerationContext());

        expect:
        assert customer != null
        assert customer.getId() == null
        assert customer.getEmail() != null
        assert customer.getEmail() ==~ /[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})/
        assert !isEmptyOrNullString().matches(customer.getAddress())
    }
}
