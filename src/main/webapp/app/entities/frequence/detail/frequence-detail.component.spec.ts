import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrequenceDetailComponent } from './frequence-detail.component';

describe('Frequence Management Detail Component', () => {
  let comp: FrequenceDetailComponent;
  let fixture: ComponentFixture<FrequenceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FrequenceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ frequence: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FrequenceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FrequenceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load frequence on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.frequence).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
