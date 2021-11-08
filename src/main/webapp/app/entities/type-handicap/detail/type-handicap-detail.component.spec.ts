import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeHandicapDetailComponent } from './type-handicap-detail.component';

describe('TypeHandicap Management Detail Component', () => {
  let comp: TypeHandicapDetailComponent;
  let fixture: ComponentFixture<TypeHandicapDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypeHandicapDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typeHandicap: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TypeHandicapDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypeHandicapDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typeHandicap on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typeHandicap).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
