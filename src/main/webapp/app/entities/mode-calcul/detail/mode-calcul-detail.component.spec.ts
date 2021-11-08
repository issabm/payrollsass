import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModeCalculDetailComponent } from './mode-calcul-detail.component';

describe('ModeCalcul Management Detail Component', () => {
  let comp: ModeCalculDetailComponent;
  let fixture: ComponentFixture<ModeCalculDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeCalculDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ modeCalcul: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ModeCalculDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ModeCalculDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load modeCalcul on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.modeCalcul).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
