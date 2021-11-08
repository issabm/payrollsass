import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SousTypeContratDetailComponent } from './sous-type-contrat-detail.component';

describe('SousTypeContrat Management Detail Component', () => {
  let comp: SousTypeContratDetailComponent;
  let fixture: ComponentFixture<SousTypeContratDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SousTypeContratDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sousTypeContrat: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SousTypeContratDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SousTypeContratDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sousTypeContrat on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sousTypeContrat).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
