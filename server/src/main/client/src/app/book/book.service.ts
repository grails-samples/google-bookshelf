import { Injectable } from '@angular/core';
import { Book } from './book.model';

@Injectable()
export class BookService {

  constructor() { }

  getGeneralBooks(): Promise<Book[]> {
    return Promise.resolve([
      new Book(
        1, 
        'Title 1', 
        'Author 1', 
        'createdBy 1', 
        'createdById 1', 
        'publishedDate 1', 
        'description 1', 
        'imageUrl 1'
      ),
      new Book(
        2, 
        'Title 2', 
        'Author 2', 
        'createdBy 2', 
        'createdById 2', 
        'publishedDate 2', 
        'description 2', 
        'imageUrl 2'
      )
    ]);
  }

  getBooksForUser(userId: string): Promise<Book[]> {
    return Promise.resolve([
      new Book(
        3, 
        `Title for ${userId}`,
        `Author for ${userId}`,
        `createdBy for ${userId}`,
        `createdById for ${userId}`,
        `publishedDate for ${userId}`,
        `description for ${userId}`,
        `imageUrl for ${userId}`
      )
    ]);
  }

}
