package bookshelf.view

import grails.test.mixin.TestFor
import spock.lang.Specification

@SuppressWarnings('LineLength')
@TestFor(LangNavbarTagLib)
class LangNavbarTagLibSpec extends Specification {

    void "test repeat"() {
        expect:
        applyTemplate('<bookshelfLocalizations:navbar uri="/books"/>') == '<ul class="nav navbar-nav navbar-right"><li><a href=\'/books?lang=en\'>en</a></li><li><a href=\'/books?lang=es\'>es</a></li><li><a href=\'/books?lang=it\'>it</a></li></ul>'
    }
}
