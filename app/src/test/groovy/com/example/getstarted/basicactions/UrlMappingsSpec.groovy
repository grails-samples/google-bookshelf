package com.example.getstarted.basicactions

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Ignore
import spock.lang.Specification

@TestFor(UrlMappings)
@Mock(Oauth2CallbackController)
class UrlMappingsSpec extends Specification {

    @Ignore
    def "test UrlMappings"() {
        expect:
        assertUrlMapping('/oauth2callback?state=yyyy&code=xxx',controller: 'oauth2Callback', action: 'index')
    }

}