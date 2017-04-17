package com.example.getstarted.basicactions

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY
import grails.test.mixin.TestFor
import org.grails.plugins.googlecloud.authorization.GoogleAuthorizationService
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(LoginController)
class LoginControllerAllowedMethodsSpec extends Specification {

    @Unroll
    def "test LoginController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index(null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    @Ignore
    def "test LoginController.index accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.googleAuthorizationService = Mock(GoogleAuthorizationService)
        controller.index(null)

        then:
        response.status == SC_MOVED_TEMPORARILY
    }
}
