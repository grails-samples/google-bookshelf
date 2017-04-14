package com.example.getstarted.objects

import groovy.transform.CompileStatic

@CompileStatic
interface Book {
    String getTitle()
    String getAuthor()
    String getCreatedBy()
    String getCreatedById()
    String getPublishedDate()
    String getDescription()
    Long getId()
    String getImageUrl()
}