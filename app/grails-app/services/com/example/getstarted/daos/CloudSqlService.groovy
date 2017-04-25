package com.example.getstarted.daos

import com.example.getstarted.objects.BookImpl
import org.grails.plugins.googlecloud.translate.GoogleCloudTranslateService
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
import groovy.util.logging.Slf4j


@Slf4j
@SuppressWarnings('GrailsStatelessService')
@CompileStatic
@Transactional
class CloudSqlService implements BookDao, GrailsConfigurationAware {

    int limit
    String orderBy
    String defaultLanguageCode
    List<String> localizations

    GoogleCloudTranslateService googleCloudTranslateService

    @Override
    Long createBook(Book book) {
        BookGormEntity entity = new BookGormEntity()
        populateEntityWithBook(entity, book)
        entity.addToLocalizations(new BookLocalizationGormEntity(languageCode: defaultLanguageCode,
                title: book.title,
                description: book.description))

        for (String languageCode : localizations ) {
            String title = googleCloudTranslateService.translateTextFromSourceToTarget(book.title, defaultLanguageCode, languageCode)
            String description = googleCloudTranslateService.translateTextFromSourceToTarget(book.description, defaultLanguageCode, languageCode)
            entity.addToLocalizations(new BookLocalizationGormEntity(languageCode: languageCode,
                    title: title,
                    description: description))
        }

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
        def entity = BookGormEntity.get(bookId)
        def book = new BookImpl()
        book.with {
            id = entity.id
            author = entity.author
            createdBy = entity.createdById
            createdById = entity.createdById
            publishedDate = entity.publishedDate
            imageUrl = entity.imageUrl
        }
        BookLocalization bookLocalization = getLocalization(bookId, defaultLanguageCode)
        book.title = bookLocalization?.title
        book.description = bookLocalization?.description
        book
    }

    @Override
    void updateBook(Book book) {
        def entity = BookGormEntity.get(book.id)
        populateEntityWithBook(entity, book)
        addOrUpdateBookLocalizationWithTitleAndDescriptionByLanguageCode(entity, defaultLanguageCode, book.title, book.description)

        for (String languageCode : localizations ) {
            String title = googleCloudTranslateService.translateTextFromSourceToTarget(book.title, defaultLanguageCode, languageCode)
            String description = googleCloudTranslateService.translateTextFromSourceToTarget(book.description, defaultLanguageCode, languageCode)
            addOrUpdateBookLocalizationWithTitleAndDescriptionByLanguageCode(entity, languageCode, title, description)
        }

        entity.save()
    }

    static void addOrUpdateBookLocalizationWithTitleAndDescriptionByLanguageCode(BookGormEntity entity, String languageCode, String title, String description) {
        BookLocalizationGormEntity defaultBookLocalizationGormEntity = entity.localizations.find { it.languageCode == languageCode }
        if ( defaultBookLocalizationGormEntity ) {
            defaultBookLocalizationGormEntity.title = title
            defaultBookLocalizationGormEntity.description = description
        } else {
            entity.addToLocalizations(new BookLocalizationGormEntity(languageCode: languageCode,
                    title: title,
                    description: description))
        }
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
        def q = BookLocalizationGormEntity.where { book.id == bookId && languageCode == language }
        log.info 'code: ' + code + 'size: ' + q.list().size()
        q.get()
    }

    @Override
    void setConfiguration(Config co) {
        limit = co.getProperty('bookshelf.limit', Integer, 10)
        orderBy = co.getProperty('bookshelf.orderBy', String, BookProperties.TITLE)
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
        localizations = co.getProperty('bookshelf.localizations', List)
    }
}
