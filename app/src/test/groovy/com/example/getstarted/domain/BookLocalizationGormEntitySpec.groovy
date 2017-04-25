package com.example.getstarted.domain

import grails.test.mixin.TestFor

@TestFor(BookLocalizationGormEntity)
class BookLocalizationGormEntitySpec {

    def "test title is not nulllable"() {
        when:
        domain.title = null

        then:
        !domain.validate(['title'])
    }

    def "test description is nullable"() {
        when:
        domain.description = null

        then:
        domain.validate(['description'])
    }
}
