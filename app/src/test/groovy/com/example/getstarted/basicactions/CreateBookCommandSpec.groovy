package com.example.getstarted.basicactions

import spock.lang.Specification

class CreateBookCommandSpec extends Specification {

    def "test file is NOT nullable"() {
        when:
        def cmd = new CreateBookCommand()

        then:
        !cmd.validate(['file'])
    }
}
