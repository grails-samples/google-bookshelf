package bookshelf.controller

import bookshelf.model.BookCurator
import groovy.transform.CompileStatic

import javax.servlet.http.HttpSession

@CompileStatic
trait BookCuratorSession {

    abstract HttpSession getSession()

    BookCurator getBookCurator() {
        BookCurator curator = new BookCurator()
        if ( session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN] ) {
            curator.createdBy = session[Oauth2CallbackController.SESSION_USER_EMAIL]
            curator.createdById = session[Oauth2CallbackController.SESSION_USER_ID]
        }
        curator
    }
}
