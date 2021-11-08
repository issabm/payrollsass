import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureAbsenceDetailComponent } from './nature-absence-detail.component';

describe('NatureAbsence Management Detail Component', () => {
  let comp: NatureAbsenceDetailComponent;
  let fixture: ComponentFixture<NatureAbsenceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureAbsenceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureAbsence: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureAbsenceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureAbsenceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureAbsence on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureAbsence).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
