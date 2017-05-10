package bookshelf.persistence

import bookshelf.model.Book
import bookshelf.model.BookDao
import bookshelf.model.BookImpl
import bookshelf.model.BookLocalization
import bookshelf.model.Result

import com.google.cloud.datastore.Cursor
import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.FullEntity
import com.google.cloud.datastore.IncompleteKey
import com.google.cloud.datastore.Key
import com.google.cloud.datastore.KeyFactory
import com.google.cloud.datastore.Query
import com.google.cloud.datastore.QueryResults
import com.google.cloud.datastore.StructuredQuery
import com.google.cloud.datastore.StructuredQuery.OrderBy
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@SuppressWarnings('GrailsStatelessService')
@CompileStatic
class DatastoreService implements BookDao, GrailsConfigurationAware {
    static final String BOOK_KIND = 'Book'
    int limit
    String orderBy

    Datastore datastore = DatastoreOptions.defaultInstance.service // Authorized Datastore service
    KeyFactory keyFactory = datastore.newKeyFactory().setKind(BOOK_KIND)  // Is used for creating keys later

    Book entityToBook(Entity entity) {
        BookImpl.builder()
                .author(entity.getString(BookProperties.AUTHOR))
                //.description(entity.getString(BookProperties.DESCRIPTION))
                .id(entity.key.id)
                .publishedDate(entity.getString(BookProperties.PUBLISHED_DATE))
                //.title(entity.getString(BookProperties.TITLE))
                .build()
    }

    @CompileDynamic
    @Override
    Long createBook(Book book) {
        IncompleteKey key = keyFactory.newKey()                           // Key will be assigned once written
        FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key)  // Create the Entity
                .set(BookProperties.AUTHOR, book.author)                  // Add Property ("author", book.getAuthor())
                //.set(BookProperties.DESCRIPTION, book.description)
                .set(BookProperties.PUBLISHED_DATE, book.publishedDate)
                //.set(BookProperties.TITLE, book.title)
                .build()
        Entity bookEntity = datastore.add(incBookEntity)                 // Save the Entity
        bookEntity.key.id                                      // The ID of the Key
    }

    @Override
    Book readBook(Long bookId) {
        Entity bookEntity = datastore.get(keyFactory.newKey(bookId)) // Load an Entity for Key(id)
        entityToBook(bookEntity)
    }

    @Override
    void updateBook(Book book) {
        Key key = keyFactory.newKey(book.id) // From a book, create a Key
        Entity entity = Entity.newBuilder(key)    // Convert Book to an Entity
                .set(BookProperties.AUTHOR, book.author)
                .set(BookProperties.PUBLISHED_DATE, book.publishedDate)
                //.set(BookProperties.DESCRIPTION, book.description)
                //.set(BookProperties.TITLE, book.title)
                .build()
        datastore.update(entity)
    }

    @Override
    void deleteBook(Long bookId) {
        Key key = keyFactory.newKey(bookId) // Create the Key
        datastore.delete(key)
    }

    @Override
    Result<Book> listBooks(String startCursorString) {
        Cursor startCursor = null
        if ( startCursorString ) {
            startCursor = Cursor.fromUrlSafe(startCursorString)    // Where we left off
        }
        Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
                .setKind(BOOK_KIND)                                 // We only care about Books
                .setLimit(this.limit)                                      // Only show 10 at a time
                .setStartCursor(startCursor)                      // Where we left off
                .setOrderBy(OrderBy.asc(orderBy))
                .build()
        QueryResults<Entity> resultList = datastore.run(query)   // Run the query
        List<Book> resultBooks = entitiesToBooks(resultList)     // Retrieve and convert Entities
        Cursor cursor = resultList.cursorAfter                   // Where to start next time
        if (cursor != null && resultBooks.size() == limit) {     // Are we paging? Save Cursor
            String cursorString = cursor.toUrlSafe()            // Cursors are WebSafe
            return new Result<Book>(resultBooks, cursorString)
        }
        new Result<Book>(resultBooks)
    }

    @Override
    Result<Book> listBooksByUser(String userId, String startCursorString) {
        Cursor startCursor = null
        if ( startCursorString ) {
            startCursor = Cursor.fromUrlSafe(startCursorString)    // Where we left off
        }
        Query<Entity> query = Query.newEntityQueryBuilder()          // Build the Query
                .setKind(BOOK_KIND)                                        // We only care about Books
                .setFilter(StructuredQuery.PropertyFilter.eq(BookProperties.CREATED_BY_ID, userId))// Only for this user
                .setLimit(limit)                                            // Only show 10 at a time
                .setStartCursor(startCursor)                             // Where we left off
                // a custom datastore index is required since you are filtering by one property
                // but ordering by another
                .setOrderBy(OrderBy.asc(orderBy))
                .build()
        QueryResults<Entity> resultList = datastore.run(query)   // Run the Query
        List<Book> resultBooks = entitiesToBooks(resultList)     // Retrieve and convert Entities
        Cursor cursor = resultList.cursorAfter                   // Where to start next time
        if (cursor != null && resultBooks.size() == limit) {     // Are we paging? Save Cursor
            String cursorString = cursor.toUrlSafe()             // Cursors are WebSafe
            return new Result<>(resultBooks, cursorString)
        }
        new Result<>(resultBooks)
    }

    @Override
    BookLocalization getLocalization(Long bookId, String languageCode) {
        // TODO Implement this
    }

    private List<Book> entitiesToBooks(QueryResults<Entity> resultList) {
        List<Book> resultBooks = []
        while (resultList.hasNext()) {  // We still have data
            resultBooks << entityToBook(resultList.next()) // Add the Book to the List
        }
        resultBooks
    }

    @Override
    void setConfiguration(Config co) {
        limit = co.getProperty('bookshelf.limit', Integer, 10)
        orderBy = co.getProperty('bookshelf.orderBy', String, BookProperties.TITLE)
    }
}
