import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICarriere, Carriere } from '../carriere.model';
import { CarriereService } from '../service/carriere.service';
import { IEchlon } from 'app/entities/echlon/echlon.model';
import { EchlonService } from 'app/entities/echlon/service/echlon.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IEmploi } from 'app/entities/emploi/emploi.model';
import { EmploiService } from 'app/entities/emploi/service/emploi.service';
import { IAffectation } from 'app/entities/affectation/affectation.model';
import { AffectationService } from 'app/entities/affectation/service/affectation.service';

@Component({
  selector: 'jhi-carriere-update',
  templateUrl: './carriere-update.component.html',
})
export class CarriereUpdateComponent implements OnInit {
  isSaving = false;

  echlonsSharedCollection: IEchlon[] = [];
  categoriesSharedCollection: ICategory[] = [];
  emploisSharedCollection: IEmploi[] = [];
  affectationsSharedCollection: IAffectation[] = [];

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    dateFin: [],
    dateEmploi: [],
    dateEchlon: [],
    dateCategorie: [],
    dateop: [],
    util: [],
    modifiedBy: [],
    op: [],
    isDeleted: [],
    createdDate: [],
    modifiedDate: [],
    echlon: [],
    category: [],
    emploi: [],
    affectation: [],
  });

  constructor(
    protected carriereService: CarriereService,
    protected echlonService: EchlonService,
    protected categoryService: CategoryService,
    protected emploiService: EmploiService,
    protected affectationService: AffectationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carriere }) => {
      if (carriere.id === undefined) {
        const today = dayjs().startOf('day');
        carriere.dateop = today;
        carriere.createdDate = today;
        carriere.modifiedDate = today;
      }

      this.updateForm(carriere);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carriere = this.createFromForm();
    if (carriere.id !== undefined) {
      this.subscribeToSaveResponse(this.carriereService.update(carriere));
    } else {
      this.subscribeToSaveResponse(this.carriereService.create(carriere));
    }
  }

  trackEchlonById(index: number, item: IEchlon): number {
    return item.id!;
  }

  trackCategoryById(index: number, item: ICategory): number {
    return item.id!;
  }

  trackEmploiById(index: number, item: IEmploi): number {
    return item.id!;
  }

  trackAffectationById(index: number, item: IAffectation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarriere>>): void {
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

  protected updateForm(carriere: ICarriere): void {
    this.editForm.patchValue({
      id: carriere.id,
      dateDebut: carriere.dateDebut,
      dateFin: carriere.dateFin,
      dateEmploi: carriere.dateEmploi,
      dateEchlon: carriere.dateEchlon,
      dateCategorie: carriere.dateCategorie,
      dateop: carriere.dateop ? carriere.dateop.format(DATE_TIME_FORMAT) : null,
      util: carriere.util,
      modifiedBy: carriere.modifiedBy,
      op: carriere.op,
      isDeleted: carriere.isDeleted,
      createdDate: carriere.createdDate ? carriere.createdDate.format(DATE_TIME_FORMAT) : null,
      modifiedDate: carriere.modifiedDate ? carriere.modifiedDate.format(DATE_TIME_FORMAT) : null,
      echlon: carriere.echlon,
      category: carriere.category,
      emploi: carriere.emploi,
      affectation: carriere.affectation,
    });

    this.echlonsSharedCollection = this.echlonService.addEchlonToCollectionIfMissing(this.echlonsSharedCollection, carriere.echlon);
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing(
      this.categoriesSharedCollection,
      carriere.category
    );
    this.emploisSharedCollection = this.emploiService.addEmploiToCollectionIfMissing(this.emploisSharedCollection, carriere.emploi);
    this.affectationsSharedCollection = this.affectationService.addAffectationToCollectionIfMissing(
      this.affectationsSharedCollection,
      carriere.affectation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.echlonService
      .query()
      .pipe(map((res: HttpResponse<IEchlon[]>) => res.body ?? []))
      .pipe(map((echlons: IEchlon[]) => this.echlonService.addEchlonToCollectionIfMissing(echlons, this.editForm.get('echlon')!.value)))
      .subscribe((echlons: IEchlon[]) => (this.echlonsSharedCollection = echlons));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing(categories, this.editForm.get('category')!.value)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.emploiService
      .query()
      .pipe(map((res: HttpResponse<IEmploi[]>) => res.body ?? []))
      .pipe(map((emplois: IEmploi[]) => this.emploiService.addEmploiToCollectionIfMissing(emplois, this.editForm.get('emploi')!.value)))
      .subscribe((emplois: IEmploi[]) => (this.emploisSharedCollection = emplois));

    this.affectationService
      .query()
      .pipe(map((res: HttpResponse<IAffectation[]>) => res.body ?? []))
      .pipe(
        map((affectations: IAffectation[]) =>
          this.affectationService.addAffectationToCollectionIfMissing(affectations, this.editForm.get('affectation')!.value)
        )
      )
      .subscribe((affectations: IAffectation[]) => (this.affectationsSharedCollection = affectations));
  }

  protected createFromForm(): ICarriere {
    return {
      ...new Carriere(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      dateEmploi: this.editForm.get(['dateEmploi'])!.value,
      dateEchlon: this.editForm.get(['dateEchlon'])!.value,
      dateCategorie: this.editForm.get(['dateCategorie'])!.value,
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
      echlon: this.editForm.get(['echlon'])!.value,
      category: this.editForm.get(['category'])!.value,
      emploi: this.editForm.get(['emploi'])!.value,
      affectation: this.editForm.get(['affectation'])!.value,
    };
  }
}
