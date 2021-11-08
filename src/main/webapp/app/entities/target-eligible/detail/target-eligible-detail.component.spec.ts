import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TargetEligibleDetailComponent } from './target-eligible-detail.component';

describe('TargetEligible Management Detail Component', () => {
  let comp: TargetEligibleDetailComponent;
  let fixture: ComponentFixture<TargetEligibleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TargetEligibleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ targetEligible: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TargetEligibleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TargetEligibleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load targetEligible on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.targetEligible).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
