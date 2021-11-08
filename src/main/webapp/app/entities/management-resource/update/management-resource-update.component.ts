import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IManagementResource, ManagementResource } from '../management-resource.model';
import { ManagementResourceService } from '../service/management-resource.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';
import { IEmploye } from 'app/entities/employe/employe.model';
import { EmployeService } from 'app/entities/employe/service/employe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';

@Component({
  selector: 'jhi-management-resource-update',
  templateUrl: './management-resource-update.component.html',
})
export class ManagementResourceUpdateComponent implements OnInit {
  isSaving = false;

  situationsSharedCollection: ISituation[] = [];
  employesSharedCollection: IEmploye[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  groupesSharedCollection: IGroupe[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];

  editForm = this.fb.group({
    id: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    createdBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    situation: [],
    employe: [],
    entreprise: [],
    groupe: [],
    affiliation: [],
  });

  constructor(
    protected managementResourceService: ManagementResourceService,
    protected situationService: SituationService,
    protected employeService: EmployeService,
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected affiliationService: AffiliationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managementResource }) => {
      if (managementResource.id === undefined) {
        const today = dayjs().startOf('day');
        managementResource.dateop = today;
        managementResource.createdDate = today;
        managementResource.modifiedDate = today;
      }

      this.updateForm(managementResource);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const managementResource = this.createFromForm();
    if (managementResource.id !== undefined) {
      this.subscribeToSaveResponse(this.managementResourceService.update(managementResource));
    } else {
      this.subscribeToSaveResponse(this.managementResourceService.create(managementResource));
    }
  }

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  trackEmployeById(index: number, item: IEmploye): number {
    return item.id!;
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  trackAffiliationById(index: number, item: IAffiliation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IManagementResource>>): void {
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

  protected updateForm(managementResource: IManagementResource): void {
    this.editForm.patchValue({
      id: managementResource.id,
      util: managementResource.util,
      dateop: managementResource.dateop ? managementResource.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: managementResource.modifiedBy,
      createdBy: managementResource.createdBy,
      op: managementResource.op,
      isDeleted: managementResource.isDeleted,
      createdDate: managementResource.createdDate ? managementResource.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: managementResource.modifiedDate ? managementResource.modifiedDate.format(DATE_TIME_FORMAT) : null,
      situation: managementResource.situation,
      employe: managementResource.employe,
      entreprise: managementResource.entreprise,
      groupe: managementResource.groupe,
      affiliation: managementResource.affiliation,
    });

    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      managementResource.situation
    );
    this.employesSharedCollection = this.employeService.addEmployeToCollectionIfMissing(
      this.employesSharedCollection,
      managementResource.employe
    );
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      managementResource.entreprise
    );
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(
      this.groupesSharedCollection,
      managementResource.groupe
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      managementResource.affiliation
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

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('entreprise')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));

    this.affiliationService
      .query()
      .pipe(map((res: HttpResponse<IAffiliation[]>) => res.body ?? []))
      .pipe(
        map((affiliations: IAffiliation[]) =>
          this.affiliationService.addAffiliationToCollectionIfMissing(affiliations, this.editForm.get('affiliation')!.value)
        )
      )
      .subscribe((affiliations: IAffiliation[]) => (this.affiliationsSharedCollection = affiliations));
  }

  protected createFromForm(): IManagementResource {
    return {
      ...new ManagementResource(),
      id: this.editForm.get(['id'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      situation: this.editForm.get(['situation'])!.value,
      employe: this.editForm.get(['employe'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      affiliation: this.editForm.get(['affiliation'])!.value,
    };
  }
}
