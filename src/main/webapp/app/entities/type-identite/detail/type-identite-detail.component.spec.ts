import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeIdentiteDetailComponent } from './type-identite-detail.component';

describe('TypeIdentite Management Detail Component', () => {
  let comp: TypeIdentiteDetailComponent;
  let fixture: ComponentFixture<TypeIdentiteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypeIdentiteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typeIdentite: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypeIdentiteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypeIdentiteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typeIdentite on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typeIdentite).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
