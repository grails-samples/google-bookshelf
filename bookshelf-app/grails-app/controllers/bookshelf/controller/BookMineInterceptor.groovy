package bookshelf.controller

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class BookMineInterceptor {

    BookMineInterceptor() {
        match(controller: 'book', action: 'mine')
    }

    boolean before() {
        String token = session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN]
        String state = session[LoginController.SESSION_ATTRIBUTE_STATE]
        if ( !token && !state ) {
            log.info 'token not detected, setting loginDestination to /books/mine'
            redirect(controller: 'login', parameters: [loginDestination: '/books/mine'])
            return false
        }
        true
    }
}
