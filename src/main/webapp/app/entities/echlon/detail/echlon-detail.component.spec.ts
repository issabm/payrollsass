import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EchlonDetailComponent } from './echlon-detail.component';

describe('Echlon Management Detail Component', () => {
  let comp: EchlonDetailComponent;
  let fixture: ComponentFixture<EchlonDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EchlonDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ echlon: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EchlonDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EchlonDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load echlon on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.echlon).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
