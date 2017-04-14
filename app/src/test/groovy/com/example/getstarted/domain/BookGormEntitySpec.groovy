package com.example.getstarted.domain

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BookGormEntity)
class BookGormEntitySpec extends Specification {

    def "test title is not nulllable"() {
        when:
        domain.title = null

        then:
        !domain.validate(['title'])
    }

    def "test author is nullable"() {
        when:
        domain.author = null

        then:
        domain.validate(['author'])
    }

    def "test createdBy is nullable"() {
        when:
        domain.createdBy = null

        then:
        domain.validate(['createdBy'])
    }

    def "test createdById is nullable"() {
        when:
        domain.createdById = null

        then:
        domain.validate(['createdById'])
    }

    def "test publishedDate is nullable"() {
        when:
        domain.publishedDate = null

        then:
        domain.validate(['publishedDate'])
    }


    def "test description is nullable"() {
        when:
        domain.description = null

        then:
        domain.validate(['description'])
    }

    def "test imageUrl is nullable"() {
        when:
        domain.imageUrl = null

        then:
        domain.validate(['imageUrl'])
    }
}