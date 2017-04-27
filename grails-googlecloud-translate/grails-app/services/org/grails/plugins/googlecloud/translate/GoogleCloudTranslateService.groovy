package org.grails.plugins.googlecloud.translate

import com.google.cloud.translate.Translate.TranslateOption
import com.google.cloud.translate.TranslateException
import com.google.cloud.translate.TranslateOptions
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class GoogleCloudTranslateService {

    @SuppressWarnings('ReturnNullFromCatchBlock')
    String translateTextFromSourceToTarget(String text, String source, String target) {
        if ( !text ) {
            return text
        }
        try {
            return TranslateOptions.defaultInstance.service.translate(text,
                    TranslateOption.sourceLanguage(source),
                    TranslateOption.targetLanguage(target)).translatedText
        } catch (TranslateException e) {
            log.error(e.message, e)
            return null
        }
    }
}
