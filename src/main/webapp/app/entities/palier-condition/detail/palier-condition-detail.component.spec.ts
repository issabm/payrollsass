import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PalierConditionDetailComponent } from './palier-condition-detail.component';

describe('PalierCondition Management Detail Component', () => {
  let comp: PalierConditionDetailComponent;
  let fixture: ComponentFixture<PalierConditionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PalierConditionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ palierCondition: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PalierConditionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PalierConditionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load palierCondition on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.palierCondition).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
