import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookRouteComponent } from './book-route.component';

describe('BookRouteComponent', () => {
  let component: BookRouteComponent;
  let fixture: ComponentFixture<BookRouteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookRouteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
