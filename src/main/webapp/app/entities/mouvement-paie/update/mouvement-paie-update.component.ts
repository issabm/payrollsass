import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMouvementPaie, MouvementPaie } from '../mouvement-paie.model';
import { MouvementPaieService } from '../service/mouvement-paie.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { INatureMvtPaie } from 'app/entities/nature-mvt-paie/nature-mvt-paie.model';
import { NatureMvtPaieService } from 'app/entities/nature-mvt-paie/service/nature-mvt-paie.service';
import { IDemandeCalculPaie } from 'app/entities/demande-calcul-paie/demande-calcul-paie.model';
import { DemandeCalculPaieService } from 'app/entities/demande-calcul-paie/service/demande-calcul-paie.service';

@Component({
  selector: 'jhi-mouvement-paie-update',
  templateUrl: './mouvement-paie-update.component.html',
})
export class MouvementPaieUpdateComponent implements OnInit {
  isSaving = false;

  situationsSharedCollection: ISituation[] = [];
  employesSharedCollection: IEmploye[] = [];
  natureMvtPaiesSharedCollection: INatureMvtPaie[] = [];
  demandeCalculPaiesSharedCollection: IDemandeCalculPaie[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    lib: [],
    annee: [],
    mois: [],
    dateCalcul: [],
    dateValid: [],
    datePayroll: [],
    totalNet: [],
    totalNetDevise: [],
    tauxChange: [],
    calculBy: [],
    effectBy: [],
    dateSituation: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    util: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    situation: [],
    employe: [],
    natureMvtPaie: [],
    demandeCalculPaie: [],
  });

  constructor(
    protected mouvementPaieService: MouvementPaieService,
    protected situationService: SituationService,
    protected employeService: EmployeService,
    protected natureMvtPaieService: NatureMvtPaieService,
    protected demandeCalculPaieService: DemandeCalculPaieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementPaie }) => {
      if (mouvementPaie.id === undefined) {
        const today = dayjs().startOf('day');
        mouvementPaie.dateop = today;
        mouvementPaie.createdDate = today;
        mouvementPaie.modifiedDate = today;
      }

      this.updateForm(mouvementPaie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvementPaie = this.createFromForm();
    if (mouvementPaie.id !== undefined) {
      this.subscribeToSaveResponse(this.mouvementPaieService.update(mouvementPaie));
    } else {
      this.subscribeToSaveResponse(this.mouvementPaieService.create(mouvementPaie));
    }
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackNatureMvtPaieById(index: number, item: INatureMvtPaie): number {
    return item.id!;
  }

  trackDemandeCalculPaieById(index: number, item: IDemandeCalculPaie): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvementPaie>>): void {
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

  protected updateForm(mouvementPaie: IMouvementPaie): void {
    this.editForm.patchValue({
      id: mouvementPaie.id,
      code: mouvementPaie.code,
      lib: mouvementPaie.lib,
      annee: mouvementPaie.annee,
      mois: mouvementPaie.mois,
      dateCalcul: mouvementPaie.dateCalcul,
      dateValid: mouvementPaie.dateValid,
      datePayroll: mouvementPaie.datePayroll,
      totalNet: mouvementPaie.totalNet,
      totalNetDevise: mouvementPaie.totalNetDevise,
      tauxChange: mouvementPaie.tauxChange,
      calculBy: mouvementPaie.calculBy,
      effectBy: mouvementPaie.effectBy,
      dateSituation: mouvementPaie.dateSituation,
      dateop: mouvementPaie.dateop ? mouvementPaie.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: mouvementPaie.modifiedBy,
      createdBy: mouvementPaie.createdBy,
      op: mouvementPaie.op,
      util: mouvementPaie.util,
      isDeleted: mouvementPaie.isDeleted,
      createdDate: mouvementPaie.createdDate ? mouvementPaie.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: mouvementPaie.modifiedDate ? mouvementPaie.modifiedDate.format(DATE_TIME_FORMAT) : null,
      situation: mouvementPaie.situation,
      employe: mouvementPaie.employe,
      natureMvtPaie: mouvementPaie.natureMvtPaie,
      demandeCalculPaie: mouvementPaie.demandeCalculPaie,
    });

    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      mouvementPaie.situation
    );
    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(
      this.employesSharedCollection,
      mouvementPaie.employe
    );
    this.natureMvtPaiesSharedCollection = this.natureMvtPaieService.addNatureMvtPaieToCollectionIfMissing(
      this.natureMvtPaiesSharedCollection,
      mouvementPaie.natureMvtPaie
    );
    this.demandeCalculPaiesSharedCollection = this.demandeCalculPaieService.addDemandeCalculPaieToCollectionIfMissing(
      this.demandeCalculPaiesSharedCollection,
      mouvementPaie.demandeCalculPaie
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

    this.employeService
      .query()
      .pipe(map((res: HttpResponse<IEmploye[]>) => res.body ?? []))
      .pipe(
        map((employes: IEmploye[]) => this.employeService.addEmployeToCollectionIfMissing(employes, this.editForm.get('employe')!.value))
      )
      .subscribe((employes: IEmploye[]) => (this.employesSharedCollection = employes));

    this.natureMvtPaieService
      .query()
      .pipe(map((res: HttpResponse<INatureMvtPaie[]>) => res.body ?? []))
      .pipe(
        map((natureMvtPaies: INatureMvtPaie[]) =>
          this.natureMvtPaieService.addNatureMvtPaieToCollectionIfMissing(natureMvtPaies, this.editForm.get('natureMvtPaie')!.value)
        )
      )
      .subscribe((natureMvtPaies: INatureMvtPaie[]) => (this.natureMvtPaiesSharedCollection = natureMvtPaies));

    this.demandeCalculPaieService
      .query()
      .pipe(map((res: HttpResponse<IDemandeCalculPaie[]>) => res.body ?? []))
      .pipe(
        map((demandeCalculPaies: IDemandeCalculPaie[]) =>
          this.demandeCalculPaieService.addDemandeCalculPaieToCollectionIfMissing(
            demandeCalculPaies,
            this.editForm.get('demandeCalculPaie')!.value
          )
        )
      )
      .subscribe((demandeCalculPaies: IDemandeCalculPaie[]) => (this.demandeCalculPaiesSharedCollection = demandeCalculPaies));
  }

  protected createFromForm(): IMouvementPaie {
    return {
      ...new MouvementPaie(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      lib: this.editForm.get(['lib'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      dateCalcul: this.editForm.get(['dateCalcul'])!.value,
      dateValid: this.editForm.get(['dateValid'])!.value,
      datePayroll: this.editForm.get(['datePayroll'])!.value,
      totalNet: this.editForm.get(['totalNet'])!.value,
      totalNetDevise: this.editForm.get(['totalNetDevise'])!.value,
      tauxChange: this.editForm.get(['tauxChange'])!.value,
      calculBy: this.editForm.get(['calculBy'])!.value,
      effectBy: this.editForm.get(['effectBy'])!.value,
      dateSituation: this.editForm.get(['dateSituation'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      util: this.editForm.get(['util'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      situation: this.editForm.get(['situation'])!.value,
      employe: this.editForm.get(['employe'])!.value,
      natureMvtPaie: this.editForm.get(['natureMvtPaie'])!.value,
      demandeCalculPaie: this.editForm.get(['demandeCalculPaie'])!.value,
    };
  }
}
