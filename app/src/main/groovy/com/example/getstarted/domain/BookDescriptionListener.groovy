package com.example.getstarted.domain

import com.example.getstarted.objects.Book
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

    String target
    String defaultLanguageCode

    BookDescriptionListener(final Datastore datastore) {
        super(datastore)
    }

    @Override
    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof BookLocalizationGormEntity) {
            def bookLocalization = (event.entityObject as BookLocalizationGormEntity)
            if (bookLocalization.languageCode == defaultLanguageCode) {
                    if (
                            event.eventType == EventType.PreInsert ||
                            (event.eventType == EventType.PreUpdate && bookLocalization.isDirty('description')) ||
                            (event.eventType == EventType.PreUpdate && bookLocalization.isDirty('title'))
                    ) {

                        def description = googleCloudTranslateService.translateTextFromSourceToTarget(bookLocalization.description, defaultLanguageCode, target)
                        log.info "Description text: ${bookLocalization.description} | source: ${bookLocalization.description} | target: ${target} | translation: ${description} "

                        def title = googleCloudTranslateService.translateTextFromSourceToTarget(bookLocalization.title, defaultLanguageCode, target)
                        log.info "Title text: ${bookLocalization.description} | source: ${bookLocalization.title} | target: ${target} | translation: ${title} "

                        if ( description != null || title != null ) {
                            BookGormEntity book = bookLocalization.book
                            bookLocalization = book.localizations.find { it.languageCode == target }
                            if (bookLocalization) {
                                bookLocalization.title = title
                                bookLocalization.description = description
                                bookLocalization.save()
                            } else {
                                book.addToLocalizations(new BookLocalizationGormEntity(languageCode: defaultLanguageCode,
                                        title: title,
                                        description: description))
                                book.save()
                            }
                        }
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
        target = co.getProperty('bookshelf.translate.target', String, 'es')
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
    }
}
