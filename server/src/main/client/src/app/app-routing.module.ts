import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router';

import {BooksRouteComponent} from "./books-route/books-route.component";
import {MyBooksRouteComponent} from "./my-books-route/my-books-route.component";

const routes: Routes = [
  {path: '', redirectTo: 'books', pathMatch: 'full'},
  {path: 'books', component: BooksRouteComponent},
  {path: 'my-books', component: MyBooksRouteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}