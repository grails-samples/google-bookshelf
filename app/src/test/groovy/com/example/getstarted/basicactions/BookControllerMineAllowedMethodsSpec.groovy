package com.example.getstarted.basicactions

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import com.example.getstarted.daos.DaoService
import spock.lang.Specification
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerMineAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.mine does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.mine(null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    @Ignore
    def "test BookController.mine accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.daoService = Mock(DaoService)
        controller.mine(null)

        then:
        response.status == SC_OK
    }
}