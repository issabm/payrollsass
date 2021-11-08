import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SensDetailComponent } from './sens-detail.component';

describe('Sens Management Detail Component', () => {
  let comp: SensDetailComponent;
  let fixture: ComponentFixture<SensDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SensDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sens: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SensDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SensDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sens on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sens).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
