package com.example.getstarted.basicactions

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.plugins.googlecloud.authorization.GoogleAuthorizationService

@Slf4j
@CompileStatic
class LoginController implements GrailsConfigurationAware {

    static allowedMethods = [index: 'GET']
    public static final String SESSION_ATTRIBUTE_STATE = 'state'
    public static final String SESSION_ATTRIBUTE_LOGIN_DESTINATION = 'loginDestination'

    String defaultLoginDestination

    GoogleAuthorizationService googleAuthorizationService

    def index(String loginDestination) {
        String state = googleAuthorizationService.randomState()
        def url = googleAuthorizationService.authorizationRedirectUrl(state)
        session.setAttribute(SESSION_ATTRIBUTE_STATE, state)
        String destination = loginDestination ?: defaultLoginDestination
        log.info 'logging destination {0}', destination
        session.setAttribute(SESSION_ATTRIBUTE_LOGIN_DESTINATION, destination)
        redirect(url: url)
    }

    @Override
    void setConfiguration(Config co) {
        defaultLoginDestination = co.getProperty('bookshelf.loginDestination', String, '/books')
    }
}
