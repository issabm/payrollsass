import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandeCalculPaieDetailComponent } from './demande-calcul-paie-detail.component';

describe('DemandeCalculPaie Management Detail Component', () => {
  let comp: DemandeCalculPaieDetailComponent;
  let fixture: ComponentFixture<DemandeCalculPaieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemandeCalculPaieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demandeCalculPaie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemandeCalculPaieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemandeCalculPaieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demandeCalculPaie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demandeCalculPaie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
