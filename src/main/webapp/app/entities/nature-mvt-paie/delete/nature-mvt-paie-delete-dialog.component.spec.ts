jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NatureMvtPaieService } from '../service/nature-mvt-paie.service';

import { NatureMvtPaieDeleteDialogComponent } from './nature-mvt-paie-delete-dialog.component';

describe('NatureMvtPaie Management Delete Component', () => {
  let comp: NatureMvtPaieDeleteDialogComponent;
  let fixture: ComponentFixture<NatureMvtPaieDeleteDialogComponent>;
  let service: NatureMvtPaieService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NatureMvtPaieDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(NatureMvtPaieDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureMvtPaieDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NatureMvtPaieService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
