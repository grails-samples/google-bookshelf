package bookshelf.controller

import bookshelf.model.Book
import bookshelf.model.BookImpl
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

@GrailsCompileStatic
class UpdateBookCommand implements Validateable {
    Long id
    String author
    String description
    String publishedDate
    String title
    MultipartFile file
    String imageUrl
    String createdById
    String createdBy

    static constraints = {
        id nullable: false
        title nullable: false
        author nullable: true
        description nullable: true
        publishedDate nullable: true
        file nullable: true
        imageUrl nullable: true
        createdById nullable: true
        createdBy nullable: true
    }

    Object asType(Class clazz) {
        if (clazz == Book) {
            def book = new BookImpl()
            copyProperties(this, book)
            return book
        }
        super.asType(clazz)
    }

    def copyProperties(source, target) {
        source.properties.each { key, value ->
            if (target.hasProperty(key as String) && !(key in ['class', 'metaClass'])) {
                target[key as String] = value
            }
        }
    }
}
