package com.kali.dbAccess.generator.providers

import com.kali.dbAccess.generator.DataGenerationContext
import spock.lang.Shared
import spock.lang.Specification

import static org.hamcrest.Matchers.isEmptyOrNullString

class RandomProductProviderSpec extends Specification {

    def @Shared randomProductProvider = new RandomProductProvider();

    def "should provide valid product"() {
        given:
        def product = randomProductProvider.getEntity(new DataGenerationContext());

        expect:
        assert product != null
        assert product.getId() == null
        assert !isEmptyOrNullString().matches(product.getName())
        assert !isEmptyOrNullString().matches(product.getDescription())
        assert product.price > 0
    }
}
