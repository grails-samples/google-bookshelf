package com.example.getstarted.basicactions

import com.example.getstarted.CreateBookWithCoverImageService
import com.example.getstarted.UploadBookCoverService
import com.example.getstarted.daos.BookDao
import com.example.getstarted.daos.CloudSqlService
import com.example.getstarted.daos.DatastoreService
import com.example.getstarted.objects.Book
import com.example.getstarted.objects.BookCurator
import com.example.getstarted.objects.Result
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.plugins.googlecloud.storage.GoogleCloudStorageService
import org.springframework.context.MessageSource

@Slf4j
@CompileStatic
class BookController implements GrailsConfigurationAware {

    String storageType

    static allowedMethods = [index: 'GET',
                             create: 'GET',
                             save: 'POST',
                             edit: 'GET',
                             update: 'POST',
                             delete: 'GET',
                             show: 'GET',
                             mine: 'GET']

    GoogleCloudStorageService googleCloudStorageService

    UploadBookCoverService uploadBookCoverService

    CreateBookWithCoverImageService createBookWithCoverImageService

    MessageSource messageSource

    DatastoreService datastoreService

    CloudSqlService cloudSqlService

    private BookDao getDao() {

        if ( storageType == 'datastore' ) {
            return datastoreService
        }

        if ( storageType == 'cloudSQL' ) {
            return cloudSqlService
        }

        cloudSqlService
    }

    def index(String cursor) {
        Result<Book> result = dao.listBooks(cursor)
        log.info 'Retrieved list of all books'
        String title = messageSource.getMessage('books', null, 'Books', request.locale)
        [books: result.result, cursor: result.cursor, title: title]
    }

    @SuppressWarnings('EmptyMethod')
    def create() {

    }

    def save(CreateBookCommand cmd) {
        if ( cmd.hasErrors() ) {
            respond cmd.errors
            return
        }

        BookCurator curator = new BookCurator()
        if ( session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN] ) {
            curator.createdBy = session[Oauth2CallbackController.SESSION_USER_EMAIL]
            curator.createdById = session[Oauth2CallbackController.SESSION_USER_ID]
        }

        Long id = createBookWithCoverImageService.saveBookWithCover(cmd.file, curator)

        log.info 'Created book {0}', id

        redirect(action: 'show', id: id)
    }

    def show(Long id) {
        log.info 'Read book with id {0}', id
        [book: dao.readBook(id)]
    }

    def edit(Long id) {
        [book: dao.readBook(id)]
    }

    def update(UpdateBookCommand cmd) {
        if ( cmd.hasErrors() ) {
            return
        }
        def book = cmd as Book

        if ( (
                book.createdById == null ||
                        book.createdBy == null
             ) && session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN] ) {
            book.createdBy = session[Oauth2CallbackController.SESSION_USER_EMAIL]
            book.createdById = session[Oauth2CallbackController.SESSION_USER_ID]
        }

        if ( cmd.file && !cmd.file.isEmpty() ) {
            String fileName = uploadBookCoverService.nameForFile(cmd.file)
            String imageUrl = googleCloudStorageService.storeMultipartFile(fileName, cmd.file)
            book.imageUrl = imageUrl
        }

        dao.updateBook(book)
        redirect(action: 'show', id: book.id)
    }

    def delete(Long id) {
        dao.deleteBook(id)
        redirect action: 'index'
    }

    def mine(String cursor) {
        String userId = session[Oauth2CallbackController.SESSION_USER_ID]
        Result<Book> result = dao.listBooksByUser(userId, cursor)
        log.info 'Retrieved list of all books for user: {0}', userId
        String title = messageSource.getMessage('books.mine', null, 'My Books', request.locale)
        render(view: 'index', model: [books: result.result, cursor: result.cursor, title: title])
    }

    @Override
    void setConfiguration(Config co) {
        storageType = co.getProperty('bookshelf.storageType', String, 'cloudSQL')
    }
}
