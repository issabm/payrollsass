import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EligibiliteExcludeDetailComponent } from './eligibilite-exclude-detail.component';

describe('EligibiliteExclude Management Detail Component', () => {
  let comp: EligibiliteExcludeDetailComponent;
  let fixture: ComponentFixture<EligibiliteExcludeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EligibiliteExcludeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eligibiliteExclude: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EligibiliteExcludeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EligibiliteExcludeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eligibiliteExclude on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eligibiliteExclude).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
