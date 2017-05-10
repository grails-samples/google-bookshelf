package bookshelf.business

import bookshelf.model.BookLocalizationImpl
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(CreateBookWithCoverImageService)
class CreateBookWithCoverImageServiceSpec extends Specification {

    @SuppressWarnings('LineLength')
    @Unroll
    def "test bookLocalizationWithText #text => #expectedTitle : #expectedDescription"(String text, String expectedTitle, String expectedDescription) {
        when:
        def output = service.bookLocalizationWithText(null)

        then:
        if ( expectedTitle == null || expectedDescription == null ) {
            output == null
        } else {
            output == new BookLocalizationImpl(title: expectedTitle, description: expectedDescription)
        }

        where:
        text      | expectedTitle | expectedDescription
        null      | null          | null
        'Hello'   | 'Hello'       | null
        'a' * 256 | 'a' * 255     | 'a'
    }
}
