package org.grails.plugins.googlecloud.translate

import com.google.cloud.translate.Translate.TranslateOption
import com.google.cloud.translate.TranslateOptions
import groovy.transform.CompileStatic

@CompileStatic
class GoogleCloudTranslateService {

    String translateTextFromSourceToTarget(String text, String source, String target) {
        TranslateOptions.defaultInstance.service.translate(text,
                TranslateOption.sourceLanguage(source),
                TranslateOption.targetLanguage(target)).translatedText
    }
}
