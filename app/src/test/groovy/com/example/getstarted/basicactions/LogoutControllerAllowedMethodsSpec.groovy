package com.example.getstarted.basicactions

import grails.test.mixin.TestFor
import org.grails.plugins.googlecloud.authorization.GoogleAuthorizationService
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY

@TestFor(LogoutController)
class LogoutControllerAllowedMethodsSpec extends Specification {

    @Unroll
    def "test LogoutController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test LogoutController.index accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }
}
