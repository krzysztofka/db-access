package com.kali.dbaccess.generator.templates

import spock.lang.Specification

import java.util.function.Consumer

class BatchProcessingServiceSpec extends Specification {

    def service = new BatchProcessingService()

    def "it should call " () {
        given:
        def items = [Integer.valueOf(3), Integer.valueOf(5)];
        Consumer<Integer> consumer = Mock()

        when:
        service.process(items, consumer);

        then:
        1 * consumer.accept(items)
    }
}
