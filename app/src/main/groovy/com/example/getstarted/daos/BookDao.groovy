package com.example.getstarted.daos

import com.example.getstarted.objects.Book
import com.example.getstarted.objects.Result
import groovy.transform.CompileStatic

import java.sql.SQLException

@CompileStatic
interface BookDao {
    Long createBook(Book book)

    Book readBook(Long bookId)

    void updateBook(Book book)

    void deleteBook(Long bookId)

    Result<Book> listBooks(String startCursor)

    Result<Book> listBooksByUser(String userId, String startCursor)
}
