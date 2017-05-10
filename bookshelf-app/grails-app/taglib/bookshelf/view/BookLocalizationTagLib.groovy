package bookshelf.view

import bookshelf.business.DaoService
import bookshelf.model.BookLocalization
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.util.logging.Slf4j

@Slf4j
class BookLocalizationTagLib implements GrailsConfigurationAware {

    static namespace = 'bookshelf'

    static defaultEncodeAs = [taglib: 'html']

    String defaultLanguageCode

    DaoService daoService

    def bookTitle = { args ->
        String languageCode = args.languageCode ?: defaultLanguageCode
        Long bookId = args.id
        BookLocalization bookLocalization = daoService.getLocalization(bookId, languageCode)
        out << bookLocalization?.title
    }

    def bookDescription = { args ->
        String languageCode = args.languageCode ?: defaultLanguageCode
        Long bookId = args.id
        BookLocalization bookLocalization = daoService.getLocalization(bookId, languageCode)
        out << bookLocalization?.description
    }

    @Override
    void setConfiguration(Config co) {
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
    }
}
