import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConcerneDetailComponent } from './concerne-detail.component';

describe('Concerne Management Detail Component', () => {
  let comp: ConcerneDetailComponent;
  let fixture: ComponentFixture<ConcerneDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConcerneDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ concerne: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConcerneDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConcerneDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load concerne on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.concerne).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
