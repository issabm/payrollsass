import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ManagementResourceDetailComponent } from './management-resource-detail.component';

describe('ManagementResource Management Detail Component', () => {
  let comp: ManagementResourceDetailComponent;
  let fixture: ComponentFixture<ManagementResourceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementResourceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ managementResource: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ManagementResourceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ManagementResourceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load managementResource on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.managementResource).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
