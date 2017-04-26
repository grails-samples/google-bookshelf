package com.example.getstarted.objects

import groovy.transform.CompileStatic

@CompileStatic
class BookLocalizationImpl implements BookLocalization {
    String title
    String description
    String languageCode
}
