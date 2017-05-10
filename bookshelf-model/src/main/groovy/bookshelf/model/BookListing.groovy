package bookshelf.model

import groovy.transform.CompileStatic

@CompileStatic
class BookListing {
    String title
    Result<Book> books
}
