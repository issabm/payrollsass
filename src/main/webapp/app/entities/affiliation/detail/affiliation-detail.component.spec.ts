import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AffiliationDetailComponent } from './affiliation-detail.component';

describe('Affiliation Management Detail Component', () => {
  let comp: AffiliationDetailComponent;
  let fixture: ComponentFixture<AffiliationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AffiliationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ affiliation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AffiliationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AffiliationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load affiliation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.affiliation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
