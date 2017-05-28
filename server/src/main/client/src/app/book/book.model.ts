export class Book {
  
  constructor(
    public id: number,
    public title: string,
    public author: string,
    public createdBy: string,
    public createdById: string,
    public publishedDate: string,
    public description: string,
    public imageUrl: string
  ) { }

}