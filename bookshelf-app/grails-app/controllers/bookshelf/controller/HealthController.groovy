package bookshelf.controller

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

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
@Slf4j
@CompileStatic
class HealthController implements GrailsConfigurationAware {
    String contentType

    static allowedMethods = [index: 'GET']

    def index() {
        log.info "Got request to my ok servlet for ${request.requestURI}"

        response.contentType = contentType
        render 'ok'
    }

    @Override
    void setConfiguration(Config co) {
        contentType = co.getProperty('grails.mime.types.text', String, 'text/plain')
    }
}
