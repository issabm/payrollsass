import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureConfigDetailComponent } from './nature-config-detail.component';

describe('NatureConfig Management Detail Component', () => {
  let comp: NatureConfigDetailComponent;
  let fixture: ComponentFixture<NatureConfigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureConfigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureConfig: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureConfigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureConfigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureConfig on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureConfig).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
