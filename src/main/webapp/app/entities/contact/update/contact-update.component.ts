import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContact, Contact } from '../contact.model';
import { ContactService } from '../service/contact.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';

@Component({
  selector: 'jhi-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;

  employesSharedCollection: IEmploye[] = [];

  editForm = this.fb.group({
    id: [],
    nomAr: [],
    prenomAr: [],
    nomEn: [],
    prenomEn: [],
    email: [],
    tel: [],
    phone: [],
    adresse: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    employe: [],
  });

  constructor(
    protected contactService: ContactService,
    protected employeService: EmployeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      if (contact.id === undefined) {
        const today = dayjs().startOf('day');
        contact.dateop = today;
      }

      this.updateForm(contact);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      nomAr: contact.nomAr,
      prenomAr: contact.prenomAr,
      nomEn: contact.nomEn,
      prenomEn: contact.prenomEn,
      email: contact.email,
      tel: contact.tel,
      phone: contact.phone,
      adresse: contact.adresse,
      util: contact.util,
      dateop: contact.dateop ? contact.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: contact.modifiedBy,
      op: contact.op,
      isDeleted: contact.isDeleted,
      employe: contact.employe,
    });

    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(this.employesSharedCollection, contact.employe);
  }

  protected loadRelationshipsOptions(): void {
    this.employeService
      .query()
      .pipe(map((res: HttpResponse<IEmploye[]>) => res.body ?? []))
      .pipe(
        map((employes: IEmploye[]) => this.employeService.addEmployeToCollectionIfMissing(employes, this.editForm.get('employe')!.value))
      )
      .subscribe((employes: IEmploye[]) => (this.employesSharedCollection = employes));
  }

  protected createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      nomAr: this.editForm.get(['nomAr'])!.value,
      prenomAr: this.editForm.get(['prenomAr'])!.value,
      nomEn: this.editForm.get(['nomEn'])!.value,
      prenomEn: this.editForm.get(['prenomEn'])!.value,
      email: this.editForm.get(['email'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      employe: this.editForm.get(['employe'])!.value,
    };
  }
}
