package bookshelf.business

import bookshelf.model.Book
import bookshelf.model.BookListing
import bookshelf.model.Result
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class BookListingService {

    DaoService daoService

    MessageSource messageSource

    BookListing findBookListingByUser(String cursor, Locale locale, String userId) {
        Result<Book> result = daoService.listBooksByUser(userId, cursor)
        String title = messageSource.getMessage('books.mine', null, 'My Books', locale)
        new BookListing(title: title, books: result)
    }

    BookListing findBookListing(String cursor, Locale locale) {
        Result<Book> result = daoService.listBooks(cursor)
        String title = messageSource.getMessage('books', null, 'Books', locale)
        new BookListing(title: title, books: result)
    }
}
