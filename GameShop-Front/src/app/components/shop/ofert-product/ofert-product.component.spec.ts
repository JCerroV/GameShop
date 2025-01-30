import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfertProductComponent } from './ofert-product.component';

describe('OfertProductComponent', () => {
  let component: OfertProductComponent;
  let fixture: ComponentFixture<OfertProductComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OfertProductComponent]
    });
    fixture = TestBed.createComponent(OfertProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
