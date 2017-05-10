package bookshelf.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
@CompileStatic
class BookLocalizationImpl implements BookLocalization {
    String title
    String description
    String languageCode
}
