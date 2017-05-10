package bookshelf.controller

import bookshelf.model.Book
import bookshelf.model.BookListing
import bookshelf.business.BookListingService
import bookshelf.business.CreateBookWithCoverImageService
import bookshelf.business.UpdateBookService
import bookshelf.business.DaoService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class BookController implements BookCuratorSession {

    static allowedMethods = [index: 'GET',
                             create: 'GET',
                             save: 'POST',
                             edit: 'GET',
                             update: 'POST',
                             delete: 'GET',
                             show: 'GET',
                             mine: 'GET']

    BookListingService bookListingService

    UpdateBookService updateBookService

    CreateBookWithCoverImageService createBookWithCoverImageService

    DaoService daoService

    // tag::index[]
    def index(String cursor) {

        log.info 'Retrieved list of all books'
        BookListing bookListing = bookListingService.findBookListing(cursor, request.locale)
        [books: bookListing.books.result, cursor: bookListing.books.cursor, title: bookListing.title]
    }
    // end::index[]

    // tag::mine[]
    def mine(String cursor) {
        String userId = session[Oauth2CallbackController.SESSION_USER_ID]
        log.info "Retrieved list of all books for user: $userId"
        BookListing bookListing = bookListingService.findBookListingByUser(cursor, request.locale, userId)
        render(view: 'index', model: [books: bookListing.books.result,
                                      cursor: bookListing.books.cursor,
                                      title: bookListing.title])
    }
    // end::mine[]

    // tag::create[]
    @SuppressWarnings('EmptyMethod')
    def create() {
    }
    // end::create[]

    // tag::save[]
    def save(CreateBookCommand cmd) {
        if ( cmd.hasErrors() ) {
            respond cmd.errors
            return
        }

        Long id = createBookWithCoverImageService.saveBookWithCover(cmd.file, bookCurator)

        log.info "Created book ${id}"

        redirect(action: 'show', id: id)
    }
    // end::save[]

    // tag::show[]
    def show(Long id) {
        log.info "Read book with id ${id}"
        [book: daoService.readBook(id)]
    }
    // end::show[]

    // tag::edit[]
    def edit(Long id) {
        [book: daoService.readBook(id)]
    }
    // end::edit[]

    // tag::update[]
    def update(UpdateBookCommand cmd) {
        if ( cmd.hasErrors() ) {
            return
        }
        def book = cmd as Book

        updateBookService.updateBook(book, cmd.file, bookCurator)

        redirect(action: 'show', id: book.id)
    }
    // end::update[]

    // tag::delete[]
    def delete(Long id) {
        daoService.deleteBook(id)
        redirect action: 'index'
    }
    // end::delete[]
}
