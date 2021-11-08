import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAffectation, Affectation } from '../affectation.model';
import { AffectationService } from '../service/affectation.service';
import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IAffiliation } from 'app/entities/affiliation/affiliation.model';
import { AffiliationService } from 'app/entities/affiliation/service/affiliation.service';
import { ISituation } from 'app/entities/situation/situation.model';
import { SituationService } from 'app/entities/situation/service/situation.service';

@Component({
  selector: 'jhi-affectation-update',
  templateUrl: './affectation-update.component.html',
})
export class AffectationUpdateComponent implements OnInit {
  isSaving = false;

  contratsSharedCollection: IContrat[] = [];
  groupesSharedCollection: IGroupe[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  affiliationsSharedCollection: IAffiliation[] = [];
  situationsSharedCollection: ISituation[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [],
    dateDebut: [],
    dateFin: [],
    dateop: [],
    util: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    contrat: [],
    groupe: [],
    entreprise: [],
    affiliation: [],
    situation: [],
  });

  constructor(
    protected affectationService: AffectationService,
    protected contratService: ContratService,
    protected groupeService: GroupeService,
    protected entrepriseService: EntrepriseService,
    protected affiliationService: AffiliationService,
    protected situationService: SituationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affectation }) => {
      if (affectation.id === undefined) {
        const today = dayjs().startOf('day');
        affectation.dateop = today;
        affectation.createdDate = today;
        affectation.modifiedDate = today;
      }

      this.updateForm(affectation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const affectation = this.createFromForm();
    if (affectation.id !== undefined) {
      this.subscribeToSaveResponse(this.affectationService.update(affectation));
    } else {
      this.subscribeToSaveResponse(this.affectationService.create(affectation));
    }
  }

  trackContratById(index: number, item: IContrat): number {
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

  trackSituationById(index: number, item: ISituation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffectation>>): void {
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

  protected updateForm(affectation: IAffectation): void {
    this.editForm.patchValue({
      id: affectation.id,
      matricule: affectation.matricule,
      dateDebut: affectation.dateDebut,
      dateFin: affectation.dateFin,
      dateop: affectation.dateop ? affectation.dateop.format(DATE_TIME_FORMAT) : null,
      util: affectation.util,
      modifiedBy: affectation.modifiedBy,
      op: affectation.op,
      isDeleted: affectation.isDeleted,
      createdDate: affectation.createdDate ? affectation.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: affectation.modifiedDate ? affectation.modifiedDate.format(DATE_TIME_FORMAT) : null,
      contrat: affectation.contrat,
      groupe: affectation.groupe,
      entreprise: affectation.entreprise,
      affiliation: affectation.affiliation,
      situation: affectation.situation,
    });

    this.contratsSharedCollection = this.contratService.addContratToCollectionIfMissing(this.contratsSharedCollection, affectation.contrat);
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, affectation.groupe);
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      affectation.entreprise
    );
    this.affiliationsSharedCollection = this.affiliationService.addAffiliationToCollectionIfMissing(
      this.affiliationsSharedCollection,
      affectation.affiliation
    );
    this.situationsSharedCollection = this.situationService.addSituationToCollectionIfMissing(
      this.situationsSharedCollection,
      affectation.situation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.contratService
      .query()
      .pipe(map((res: HttpResponse<IContrat[]>) => res.body ?? []))
      .pipe(
        map((contrats: IContrat[]) => this.contratService.addContratToCollectionIfMissing(contrats, this.editForm.get('contrat')!.value))
      )
      .subscribe((contrats: IContrat[]) => (this.contratsSharedCollection = contrats));

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

    this.situationService
      .query()
      .pipe(map((res: HttpResponse<ISituation[]>) => res.body ?? []))
      .pipe(
        map((situations: ISituation[]) =>
          this.situationService.addSituationToCollectionIfMissing(situations, this.editForm.get('situation')!.value)
        )
      )
      .subscribe((situations: ISituation[]) => (this.situationsSharedCollection = situations));
  }

  protected createFromForm(): IAffectation {
    return {
      ...new Affectation(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      util: this.editForm.get(['util'])!.value,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      op: this.editForm.get(['op'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modifiedDate: this.editForm.get(['modifiedDate'])!.value
        ? dayjs(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      contrat: this.editForm.get(['contrat'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      affiliation: this.editForm.get(['affiliation'])!.value,
      situation: this.editForm.get(['situation'])!.value,
    };
  }
}
