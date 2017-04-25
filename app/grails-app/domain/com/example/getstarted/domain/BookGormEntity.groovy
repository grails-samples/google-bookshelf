package com.example.getstarted.domain

import com.example.getstarted.objects.Book
import grails.compiler.GrailsCompileStatic
import org.springframework.beans.factory.annotation.Value

@GrailsCompileStatic
class BookGormEntity implements Book {

    @Value('bookshelf.defaultLanguageCode')
    String defaultLanguageCode

    String author
    String createdBy
    String createdById
    String publishedDate
    String imageUrl
    static hasMany = [localizations: BookLocalizationGormEntity]

    static transients = ['defaultLanguageCode', 'defaultLocalization']

    static constraints = {
        author nullable: true
        createdBy nullable: true
        createdById nullable: true
        publishedDate nullable: true
        imageUrl    nullable: true
    }

    static mapping = {
        table 'book'
    }

    BookLocalizationGormEntity getDefaultLocalization() {
        def bookLocalization = localizations.find { it.languageCode = defaultLanguageCode}
        if ( bookLocalization ) {
            return bookLocalization
        }
        localizations.isEmpty() ? null : localizations.first()
    }

    @Override
    String getTitle() {
        defaultLocalization?.title
    }

    @Override
    String getDescription() {
        defaultLocalization?.description
    }
}
