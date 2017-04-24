package com.example.getstarted.domain

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

@CompileStatic
class BookDescriptionListener extends AbstractPersistenceEventListener {

    @Autowired
    GoogleCloudTranslateService googleCloudTranslateService

    BookDescriptionListener(final Datastore datastore) {
        super(datastore)
    }

    @Override
    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof BookGormEntity) {
            def book = (event.entityObject as BookGormEntity)
            if (book.description && (event.eventType == EventType.PreInsert || (event.eventType == EventType.PreUpdate && book.isDirty('description')))) {
                event.getEntityAccess().setProperty("descriptionSpanish", googleCloudTranslateService.translate(book.description, 'en', 'es'))
            }
        }
    }

    @Override
    boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        eventType == PreUpdateEvent || eventType == PreInsertEvent
    }
}
