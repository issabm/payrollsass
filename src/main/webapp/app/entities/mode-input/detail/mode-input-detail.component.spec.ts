import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModeInputDetailComponent } from './mode-input-detail.component';

describe('ModeInput Management Detail Component', () => {
  let comp: ModeInputDetailComponent;
  let fixture: ComponentFixture<ModeInputDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModeInputDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ modeInput: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ModeInputDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ModeInputDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load modeInput on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.modeInput).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
