package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerIndexAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index(null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    @Ignore
    def "test BookController.index accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.index(null)

        then:
        response.status == SC_OK
    }
}
