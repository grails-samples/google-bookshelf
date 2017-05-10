package bookshelf.model

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder

@Builder
@CompileStatic
class BookImpl implements Book {
    String title
    String description
    String author
    String createdBy
    String createdById
    String publishedDate
    Long id
    String imageUrl

    String toString() {
        "Title: ${title}, Author: ${author}, Published date: ${publishedDate}, Added by: ${createdBy}" as String
    }
}
