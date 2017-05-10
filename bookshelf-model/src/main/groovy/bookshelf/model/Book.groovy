package bookshelf.model

import groovy.transform.CompileStatic

@CompileStatic
interface Book {
    String getTitle()
    String getAuthor()
    String getCreatedBy()
    void setCreatedBy(String createdBy)
    String getCreatedById()
    void setCreatedById(String createdById)
    String getPublishedDate()
    String getDescription()
    Long getId()
    String getImageUrl()
    void setImageUrl(String imageUrl)
}
