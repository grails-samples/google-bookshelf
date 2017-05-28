import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyBooksRouteComponent } from './my-books-route.component';

describe('MyBooksRouteComponent', () => {
  let component: MyBooksRouteComponent;
  let fixture: ComponentFixture<MyBooksRouteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyBooksRouteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyBooksRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
