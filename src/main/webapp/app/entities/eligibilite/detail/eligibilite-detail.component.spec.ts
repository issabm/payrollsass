import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EligibiliteDetailComponent } from './eligibilite-detail.component';

describe('Eligibilite Management Detail Component', () => {
  let comp: EligibiliteDetailComponent;
  let fixture: ComponentFixture<EligibiliteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EligibiliteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eligibilite: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EligibiliteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EligibiliteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eligibilite on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eligibilite).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
