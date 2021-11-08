import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RebriqueDetailComponent } from './rebrique-detail.component';

describe('Rebrique Management Detail Component', () => {
  let comp: RebriqueDetailComponent;
  let fixture: ComponentFixture<RebriqueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RebriqueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rebrique: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RebriqueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RebriqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rebrique on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rebrique).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
