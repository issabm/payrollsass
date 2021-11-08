import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EntityAdhesionDetailComponent } from './entity-adhesion-detail.component';

describe('EntityAdhesion Management Detail Component', () => {
  let comp: EntityAdhesionDetailComponent;
  let fixture: ComponentFixture<EntityAdhesionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EntityAdhesionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ entityAdhesion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EntityAdhesionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EntityAdhesionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load entityAdhesion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.entityAdhesion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
