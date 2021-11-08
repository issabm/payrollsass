import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureAdhesionDetailComponent } from './nature-adhesion-detail.component';

describe('NatureAdhesion Management Detail Component', () => {
  let comp: NatureAdhesionDetailComponent;
  let fixture: ComponentFixture<NatureAdhesionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureAdhesionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureAdhesion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureAdhesionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureAdhesionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureAdhesion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureAdhesion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
