import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IdentiteDetailComponent } from './identite-detail.component';

describe('Identite Management Detail Component', () => {
  let comp: IdentiteDetailComponent;
  let fixture: ComponentFixture<IdentiteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IdentiteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ identite: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IdentiteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IdentiteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load identite on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.identite).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
