package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(HealthController)
class HealthControllerAllowedMethodsSpec extends Specification {

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
