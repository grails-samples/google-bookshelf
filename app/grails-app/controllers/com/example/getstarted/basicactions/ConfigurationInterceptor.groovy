package com.example.getstarted.basicactions

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@SuppressWarnings('GrailsServletContextReference')
@CompileStatic
class ConfigurationInterceptor implements GrailsConfigurationAware {

    String bucket
    String clientID

    ConfigurationInterceptor() {
        matchAll()
    }

    @SuppressWarnings('LineLength')
    boolean before() {
        String instanceId =  System.getenv().containsKey('GAE_MODULE_INSTANCE') ?: '-1'
        log.info 'ConfigurationInterceptor processing new request for path: {0} and instance {1}', request.requestURI, instanceId

        servletContext['isCloudStorageConfigured'] = bucket as boolean
        servletContext['isAuthConfigured'] = clientID as boolean
        true
    }

    @Override
    void setConfiguration(Config co) {
        bucket = co.getProperty('org.grails.plugins.googlecloud.storage.bucket', String)
        clientID = co.getProperty('bookshelf.clientID', String)
    }
}
