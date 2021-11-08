import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PalierPlateDetailComponent } from './palier-plate-detail.component';

describe('PalierPlate Management Detail Component', () => {
  let comp: PalierPlateDetailComponent;
  let fixture: ComponentFixture<PalierPlateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PalierPlateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ palierPlate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PalierPlateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PalierPlateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load palierPlate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.palierPlate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
