package bookshelf.controller

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import bookshelf.business.DaoService
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerSaveAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.save does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.save()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    @Ignore
    def "test BookController.save accepts POST requests"() {
        when:
        request.method = 'POST'
        controller.daoService = Mock(DaoService)
        controller.save()

        then:
        response.status == SC_OK
    }
}
