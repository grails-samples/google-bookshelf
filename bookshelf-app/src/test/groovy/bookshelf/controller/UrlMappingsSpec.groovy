package bookshelf.controller

import spock.lang.Specification
import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Ignore

@TestFor(UrlMappings)
@Mock(Oauth2CallbackController)
class UrlMappingsSpec extends Specification {

    @Ignore
    def "test UrlMappings"() {
        expect:
        assertUrlMapping('/oauth2callback?state=yyyy&code=xxx',controller: 'oauth2Callback', action: 'index')
    }
}
