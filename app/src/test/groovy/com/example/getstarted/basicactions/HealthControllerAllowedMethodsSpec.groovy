package com.example.getstarted.basicactions

import grails.test.mixin.TestFor
import org.grails.plugins.googlecloud.authorization.GoogleAuthorizationService
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK

@TestFor(HealthController)
class HealthControllerAllowedMethodsSpec extends Specification {
    static loadExternalBeans = true

    @Unroll
    def "test HealthController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test HealthController.index accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == SC_OK
    }
}

