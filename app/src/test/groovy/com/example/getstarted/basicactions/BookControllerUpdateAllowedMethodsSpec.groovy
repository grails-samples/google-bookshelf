package com.example.getstarted.basicactions

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import com.example.getstarted.daos.CloudSqlService
import com.example.getstarted.daos.DatastoreService
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(BookController)
class BookControllerUpdateAllowedMethodsSpec extends Specification {

    @Unroll
    def "test BookController.update does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.update(null)

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    @Ignore
    def "test BookController.update accepts POST requests"() {
        when:
        request.method = 'GET'
        controller.datastoreService = Mock(DatastoreService)
        controller.cloudSqlService = Mock(CloudSqlService)
        controller.update(null)

        then:
        response.status == SC_OK
    }
}
