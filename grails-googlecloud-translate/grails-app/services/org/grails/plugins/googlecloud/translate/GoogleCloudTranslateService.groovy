package org.grails.plugins.googlecloud.translate

import com.google.cloud.translate.Translate
import com.google.cloud.translate.Translate.TranslateOption
import com.google.cloud.translate.TranslateOptions
import groovy.transform.CompileStatic

@CompileStatic
class GoogleCloudTranslateService {

    Translate translate = TranslateOptions.defaultInstance.service

    String translate(String text, String source, String target) {
        translate.translate(text,
                TranslateOption.sourceLanguage(source),
                TranslateOption.targetLanguage(target)).translatedText
    }
}