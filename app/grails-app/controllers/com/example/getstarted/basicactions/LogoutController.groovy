package com.example.getstarted.basicactions

import groovy.transform.CompileStatic

@CompileStatic
class LogoutController {

    static allowedMethods = [index: 'GET']

    def index() {
        session.invalidate()
        redirect(controller: 'book', action: 'index')
    }
}
