package bookshelf.persistence

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BookGormEntity {

    String author
    String createdBy
    String createdById
    String publishedDate
    String imageUrl
    static hasMany = [localizations: BookLocalizationGormEntity]

    static constraints = {
        author nullable: true, maxSize: 255
        createdBy nullable: true, maxSize: 255
        createdById nullable: true, maxSize: 255
        publishedDate nullable: true
        imageUrl    nullable: true
    }

    static mapping = {
        table 'book'
    }
}
