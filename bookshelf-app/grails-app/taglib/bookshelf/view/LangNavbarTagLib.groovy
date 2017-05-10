package bookshelf.view

import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class LangNavbarTagLib implements GrailsConfigurationAware {

    static namespace = 'bookshelfLocalizations'

    static defaultEncodeAs = [taglib: 'none']

    List<String> localizations
    String defaultLanguageCode

    def navbar = { args ->
        String uri = args.uri
        out << '<ul class="nav navbar-nav navbar-right">'
        for (String language : (localizations + defaultLanguageCode).sort() ) {
            out << "<li><a href='${uri}?lang=${language}'>${language}</a></li>" as String
        }
        out << '</ul>'
    }

    @Override
    void setConfiguration(Config co) {
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
        localizations = co.getProperty('bookshelf.localizations', List)
    }
}
