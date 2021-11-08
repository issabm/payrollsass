import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGroupe, Groupe } from '../groupe.model';
import { GroupeService } from '../service/groupe.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

@Component({
  selector: 'jhi-groupe-update',
  templateUrl: './groupe-update.component.html',
})
export class GroupeUpdateComponent implements OnInit {
  isSaving = false;

  paysSharedCollection: IPays[] = [];
  devisesSharedCollection: IDevise[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libAr: [],
    libEn: [],
    addresseAr: [],
    addresseEn: [],
    tel: [],
    email: [],
    fax: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    pays: [],
    devise: [],
  });

  constructor(
    protected groupeService: GroupeService,
    protected paysService: PaysService,
    protected deviseService: DeviseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupe }) => {
      if (groupe.id === undefined) {
        const today = dayjs().startOf('day');
        groupe.dateop = today;
      }

      this.updateForm(groupe);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupe = this.createFromForm();
    if (groupe.id !== undefined) {
      this.subscribeToSaveResponse(this.groupeService.update(groupe));
    } else {
      this.subscribeToSaveResponse(this.groupeService.create(groupe));
    }
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackDeviseById(index: number, item: IDevise): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupe>>): void {
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

  protected updateForm(groupe: IGroupe): void {
    this.editForm.patchValue({
      id: groupe.id,
      code: groupe.code,
      libAr: groupe.libAr,
      libEn: groupe.libEn,
      addresseAr: groupe.addresseAr,
      addresseEn: groupe.addresseEn,
      tel: groupe.tel,
      email: groupe.email,
      fax: groupe.fax,
      util: groupe.util,
      dateop: groupe.dateop ? groupe.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: groupe.modifiedBy,
      op: groupe.op,
      isDeleted: groupe.isDeleted,
      pays: groupe.pays,
      devise: groupe.devise,
    });

    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, groupe.pays);
    this.devisesSharedCollection = this.deviseService.addDeviseToCollectionIfMissing(this.devisesSharedCollection, groupe.devise);
  }

  protected loadRelationshipsOptions(): void {
    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

    this.deviseService
      .query()
      .pipe(map((res: HttpResponse<IDevise[]>) => res.body ?? []))
      .pipe(map((devises: IDevise[]) => this.deviseService.addDeviseToCollectionIfMissing(devises, this.editForm.get('devise')!.value)))
      .subscribe((devises: IDevise[]) => (this.devisesSharedCollection = devises));
  }

  protected createFromForm(): IGroupe {
    return {
      ...new Groupe(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libAr: this.editForm.get(['libAr'])!.value,
      libEn: this.editForm.get(['libEn'])!.value,
      addresseAr: this.editForm.get(['addresseAr'])!.value,
      addresseEn: this.editForm.get(['addresseEn'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      email: this.editForm.get(['email'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      devise: this.editForm.get(['devise'])!.value,
    };
  }
}
