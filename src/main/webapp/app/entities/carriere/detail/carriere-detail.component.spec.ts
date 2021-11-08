import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CarriereDetailComponent } from './carriere-detail.component';

describe('Carriere Management Detail Component', () => {
  let comp: CarriereDetailComponent;
  let fixture: ComponentFixture<CarriereDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CarriereDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ carriere: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CarriereDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CarriereDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load carriere on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.carriere).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
