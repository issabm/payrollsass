import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoldeAbsenceDetailComponent } from './solde-absence-detail.component';

describe('SoldeAbsence Management Detail Component', () => {
  let comp: SoldeAbsenceDetailComponent;
  let fixture: ComponentFixture<SoldeAbsenceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SoldeAbsenceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ soldeAbsence: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SoldeAbsenceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SoldeAbsenceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load soldeAbsence on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.soldeAbsence).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
