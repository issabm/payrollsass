import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmploiDetailComponent } from './emploi-detail.component';

describe('Emploi Management Detail Component', () => {
  let comp: EmploiDetailComponent;
  let fixture: ComponentFixture<EmploiDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmploiDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ emploi: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmploiDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmploiDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load emploi on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.emploi).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
