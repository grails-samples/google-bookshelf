package bookshelf.persistence

import bookshelf.model.BookLocalization
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BookLocalizationGormEntity implements BookLocalization {
    String title
    String description
    String languageCode
    static belongsTo = [book: BookGormEntity]

    static constraints = {
        title nullable: false, maxSize: 255
        description nullable: true
    }

    static mapping = {
        table 'book_localization'
        description type: 'text'
    }
}
