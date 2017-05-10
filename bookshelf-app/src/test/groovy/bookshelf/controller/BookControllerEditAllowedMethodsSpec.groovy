package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerEditAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.edit does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.edit(null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    @Ignore
    def "test BookController.edit accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.edit(null)

        then:
        response.status == SC_OK
    }
}
