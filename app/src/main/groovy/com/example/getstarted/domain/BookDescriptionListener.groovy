package com.example.getstarted.domain

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.util.logging.Slf4j
import org.grails.plugins.googlecloud.translate.GoogleCloudTranslateService
import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener
import org.grails.datastore.mapping.engine.event.EventType
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import groovy.transform.CompileStatic

@Slf4j
@CompileStatic
class BookDescriptionListener extends AbstractPersistenceEventListener implements GrailsConfigurationAware {

    @Autowired
    GoogleCloudTranslateService googleCloudTranslateService

    String source
    String target

    BookDescriptionListener(final Datastore datastore) {
        super(datastore)
    }

    @Override
    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof BookGormEntity) {
            def book = (event.entityObject as BookGormEntity)
            if (book.description && (event.eventType == EventType.PreInsert
                    || (event.eventType == EventType.PreUpdate && book.isDirty('description')))) {
                def text = googleCloudTranslateService.translateTextFromSourceToTarget(book.description, source, target)
                if ( text ) {
                log.info "text: ${book.description} | source: ${source} | target: ${target} | translation: ${text} "
                    event.entityAccess.setProperty('descriptionSpanish', text)
                }

            }
        }
    }

    @Override
    boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        eventType == PreUpdateEvent || eventType == PreInsertEvent
    }

    @Override
    void setConfiguration(Config co) {
        source = co.getProperty('bookshelf.translation.source', String, 'en')
        target = co.getProperty('bookshelf.translation.target', String, 'es')

    }
}
