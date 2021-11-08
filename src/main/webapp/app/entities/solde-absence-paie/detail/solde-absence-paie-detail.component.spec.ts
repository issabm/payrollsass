import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoldeAbsencePaieDetailComponent } from './solde-absence-paie-detail.component';

describe('SoldeAbsencePaie Management Detail Component', () => {
  let comp: SoldeAbsencePaieDetailComponent;
  let fixture: ComponentFixture<SoldeAbsencePaieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SoldeAbsencePaieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ soldeAbsencePaie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SoldeAbsencePaieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SoldeAbsencePaieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load soldeAbsencePaie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.soldeAbsencePaie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
