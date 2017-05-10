package bookshelf.business

import bookshelf.model.BookCurator
import bookshelf.model.BookImpl
import bookshelf.model.BookLocalization
import bookshelf.model.BookLocalizationImpl
import org.grails.plugins.googlecloud.vision.GoogleCloudVisionService
import org.springframework.web.multipart.MultipartFile
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@SuppressWarnings('GrailsStatelessService')
@Slf4j
@CompileStatic
class CreateBookWithCoverImageService {

    CloudStorageService cloudStorageService

    GoogleCloudVisionService googleCloudVisionService

    DaoService daoService

    BookLocalization bookLocalizationWithFile(MultipartFile file) {
        try {
            def text = googleCloudVisionService.detectDocumentText(file.inputStream)
            return bookLocalizationWithText(text)

        } catch (FileNotFoundException e) {
            log.error('file not found, thus text could not be extracted', e)
        }
        null
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
        String fileName = UploadUtils.nameForFile(file)
        cloudStorageService.storeMultipartFile(fileName, file)
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
