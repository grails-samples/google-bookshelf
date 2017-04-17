package com.example.getstarted.basicactions

import com.example.getstarted.daos.CloudSqlService
import com.example.getstarted.daos.DatastoreService
import grails.test.mixin.TestFor
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK

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
        controller.datastoreService = Mock(DatastoreService)
        controller.cloudSqlService = Mock(CloudSqlService)
        controller.save()

        then:
        response.status == SC_OK
    }
}


