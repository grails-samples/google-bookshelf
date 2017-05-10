package bookshelf.business

import bookshelf.model.Book
import bookshelf.model.BookCurator
import org.springframework.web.multipart.MultipartFile
import groovy.transform.CompileStatic

@CompileStatic
class UpdateBookService {

    CloudStorageService cloudStorageService

    DaoService daoService

    void updateBook(Book book, MultipartFile file, BookCurator bookCurator) {

        if (book.createdById == null || book.createdBy == null ) {
            book.createdBy = bookCurator.createdBy
            book.createdById = bookCurator.createdById
        }

        if ( file && !file.isEmpty() ) {
            String fileName = UploadUtils.nameForFile(file)
            String imageUrl = cloudStorageService.storeMultipartFile(fileName, file)
            book.imageUrl = imageUrl
        }

        daoService.updateBook(book)
    }
}
