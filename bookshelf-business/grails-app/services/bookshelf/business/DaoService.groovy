package bookshelf.business

import bookshelf.model.Book
import bookshelf.model.BookDao
import bookshelf.model.BookLocalization
import bookshelf.model.Result
import bookshelf.persistence.CloudSqlService
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class DaoService implements BookDao, GrailsConfigurationAware {

    String persistenceType

    //x datastoreService

    CloudSqlService cloudSqlService

    @Override
    void setConfiguration(Config co) {
        persistenceType = co.getProperty('bookshelf.persistenceType', String, 'cloudSQL')
    }

    private BookDao getDao() {

//        if ( persistenceType == 'datastore' ) {
//            return datastoreService
//        }

        if ( persistenceType == 'cloudSQL' ) {
            return cloudSqlService
        }

        cloudSqlService
    }

    @Override
    Long createBook(Book book) {
        dao.createBook(book)
    }

    @Override
    Book readBook(Long bookId) {
        dao.readBook(bookId)
    }

    @Override
    void updateBook(Book book) {
        dao.updateBook(book)
    }

    @Override
    void deleteBook(Long bookId) {
        dao.deleteBook(bookId)
    }

    @Override
    Result<Book> listBooks(String startCursor) {
        dao.listBooks(startCursor)
    }

    @Override
    Result<Book> listBooksByUser(String userId, String startCursor) {
        dao.listBooksByUser(userId, startCursor)
    }

    @Override
    BookLocalization getLocalization(Long bookId, String languageCode) {
        dao.getLocalization(bookId, languageCode)
    }
}
