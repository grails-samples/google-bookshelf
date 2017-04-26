package com.example.getstarted.daos

import com.example.getstarted.objects.Book
import com.example.getstarted.objects.BookLocalization
import com.example.getstarted.objects.Result
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
//import com.example.getstarted.daos.DatastoreService
import groovy.transform.CompileStatic

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class DaoService implements BookDao, GrailsConfigurationAware {

    String storageType

    //x datastoreService

    CloudSqlService cloudSqlService

    @Override
    void setConfiguration(Config co) {
        storageType = co.getProperty('bookshelf.storageType', String, 'cloudSQL')
    }

    private BookDao getDao() {

//        if ( storageType == 'datastore' ) {
//            return datastoreService
//        }

        if ( storageType == 'cloudSQL' ) {
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
