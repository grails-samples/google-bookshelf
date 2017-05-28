import { Component, OnInit } from '@angular/core';
import { BookService } from '../book/book.service';
import { Book } from '../book/book.model';

@Component({
  selector: 'app-my-books-route',
  templateUrl: './my-books-route.component.html',
  styleUrls: ['./my-books-route.component.css']
})
export class MyBooksRouteComponent implements OnInit {

  books: Book[];

  constructor(private bookService: BookService) { }

  ngOnInit() {
    this.bookService.getBooksForUser('12345').then(books => this.books = books);
  }

}
