import { Component, OnInit } from '@angular/core';

import { BookService } from '../book/book.service';
import { Book } from '../book/book.model';

@Component({
  selector: 'app-books-route',
  templateUrl: './books-route.component.html',
  styleUrls: ['./books-route.component.css']
})
export class BooksRouteComponent implements OnInit {

  books: Book[];

  constructor(private bookService: BookService) { }

  ngOnInit() {
    this.bookService.getGeneralBooks().then(books => this.books = books);
  }

}
