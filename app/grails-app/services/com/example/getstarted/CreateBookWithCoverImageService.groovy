package com.example.getstarted

import com.example.getstarted.daos.BookDao
import com.example.getstarted.daos.CloudSqlService
import com.example.getstarted.daos.DatastoreService
import com.example.getstarted.objects.BookCurator
import com.example.getstarted.objects.BookImpl
import com.example.getstarted.objects.BookLocalization
import com.example.getstarted.objects.BookLocalizationImpl
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import org.grails.plugins.googlecloud.storage.GoogleCloudStorageService
import org.grails.plugins.googlecloud.vision.GoogleCloudVisionService
import org.springframework.web.multipart.MultipartFile
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@SuppressWarnings('GrailsStatelessService')
@Slf4j
@CompileStatic
class CreateBookWithCoverImageService implements GrailsConfigurationAware {

    UploadBookCoverService uploadBookCoverService

    GoogleCloudStorageService googleCloudStorageService

    GoogleCloudVisionService googleCloudVisionService

    DatastoreService datastoreService

    CloudSqlService cloudSqlService

    String storageType

    String defaultLanguageCode

    @Override
    void setConfiguration(Config co) {
        storageType = co.getProperty('bookshelf.storageType', String, 'cloudSQL')
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
    }

    private BookDao getDao() {
        if ( storageType == 'datastore' ) {
            return datastoreService
        }
        if ( storageType == 'cloudSQL' ) {
            return cloudSqlService
        }
        cloudSqlService
    }

    BookLocalization bookLocalizationWithFile(MultipartFile file) {
        def text = googleCloudVisionService.detectDocumentText(file.inputStream)
        bookLocalizationWithText(text)
    }

    BookLocalization bookLocalizationWithText(String text){
        if (text == null ) {
            return null
        }
        final int titleMaxSize = 255

        String title = null
        String description = null

        if ( text.size() >= (titleMaxSize + 1) ) {
            title = text[0 .. (titleMaxSize - 1)]
            description = text[titleMaxSize .. (text.size() - 1)]

        } else {
            title = text
        }

        new BookLocalizationImpl(title: title, description: description)
    }

    String imageUrlWithFile(MultipartFile file) {
        String fileName = uploadBookCoverService.nameForFile(file)
        googleCloudStorageService.storeMultipartFile(fileName, file)
    }

    Long saveBookWithCover(MultipartFile file, BookCurator curator) {
        def book = new BookImpl()
        book.createdBy = curator.createdBy
        book.createdById = curator.createdById
        book.imageUrl = imageUrlWithFile(file)
        def bookLocalization = bookLocalizationWithFile(file)
        book.title = bookLocalization.title
        book.description = bookLocalization.description
        dao.createBook(book)
    }
}
