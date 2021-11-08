import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureEligibiliteDetailComponent } from './nature-eligibilite-detail.component';

describe('NatureEligibilite Management Detail Component', () => {
  let comp: NatureEligibiliteDetailComponent;
  let fixture: ComponentFixture<NatureEligibiliteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureEligibiliteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureEligibilite: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureEligibiliteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureEligibiliteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureEligibilite on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureEligibilite).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
