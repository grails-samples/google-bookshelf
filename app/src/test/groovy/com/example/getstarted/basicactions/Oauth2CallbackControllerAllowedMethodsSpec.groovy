package com.example.getstarted.basicactions

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY

@TestFor(Oauth2CallbackController)
class Oauth2CallbackControllerAllowedMethodsSpec extends Specification {

    @Unroll
    def "test Oauth2CallbackController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index(null, null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test TestController.index accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.index(null, null)

        then:
        response.status == SC_MOVED_TEMPORARILY
    }
}
