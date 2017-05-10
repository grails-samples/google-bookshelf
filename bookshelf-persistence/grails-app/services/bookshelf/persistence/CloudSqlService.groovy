package bookshelf.persistence

import bookshelf.model.BookDao
import bookshelf.model.Book
import bookshelf.model.BookImpl
import bookshelf.model.BookLocalization
import bookshelf.model.Result
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.DetachedCriteria
import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.plugins.googlecloud.translate.GoogleCloudTranslateService

@Slf4j
@SuppressWarnings('GrailsStatelessService')
@CompileStatic
@Transactional
class CloudSqlService implements BookDao, GrailsConfigurationAware {

    int limit
    String defaultLanguageCode
    List<String> localizations

    GoogleCloudTranslateService googleCloudTranslateService

    @Override
    void setConfiguration(Config co) {
        limit = co.getProperty('bookshelf.limit', Integer, 10)
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
        localizations = co.getProperty('bookshelf.localizations', List)
    }

    @SuppressWarnings('LineLength')
    @Override
    Long createBook(Book book) {
        BookGormEntity entity = new BookGormEntity()
        populateEntityWithBook(entity, book)
        if ( book.title != null ) {
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
        }
        entity.save()
        entity.id
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

    @SuppressWarnings('LineLength')
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

    @SuppressWarnings('LineLength')
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

    private DetachedCriteria<BookGormEntity> listBooksQuery() {
        BookGormEntity.where { }
    }

    private DetachedCriteria<BookGormEntity> listBooksByUserQuery(String userId) {
        BookGormEntity.where { createdById == userId }
    }

    private Result<Book> listBooksByQuery(String cursor, DetachedCriteria<BookGormEntity> query) {
        def offset = cursor ? Integer.parseInt(cursor) : 0
        def max = limit
        int total = query.count() as int
        def booksEntities = query.max(max).offset(offset).list()
        def books = collectBooks(booksEntities)
        if (total > (offset + limit)) {
            return new Result<>(books, Integer.toString(offset + limit))
        }
        new Result<>(books)
    }

    private List<Book> collectBooks(List<BookGormEntity> entities) {
        entities.collect { BookGormEntity entity ->
            BookImpl book = new BookImpl()

            BookLocalization bookLocalization = getLocalization(entity.id, defaultLanguageCode)
            book.with {
                id = entity.id
                author = entity.author
                createdBy = entity.createdBy
                createdById = entity.createdById
                publishedDate = entity.publishedDate
                imageUrl = entity.imageUrl
                title = bookLocalization?.title
                description = bookLocalization?.description
            }
            book
        } as List<Book>
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
}
