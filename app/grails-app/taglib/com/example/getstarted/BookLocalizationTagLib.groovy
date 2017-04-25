package com.example.getstarted

import com.example.getstarted.daos.BookDao
import com.example.getstarted.daos.CloudSqlService
import com.example.getstarted.daos.DatastoreService
import com.example.getstarted.objects.BookLocalization
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.util.logging.Slf4j

@Slf4j
class BookLocalizationTagLib implements GrailsConfigurationAware {

    static namespace = 'bookshelf'

    static defaultEncodeAs = [taglib:'html']

    String storageType

    String defaultLanguageCode

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

    def bookTitle = { args ->
        String languageCode = args.languageCode ?: defaultLanguageCode
        Long bookId = args.id
        BookLocalization bookLocalization = dao.getLocalization(bookId, languageCode)
        out << bookLocalization?.title
    }

    def bookDescription = { args ->
        String languageCode = args.languageCode ?: defaultLanguageCode
        Long bookId = args.id
        BookLocalization bookLocalization = dao.getLocalization(bookId, languageCode)
        out << bookLocalization?.description
    }

    @Override
    void setConfiguration(Config co) {
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
        storageType = co.getProperty('bookshelf.storageType', String, 'cloudSQL')
    }
}
