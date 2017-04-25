package com.example.getstarted.daos

import com.example.getstarted.domain.BookGormEntity
import com.example.getstarted.objects.Book
import com.example.getstarted.objects.BookProperties
import com.example.getstarted.objects.Result
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.DetachedCriteria
import grails.transaction.Transactional
import groovy.transform.CompileStatic

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
@Transactional
class CloudSqlService implements BookDao, GrailsConfigurationAware {

    int limit
    String orderBy

    @Override
    Long createBook(Book book) {
        BookGormEntity entity = new BookGormEntity()
        populateEntityWithBook(entity, book)
        entity.save()
        entity.id
    }

    private void populateEntityWithBook(BookGormEntity entity, Book book) {
        entity.with {
            title = book.title
            author = book.author
            createdBy = book.createdBy
            createdById = book.createdById
            publishedDate = book.publishedDate
            description = book.description
            imageUrl = book.imageUrl
        }
    }

    @Transactional(readOnly = true)
    @Override
    Book readBook(Long bookId) {
        BookGormEntity.get(bookId)
    }

    @Override
    void updateBook(Book book, boolean flush = false) {
        def entity = BookGormEntity.get(book.id)
        populateEntityWithBook(entity, book)
        entity.save(flush: flush)
    }

    @Override
    void deleteBook(Long bookId) {
        def entity = BookGormEntity.get(bookId)
        entity?.delete()
    }

    @Transactional(readOnly = true)
    @Override
    Result<Book> listBooks(String cursor) {
        def query = listBooksQuery()
        listBooksByQuery(cursor, query)
    }

    private DetachedCriteria<BookGormEntity> listBooksQuery() {
        BookGormEntity.where { }
    }

    private DetachedCriteria<BookGormEntity> listBooksByUserQuery(String userId) {
        BookGormEntity.where { createdById == userId }
    }

    private Result<Book> listBooksByQuery(String cursor, DetachedCriteria<BookGormEntity> query) {
        def offset = cursor ? Integer.parseInt(cursor) : 0
        def max = limit

        def books = query.list(max: max, offset: offset)
        int total = query.count() as int
        if (total > (offset + limit)) {
            return new Result<>(books, Integer.toString(offset + limit))
        }
        new Result<>(books)
    }

    @Transactional(readOnly = true)
    @Override
    Result<Book> listBooksByUser(String userId, String cursor) {
        def query = listBooksByUserQuery(userId)
        listBooksByQuery(cursor, query)
    }

    @Override
    void setConfiguration(Config co) {
        limit = co.getProperty('bookshelf.limit', Integer, 10)
        orderBy = co.getProperty('bookshelf.orderBy', String, BookProperties.TITLE)
    }
}
