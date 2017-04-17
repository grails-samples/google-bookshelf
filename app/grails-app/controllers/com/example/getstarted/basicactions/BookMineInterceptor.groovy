package com.example.getstarted.basicactions

import groovy.transform.CompileStatic

@CompileStatic
class BookMineInterceptor {

    BookMineInterceptor() {
        match(controller: 'book', action: 'mine')
    }

    boolean before() {
        String token = session[Oauth2CallbackController.SESSION_ATTRIBUTE_TOKEN]
        String state = session[LoginController.SESSION_ATTRIBUTE_STATE]
        if ( !token && !state ) {
            redirect(controller: 'login', parameters: [loginDestination: '/books/mine'])
            return false
        }
        true
    }
}
