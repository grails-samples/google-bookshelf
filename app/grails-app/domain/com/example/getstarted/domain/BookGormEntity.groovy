package com.example.getstarted.domain

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BookGormEntity {

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
}
