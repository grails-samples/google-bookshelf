package com.example.getstarted.basicactions

import com.example.getstarted.UploadBookCoverService
import com.example.getstarted.basicactions.CreateBookCommand
import com.example.getstarted.basicactions.UpdateBookCommand
import com.example.getstarted.daos.BookDao
import com.example.getstarted.daos.CloudSqlService
import com.example.getstarted.daos.DatastoreService
import com.example.getstarted.objects.Book
import com.example.getstarted.objects.Result
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import org.grails.plugins.googlecloud.storage.GoogleCloudStorageService
import org.springframework.context.MessageSource

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

    DatastoreService datastoreService

    CloudSqlService cloudSqlService

    GoogleCloudStorageService googleCloudStorageService

    UploadBookCoverService uploadBookCoverService

    MessageSource messageSource

    private BookDao getDao() {

        if ( storageType == 'datastore' ) {
            return datastoreService
        }

        if ( storageType == 'cloudSQL' ) {
            return cloudSqlService
        }

        return cloudSqlService
    }

    def index(String cursor) {
        Result<Book> result = dao.listBooks(cursor)
        String title = messageSource.getMessage('books', null, 'Books', request.locale)
        [books: result.result, cursor: result.cursor, title: title]
    }

    def create() {}

    def save(CreateBookCommand cmd) {
        if(cmd.hasErrors()) {
            return
        }

        def book = cmd as Book

        if ( session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN] ) {
            book.createdBy = session[Oauth2CallbackController.SESSION_USER_EMAIL]
            book.createdById = session[Oauth2CallbackController.SESSION_USER_ID]
        }

        if ( cmd.file ) {
            String fileName = uploadBookCoverService.nameForFile(cmd.file)
            String imageUrl = googleCloudStorageService.storeMultipartFile(fileName, cmd.file)
            book.imageUrl = imageUrl
        }
        Long id = dao.createBook(book)

        redirect(action: 'show', id: id)
    }

    def show(Long id) {
        [book: dao.readBook(id)]
    }

    def edit(Long id) {
        [book: dao.readBook(id)]
    }

    def update(UpdateBookCommand cmd) {
        if(cmd.hasErrors()) {
            return
        }
        def book = cmd as Book

        if ( session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN] ) {
            book.createdBy = session[Oauth2CallbackController.SESSION_USER_EMAIL]
            book.createdById = session[Oauth2CallbackController.SESSION_USER_ID]
        }

        if ( cmd.file ) {
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
        String title = messageSource.getMessage('books.mine', null, 'My Books', request.locale)
        render(view: 'index', model: [books: result.result, cursor: result.cursor, title: title])
    }

    @Override
    void setConfiguration(Config co) {
        storageType = co.getProperty('bookshelf.storageType', String, 'cloudSQL')
    }
}