package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Unroll

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
