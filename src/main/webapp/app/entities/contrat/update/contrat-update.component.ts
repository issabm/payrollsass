import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IContrat, Contrat } from '../contrat.model';
import { ContratService } from '../service/contrat.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { ISousTypeContrat } from 'app/entities/sous-type-contrat/sous-type-contrat.model';
import { SousTypeContratService } from 'app/entities/sous-type-contrat/service/sous-type-contrat.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html',
})
export class ContratUpdateComponent implements OnInit {
  isSaving = false;

  employesSharedCollection: IEmploye[] = [];
  sousTypeContratsSharedCollection: ISousTypeContrat[] = [];
  groupesSharedCollection: IGroupe[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  devisesSharedCollection: IDevise[] = [];

  editForm = this.fb.group({
    id: [],
    ref: [],
    matricule: [],
    dateDebut: [],
    dateFin: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    employe: [],
    sousType: [],
    groupe: [],
    entreprise: [],
    affiliation: [],
    devise: [],
  });

  constructor(
    protected contratService: ContratService,
    protected employeService: EmployeService,
    protected sousTypeContratService: SousTypeContratService,
    protected groupeService: GroupeService,
    protected entrepriseService: EntrepriseService,
    protected affiliationService: AffiliationService,
    protected deviseService: DeviseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrat }) => {
      if (contrat.id === undefined) {
        const today = dayjs().startOf('day');
        contrat.dateop = today;
        contrat.createdDate = today;
        contrat.modifiedDate = today;
      }

      this.updateForm(contrat);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrat = this.createFromForm();
    if (contrat.id !== undefined) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackSousTypeContratById(index: number, item: ISousTypeContrat): number {
    return item.id!;
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackAffiliationById(index: number, item: IAffiliation): number {
    return item.id!;
  }

  trackDeviseById(index: number, item: IDevise): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>): void {
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

  protected updateForm(contrat: IContrat): void {
    this.editForm.patchValue({
      id: contrat.id,
      ref: contrat.ref,
      matricule: contrat.matricule,
      dateDebut: contrat.dateDebut,
      dateFin: contrat.dateFin,
      util: contrat.util,
      dateop: contrat.dateop ? contrat.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: contrat.modifiedBy,
      op: contrat.op,
      isDeleted: contrat.isDeleted,
      createdDate: contrat.createdDate ? contrat.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: contrat.modifiedDate ? contrat.modifiedDate.format(DATE_TIME_FORMAT) : null,
      employe: contrat.employe,
      sousType: contrat.sousType,
      groupe: contrat.groupe,
      entreprise: contrat.entreprise,
      affiliation: contrat.affiliation,
      devise: contrat.devise,
    });

    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(this.employesSharedCollection, contrat.employe);
    this.sousTypeContratsSharedCollection = this.sousTypeContratService.addSousTypeContratToCollectionIfMissing(
      this.sousTypeContratsSharedCollection,
      contrat.sousType
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, contrat.groupe);
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      contrat.entreprise
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      contrat.affiliation
    );
    this.devisesSharedCollection = this.deviseService.addDeviseToCollectionIfMissing(this.devisesSharedCollection, contrat.devise);
  }

  protected loadRelationshipsOptions(): void {
    this.employeService
      .query()
      .pipe(map((res: HttpResponse<IEmploye[]>) => res.body ?? []))
      .pipe(
        map((employes: IEmploye[]) => this.employeService.addEmployeToCollectionIfMissing(employes, this.editForm.get('employe')!.value))
      )
      .subscribe((employes: IEmploye[]) => (this.employesSharedCollection = employes));

    this.sousTypeContratService
      .query()
      .pipe(map((res: HttpResponse<ISousTypeContrat[]>) => res.body ?? []))
      .pipe(
        map((sousTypeContrats: ISousTypeContrat[]) =>
          this.sousTypeContratService.addSousTypeContratToCollectionIfMissing(sousTypeContrats, this.editForm.get('sousType')!.value)
        )
      )
      .subscribe((sousTypeContrats: ISousTypeContrat[]) => (this.sousTypeContratsSharedCollection = sousTypeContrats));

    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.affiliationService
      .query()
      .pipe(map((res: HttpResponse<IAffiliation[]>) => res.body ?? []))
      .pipe(
        map((affiliations: IAffiliation[]) =>
          this.affiliationService.addAffiliationToCollectionIfMissing(affiliations, this.editForm.get('affiliation')!.value)
        )
      )
      .subscribe((affiliations: IAffiliation[]) => (this.affiliationsSharedCollection = affiliations));

    this.deviseService
      .query()
      .pipe(map((res: HttpResponse<IDevise[]>) => res.body ?? []))
      .pipe(map((devises: IDevise[]) => this.deviseService.addDeviseToCollectionIfMissing(devises, this.editForm.get('devise')!.value)))
      .subscribe((devises: IDevise[]) => (this.devisesSharedCollection = devises));
  }

  protected createFromForm(): IContrat {
    return {
      ...new Contrat(),
      id: this.editForm.get(['id'])!.value,
      ref: this.editForm.get(['ref'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      employe: this.editForm.get(['employe'])!.value,
      sousType: this.editForm.get(['sousType'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      affiliation: this.editForm.get(['affiliation'])!.value,
      devise: this.editForm.get(['devise'])!.value,
    };
  }
}
