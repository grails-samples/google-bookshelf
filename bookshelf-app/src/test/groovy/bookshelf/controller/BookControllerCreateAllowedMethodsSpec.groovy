package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerCreateAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.create does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.create()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test BookController.create accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.create()

        then:
        response.status == SC_OK
    }
}
