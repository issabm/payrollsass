import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SexeDetailComponent } from './sexe-detail.component';

describe('Sexe Management Detail Component', () => {
  let comp: SexeDetailComponent;
  let fixture: ComponentFixture<SexeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SexeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sexe: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SexeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SexeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sexe on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sexe).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
