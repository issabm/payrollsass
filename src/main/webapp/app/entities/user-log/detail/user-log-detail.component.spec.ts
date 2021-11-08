import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserLogDetailComponent } from './user-log-detail.component';

describe('UserLog Management Detail Component', () => {
  let comp: UserLogDetailComponent;
  let fixture: ComponentFixture<UserLogDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserLogDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userLog: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserLogDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserLogDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userLog on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userLog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
