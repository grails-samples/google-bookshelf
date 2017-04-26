package test.org.grails.plugins.googlecloud.translate

import grails.test.mixin.TestFor
import org.grails.plugins.googlecloud.translate.GoogleCloudTranslateService
import spock.lang.Specification

@TestFor(GoogleCloudTranslateService)
class GoogleCloudTranslateServiceSpec extends Specification {

    def "test translateTextFromSourceToTarget for null input returns null"() {
        when:
        def response = service.translateTextFromSourceToTarget(null, 'en', 'es')

        then:
        response == null
        noExceptionThrown()
    }
}
