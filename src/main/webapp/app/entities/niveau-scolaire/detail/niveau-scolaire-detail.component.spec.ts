import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NiveauScolaireDetailComponent } from './niveau-scolaire-detail.component';

describe('NiveauScolaire Management Detail Component', () => {
  let comp: NiveauScolaireDetailComponent;
  let fixture: ComponentFixture<NiveauScolaireDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NiveauScolaireDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ niveauScolaire: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NiveauScolaireDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NiveauScolaireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load niveauScolaire on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.niveauScolaire).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
