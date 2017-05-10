package bookshelf.controller

import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import spock.lang.Shared
import spock.lang.Specification

@Integration
class HealthControllerIntegrationSpec extends Specification {

    @Shared RestBuilder rest = new RestBuilder()

    def "test /_ah/health responds the message ok, HTTP code and contentType text/plain"() {
        when:
        def resp = rest.get("http://localhost:${serverPort}/_ah/health")

        then:
        resp.status == 200
        resp.text == 'ok'
        (resp.headers['Content-Type'] as String).contains('text/plain')
    }

    def "test /_ah/start responds the message ok, HTTP code and contentType text/plain"() {
        when:
        def resp = rest.get("http://localhost:${serverPort}/_ah/start")

        then:
        resp.status == 200
        resp.text == 'ok'
        (resp.headers['Content-Type'] as String).contains('text/plain')
    }

    def "test /_ah/stop responds the message ok, HTTP code and contentType text/plain"() {
        when:
        def resp = rest.get("http://localhost:${serverPort}/_ah/stop")

        then:
        resp.status == 200
        resp.text == 'ok'
        (resp.headers['Content-Type'] as String).contains('text/plain')
    }

}
