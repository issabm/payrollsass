import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ManagementResourceFunDetailComponent } from './management-resource-fun-detail.component';

describe('ManagementResourceFun Management Detail Component', () => {
  let comp: ManagementResourceFunDetailComponent;
  let fixture: ComponentFixture<ManagementResourceFunDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagementResourceFunDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ managementResourceFun: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ManagementResourceFunDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ManagementResourceFunDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load managementResourceFun on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.managementResourceFun).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
