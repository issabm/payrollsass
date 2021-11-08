import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MouvementPaieDetailComponent } from './mouvement-paie-detail.component';

describe('MouvementPaie Management Detail Component', () => {
  let comp: MouvementPaieDetailComponent;
  let fixture: ComponentFixture<MouvementPaieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MouvementPaieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mouvementPaie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MouvementPaieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MouvementPaieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mouvementPaie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mouvementPaie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
