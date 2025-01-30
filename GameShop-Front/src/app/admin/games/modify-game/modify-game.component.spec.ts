import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyGameComponent } from './modify-game.component';

describe('ModifyGameComponent', () => {
  let component: ModifyGameComponent;
  let fixture: ComponentFixture<ModifyGameComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModifyGameComponent]
    });
    fixture = TestBed.createComponent(ModifyGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
