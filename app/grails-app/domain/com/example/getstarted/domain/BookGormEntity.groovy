package com.example.getstarted.domain

import com.example.getstarted.objects.Book
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BookGormEntity implements Book {
    String title
    String author
    String createdBy
    String createdById
    String publishedDate
    String description
    String imageUrl

    static constraints = {
        title nullable: false, unique: true
        author nullable: true
        createdBy nullable: true
        createdById nullable: true
        publishedDate nullable: true
        description nullable: true
        imageUrl    nullable: true
    }

    static mapping = {
        table 'book'
        description type: 'text'
    }
}