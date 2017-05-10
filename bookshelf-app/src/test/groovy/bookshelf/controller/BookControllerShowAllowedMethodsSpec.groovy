package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import bookshelf.business.DaoService
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerShowAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.show does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.show(null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    @Ignore
    def "test BookController.show accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.daoService = Mock(DaoService)
        controller.show(null)

        then:
        response.status == SC_OK
    }
}
