import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlateInfoDetailComponent } from './plate-info-detail.component';

describe('PlateInfo Management Detail Component', () => {
  let comp: PlateInfoDetailComponent;
  let fixture: ComponentFixture<PlateInfoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlateInfoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ plateInfo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlateInfoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlateInfoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load plateInfo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.plateInfo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
