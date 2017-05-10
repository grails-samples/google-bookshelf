package bookshelf.model

import groovy.transform.CompileStatic

@CompileStatic
interface BookLocalization {
    String getTitle()
    String getDescription()
    String getLanguageCode()
}
