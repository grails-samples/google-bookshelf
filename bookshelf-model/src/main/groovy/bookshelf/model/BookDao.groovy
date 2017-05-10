package bookshelf.model

import groovy.transform.CompileStatic

@CompileStatic
interface BookDao {
    Long createBook(Book book)

    Book readBook(Long bookId)

    void updateBook(Book book)

    void deleteBook(Long bookId)

    Result<Book> listBooks(String startCursor)

    Result<Book> listBooksByUser(String userId, String startCursor)

    BookLocalization getLocalization(Long bookId, String languageCode)
}
