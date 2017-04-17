package com.example.getstarted.basicactions

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic

@CompileStatic
class ConfigurationInterceptor implements GrailsConfigurationAware {

    String bucket
    String clientID

    ConfigurationInterceptor() {
        matchAll()
    }

    boolean before() {
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