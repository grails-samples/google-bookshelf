package com.example.getstarted

import com.example.getstarted.daos.DaoService
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

    String defaultLanguageCode

    DaoService daoService

    @Override
    void setConfiguration(Config co) {
        defaultLanguageCode = co.getProperty('bookshelf.defaultLanguageCode', String, 'en')
    }

    BookLocalization bookLocalizationWithFile(MultipartFile file) {
        def text = googleCloudVisionService.detectDocumentText(file.inputStream)
        bookLocalizationWithText(text)
    }

    BookLocalization bookLocalizationWithText(String text) {
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
        if ( bookLocalization ) {
            book.title = bookLocalization.title
            book.description = bookLocalization.description
        }
        daoService.createBook(book)
    }
}
