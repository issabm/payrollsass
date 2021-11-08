import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureMvtPaieDetailComponent } from './nature-mvt-paie-detail.component';

describe('NatureMvtPaie Management Detail Component', () => {
  let comp: NatureMvtPaieDetailComponent;
  let fixture: ComponentFixture<NatureMvtPaieDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureMvtPaieDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureMvtPaie: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureMvtPaieDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureMvtPaieDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureMvtPaie on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureMvtPaie).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
