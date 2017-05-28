import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BooksRouteComponent } from './books-route.component';

describe('BooksRouteComponent', () => {
  let component: BooksRouteComponent;
  let fixture: ComponentFixture<BooksRouteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BooksRouteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BooksRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
