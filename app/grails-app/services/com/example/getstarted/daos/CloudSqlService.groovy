package com.example.getstarted.daos

import com.example.getstarted.BookLocalizationTagLib
import com.example.getstarted.domain.BookGormEntity
import com.example.getstarted.domain.BookLocalizationGormEntity
import com.example.getstarted.objects.Book
import com.example.getstarted.objects.BookLocalization
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
    String defaultLanguageCode

    @Override
    Long createBook(Book book) {
        BookGormEntity entity = new BookGormEntity()
        populateEntityWithBook(entity, book)
        entity.addToLocalizations(new BookLocalizationGormEntity(languageCode: defaultLanguageCode,
                title: book.title,
                description: book.description))
        entity.save()
        entity.id
    }

    private static void populateEntityWithBook(BookGormEntity entity, Book book) {
        entity.with {
            author = book.author
            createdBy = book.createdBy
            createdById = book.createdById
            publishedDate = book.publishedDate
            imageUrl = book.imageUrl
        }
    }

    @Transactional(readOnly = true)
    @Override
    Book readBook(Long bookId) {
        BookGormEntity.get(bookId)
    }

    @Override
    void updateBook(Book book) {
        def entity = BookGormEntity.get(book.id)
        populateEntityWithBook(entity, book)
        def bookLocalization = entity.localizations.find { it.languageCode = defaultLanguageCode }
        if ( bookLocalization ) {
            bookLocalization.title = book.title
            bookLocalization.description = book.description
        }
        entity.save(flush: true)
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

    @Transactional(readOnly = true)
    @Override
    BookLocalization getLocalization(Long bookId, String code) {
        String language = code ?: defaultLanguageCode
        BookLocalizationGormEntity.where { book.id == bookId && languageCode == language }.get()
    }

    @Override
    void setConfiguration(Config co) {
        limit = co.getProperty('bookshelf.limit', Integer, 10)
        orderBy = co.getProperty('bookshelf.orderBy', String, BookProperties.TITLE)
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
    }
}
