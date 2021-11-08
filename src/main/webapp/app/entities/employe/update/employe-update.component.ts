import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEmploye, Employe } from '../employe.model';
import { EmployeService } from '../service/employe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { ITypeHandicap } from 'app/entities/type-handicap/type-handicap.model';
import { TypeHandicapService } from 'app/entities/type-handicap/service/type-handicap.service';

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
})
export class EmployeUpdateComponent implements OnInit {
  isSaving = false;

  situationsSharedCollection: ISituation[] = [];
  paysSharedCollection: IPays[] = [];
  typeHandicapsSharedCollection: ITypeHandicap[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [],
    nomAr: [],
    nomJfAr: [],
    prenomAr: [],
    nomEn: [],
    nomJfEn: [],
    prenomEn: [],
    nomPereAr: [],
    nomPereEn: [],
    nomMereAr: [],
    nomMereEn: [],
    nomGpAr: [],
    nomGpEn: [],
    dateNaiss: [],
    rib: [],
    banque: [],
    dateRib: [],
    dateBanque: [],
    img: [],
    imgContentType: [],
    util: [],
    dateop: [],
    dateModif: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    situation: [],
    nationalite: [],
    typeHandicap: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected employeService: EmployeService,
    protected situationService: SituationService,
    protected paysService: PaysService,
    protected typeHandicapService: TypeHandicapService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      if (employe.id === undefined) {
        const today = dayjs().startOf('day');
        employe.dateop = today;
        employe.dateModif = today;
      }

      this.updateForm(employe);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('payrollApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employe = this.createFromForm();
    if (employe.id !== undefined) {
      this.subscribeToSaveResponse(this.employeService.update(employe));
    } else {
      this.subscribeToSaveResponse(this.employeService.create(employe));
    }
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackTypeHandicapById(index: number, item: ITypeHandicap): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>): void {
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

  protected updateForm(employe: IEmploye): void {
    this.editForm.patchValue({
      id: employe.id,
      matricule: employe.matricule,
      nomAr: employe.nomAr,
      nomJfAr: employe.nomJfAr,
      prenomAr: employe.prenomAr,
      nomEn: employe.nomEn,
      nomJfEn: employe.nomJfEn,
      prenomEn: employe.prenomEn,
      nomPereAr: employe.nomPereAr,
      nomPereEn: employe.nomPereEn,
      nomMereAr: employe.nomMereAr,
      nomMereEn: employe.nomMereEn,
      nomGpAr: employe.nomGpAr,
      nomGpEn: employe.nomGpEn,
      dateNaiss: employe.dateNaiss,
      rib: employe.rib,
      banque: employe.banque,
      dateRib: employe.dateRib,
      dateBanque: employe.dateBanque,
      img: employe.img,
      imgContentType: employe.imgContentType,
      util: employe.util,
      dateop: employe.dateop ? employe.dateop.format(DATE_TIME_FORMAT) : null,
      dateModif: employe.dateModif ? employe.dateModif.format(DATE_TIME_FORMAT) : null,
      modifiedBy: employe.modifiedBy,
      op: employe.op,
      isDeleted: employe.isDeleted,
      situation: employe.situation,
      nationalite: employe.nationalite,
      typeHandicap: employe.typeHandicap,
    });

    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      employe.situation
    );
    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, employe.nationalite);
    this.typeHandicapsSharedCollection = this.typeHandicapService.addTypeHandicapToCollectionIfMissing(
      this.typeHandicapsSharedCollection,
      employe.typeHandicap
    );
  }

  protected loadRelationshipsOptions(): void {
    this.situationService
      .query()
      .pipe(map((res: HttpResponse<ISituation[]>) => res.body ?? []))
      .pipe(
        map((situations: ISituation[]) =>
          this.situationService.addSituationToCollectionIfMissing(situations, this.editForm.get('situation')!.value)
        )
      )
      .subscribe((situations: ISituation[]) => (this.situationsSharedCollection = situations));

    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('nationalite')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

    this.typeHandicapService
      .query()
      .pipe(map((res: HttpResponse<ITypeHandicap[]>) => res.body ?? []))
      .pipe(
        map((typeHandicaps: ITypeHandicap[]) =>
          this.typeHandicapService.addTypeHandicapToCollectionIfMissing(typeHandicaps, this.editForm.get('typeHandicap')!.value)
        )
      )
      .subscribe((typeHandicaps: ITypeHandicap[]) => (this.typeHandicapsSharedCollection = typeHandicaps));
  }

  protected createFromForm(): IEmploye {
    return {
      ...new Employe(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      nomAr: this.editForm.get(['nomAr'])!.value,
      nomJfAr: this.editForm.get(['nomJfAr'])!.value,
      prenomAr: this.editForm.get(['prenomAr'])!.value,
      nomEn: this.editForm.get(['nomEn'])!.value,
      nomJfEn: this.editForm.get(['nomJfEn'])!.value,
      prenomEn: this.editForm.get(['prenomEn'])!.value,
      nomPereAr: this.editForm.get(['nomPereAr'])!.value,
      nomPereEn: this.editForm.get(['nomPereEn'])!.value,
      nomMereAr: this.editForm.get(['nomMereAr'])!.value,
      nomMereEn: this.editForm.get(['nomMereEn'])!.value,
      nomGpAr: this.editForm.get(['nomGpAr'])!.value,
      nomGpEn: this.editForm.get(['nomGpEn'])!.value,
      dateNaiss: this.editForm.get(['dateNaiss'])!.value,
      rib: this.editForm.get(['rib'])!.value,
      banque: this.editForm.get(['banque'])!.value,
      dateRib: this.editForm.get(['dateRib'])!.value,
      dateBanque: this.editForm.get(['dateBanque'])!.value,
      imgContentType: this.editForm.get(['imgContentType'])!.value,
      img: this.editForm.get(['img'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      dateModif: this.editForm.get(['dateModif'])!.value ? dayjs(this.editForm.get(['dateModif'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      situation: this.editForm.get(['situation'])!.value,
      nationalite: this.editForm.get(['nationalite'])!.value,
      typeHandicap: this.editForm.get(['typeHandicap'])!.value,
    };
  }
}
