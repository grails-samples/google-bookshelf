package com.example.getstarted.basicactions

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

@CompileStatic
class BookController implements GrailsConfigurationAware {

    String storageType

    static allowedMethods = [index: 'GET',
                             create: 'GET',
                             save: 'POST',
                             edit: 'GET',
                             update: 'POST',
                             delete: 'GET',
                             show: 'GET']

    DatastoreService datastoreService

    CloudSqlService cloudSqlService

    private BookDao getDao() {

        if ( storageType == 'datastore' ) {
            return datastoreService
        }

        if ( storageType == 'cloudSQL' ) {
            return cloudSqlService
        }

        return datastoreService
    }

    def index(String cursor) {
        Result<Book> result = dao.listBooks(cursor)
        [books: result.result, cursor: result.cursor]
    }

    def create() {}

    def save(CreateBookCommand cmd) {
        if(cmd.hasErrors()) {
            return
        }

        def book = cmd as Book
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
        dao.updateBook(book)
        redirect(action: 'show', id: book.id)
    }

    def delete(Long id) {
        dao.deleteBook(id)
        redirect action: 'index'
    }

    @Override
    void setConfiguration(Config co) {
        storageType = co.getRequiredProperty('bookshelf.storageType', String)
    }
}