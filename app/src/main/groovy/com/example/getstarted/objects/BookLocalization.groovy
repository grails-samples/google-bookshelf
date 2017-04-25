package com.example.getstarted.objects

import groovy.transform.CompileStatic

@CompileStatic
interface BookLocalization {
    String getTitle()
    String getDescription()
    String getLanguageCode()
}