import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { NavService } from './nav/nav.service';
import { AppRoutingModule } from "./app-routing.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';

import { AppMaterialModule } from '../material/material.module';
import { BooksRouteComponent } from './books-route/books-route.component';
import { MyBooksRouteComponent } from './my-books-route/my-books-route.component';
import { BooksListComponent } from './books-list/books-list.component';
import { BookComponent } from './book/book.component';
import { BookService } from './book/book.service';
import { BookListItemComponent } from './book-list-item/book-list-item.component';
import { BookRouteComponent } from './book-route/book-route.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    BooksRouteComponent,
    MyBooksRouteComponent,
    BooksListComponent,
    BookComponent,
    BookListItemComponent,
    BookRouteComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    AppMaterialModule
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}, NavService, BookService],
  bootstrap: [AppComponent]
})
export class AppModule { }
