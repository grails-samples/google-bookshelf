import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MdSnackBar, MdSnackBarConfig } from '@angular/material';
import 'rxjs/add/operator/switchMap';

import { BookService } from '../book/book.service';
import { Book } from '../book/book.model';

const SB_CONFIG = new MdSnackBarConfig();
SB_CONFIG.duration = 5000;

@Component({
  selector: 'app-book-route',
  templateUrl: './book-route.component.html',
  styleUrls: ['./book-route.component.css']
})
export class BookRouteComponent implements OnInit {

  book : Book;

  constructor(
    private bookService : BookService,
    private route : ActivatedRoute,
    private router: Router,
    private snackBar: MdSnackBar
  ) { }

  ngOnInit() {
    this.route.params
      .switchMap((params : Params) => this.bookService.getBookById(+params['id']))
      .subscribe(
        result => this.book = result,
        error => {
          this.snackBar.open(error, 'Dismiss', SB_CONFIG);
          this.router.navigate(['/']);
        }
      );

  }

}
