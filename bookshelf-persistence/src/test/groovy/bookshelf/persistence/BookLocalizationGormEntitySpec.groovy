package bookshelf.persistence

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

    void "test title can have a maximum of 255 characters"() {
        when: 'for a string of 256 characters'
        String str = 'a' * 256
        domain.title = str

        then: 'title validation fails'
        !domain.validate(['title'])
        domain.errors['title'].code == 'maxSize.exceeded'

        when: 'for a string of 256 characters'
        str = 'a' * 255
        domain.title = str

        then: 'title validation passes'
        domain.validate(['title'])
    }
}
