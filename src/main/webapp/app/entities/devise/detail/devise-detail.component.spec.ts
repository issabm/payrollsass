import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeviseDetailComponent } from './devise-detail.component';

describe('Devise Management Detail Component', () => {
  let comp: DeviseDetailComponent;
  let fixture: ComponentFixture<DeviseDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeviseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ devise: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DeviseDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DeviseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load devise on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.devise).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
