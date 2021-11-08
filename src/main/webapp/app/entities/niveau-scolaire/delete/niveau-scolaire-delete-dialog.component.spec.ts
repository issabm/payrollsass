jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NiveauScolaireService } from '../service/niveau-scolaire.service';

import { NiveauScolaireDeleteDialogComponent } from './niveau-scolaire-delete-dialog.component';

describe('NiveauScolaire Management Delete Component', () => {
  let comp: NiveauScolaireDeleteDialogComponent;
  let fixture: ComponentFixture<NiveauScolaireDeleteDialogComponent>;
  let service: NiveauScolaireService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [NiveauScolaireDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(NiveauScolaireDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NiveauScolaireDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NiveauScolaireService);
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
