package bookshelf.persistence

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BookGormEntity)
class BookGormEntitySpec extends Specification {

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

    def "test imageUrl is nullable"() {
        when:
        domain.imageUrl = null

        then:
        domain.validate(['imageUrl'])
    }

    void "test author can have a maximum of 255 characters"() {
        when: 'for a string of 256 characters'
        String str = 'a' * 256
        domain.author = str

        then: 'author validation fails'
        !domain.validate(['author'])
        domain.errors['author'].code == 'maxSize.exceeded'

        when: 'for a string of 256 characters'
        str = 'a' * 255
        domain.author = str

        then: 'author validation passes'
        domain.validate(['author'])
    }

    void "test createdBy can have a maximum of 255 characters"() {
        when: 'for a string of 256 characters'
        String str = 'a' * 256
        domain.createdBy = str

        then: 'createdBy validation fails'
        !domain.validate(['createdBy'])
        domain.errors['createdBy'].code == 'maxSize.exceeded'

        when: 'for a string of 256 characters'
        str = 'a' * 255
        domain.createdBy = str

        then: 'createdBy validation passes'
        domain.validate(['createdBy'])
    }

    void "test createdById can have a maximum of 255 characters"() {
        when: 'for a string of 256 characters'
        String str = 'a' * 256
        domain.createdById = str

        then: 'createdById validation fails'
        !domain.validate(['createdById'])
        domain.errors['createdById'].code == 'maxSize.exceeded'

        when: 'for a string of 256 characters'
        str = 'a' * 255
        domain.createdById = str

        then: 'createdById validation passes'
        domain.validate(['createdById'])
    }

}
