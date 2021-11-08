import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConjointDetailComponent } from './conjoint-detail.component';

describe('Conjoint Management Detail Component', () => {
  let comp: ConjointDetailComponent;
  let fixture: ComponentFixture<ConjointDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConjointDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ conjoint: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConjointDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConjointDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load conjoint on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.conjoint).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
