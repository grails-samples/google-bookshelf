package com.example.getstarted.basicactions

import com.example.getstarted.objects.Book
import com.example.getstarted.objects.BookImpl
import grails.validation.Validateable
import groovy.transform.CompileStatic

@CompileStatic
class UpdateBookCommand implements Validateable {
    Long id
    String author
    String description
    String publishedDate
    String title

    static constraints = {
    }

    Object asType(Class clazz) {
        if (clazz == Book) {
            def book = new BookImpl()
            copyProperties(this, book)
            return book
        }
        else {
            super.asType(clazz)
        }
    }

    def copyProperties(source, target) {
        source.properties.each { key, value ->
            if (target.hasProperty(key as String) && !(key in ['class', 'metaClass'])) {
                target[key as String] = value
            }
        }
    }
}