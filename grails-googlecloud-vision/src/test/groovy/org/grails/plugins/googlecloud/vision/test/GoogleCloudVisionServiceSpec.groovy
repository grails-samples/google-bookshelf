package org.grails.plugins.googlecloud.vision.test

import grails.test.mixin.TestFor
import org.grails.plugins.googlecloud.vision.GoogleCloudVisionService
import spock.lang.IgnoreIf
import spock.lang.Specification

@TestFor(GoogleCloudVisionService)
class GoogleCloudVisionServiceSpec extends Specification {

    @IgnoreIf( { System.getenv('TRAVIS') as boolean } )
    @SuppressWarnings('JavaIoPackageAccess')
    def "test image text extraction"() {
        when:
        def f = new File('src/test/groovy/org/grails/plugins/googlecloud/vision/test/pratical-grails-3-book-cover.png')

        then:
        f.exists()

        when:
        def text = service.detectDocumentText(f.newInputStream())

        then:
        text == 'PRACTICAL | GRAILS 3 A HANDS ON GUIDE TO GRAILS BY ERIC HELGESON First book dedicated to Grails 3'

        when:
        f = new File('src/test/groovy/org/grails/plugins/googlecloud/vision/test/gdsl.jpg')

        then:
        f.exists()

        when:
        text = service.detectDocumentText(f.newInputStream())

        then:
        text?.contains('Groovy for Domain-Specific languages')
    }
}
