package com.example.getstarted.basicactions

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic

/**
 * Controller to respond to health checks.
 *
 * <p>This controller responds to all requests with the message "ok" and HTTP code
 * 200. It is periodically run by the health checker to determine if the server
 * is still responding to requests.
 *
 * <p>A health check servlet like this is required when using the load balancer
 * for Google Compute Engine, but App Engine flexible environment provides one
 * for you if you do not supply one.
 */
@CompileStatic
class HealthController implements GrailsConfigurationAware {
    String contentType

    def index() {
        response.contentType = contentType
        render 'ok'
    }

    @Override
    void setConfiguration(Config co) {
        contentType = co.getRequiredProperty('grails.mime.types.text', String)
    }
}