import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IEntreprise, Entreprise } from '../entreprise.model';
import { EntrepriseService } from '../service/entreprise.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { IPays } from 'app/entities/pays/pays.model';
import { PaysService } from 'app/entities/pays/service/pays.service';
import { IDevise } from 'app/entities/devise/devise.model';
import { DeviseService } from 'app/entities/devise/service/devise.service';

@Component({
  selector: 'jhi-entreprise-update',
  templateUrl: './entreprise-update.component.html',
})
export class EntrepriseUpdateComponent implements OnInit {
  isSaving = false;

  groupesSharedCollection: IGroupe[] = [];
  paysSharedCollection: IPays[] = [];
  entreprisesSharedCollection: IEntreprise[] = [];
  devisesSharedCollection: IDevise[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    matFiscal: [],
    registreCommerce: [],
    nomCommerceAr: [],
    nomCommerceEn: [],
    raisonSocialAr: [],
    raisonSocialEn: [],
    addresseAr: [],
    addresseEn: [],
    codePostal: [],
    tel: [],
    email: [],
    fax: [],
    util: [],
    dateop: [],
    modifiedBy: [],
    groupe: [],
    pays: [],
    mere: [],
    devise: [],
  });

  constructor(
    protected entrepriseService: EntrepriseService,
    protected groupeService: GroupeService,
    protected paysService: PaysService,
    protected deviseService: DeviseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entreprise }) => {
      if (entreprise.id === undefined) {
        const today = dayjs().startOf('day');
        entreprise.dateop = today;
      }

      this.updateForm(entreprise);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entreprise = this.createFromForm();
    if (entreprise.id !== undefined) {
      this.subscribeToSaveResponse(this.entrepriseService.update(entreprise));
    } else {
      this.subscribeToSaveResponse(this.entrepriseService.create(entreprise));
    }
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  trackPaysById(index: number, item: IPays): number {
    return item.id!;
  }

  trackEntrepriseById(index: number, item: IEntreprise): number {
    return item.id!;
  }

  trackDeviseById(index: number, item: IDevise): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntreprise>>): void {
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

  protected updateForm(entreprise: IEntreprise): void {
    this.editForm.patchValue({
      id: entreprise.id,
      code: entreprise.code,
      matFiscal: entreprise.matFiscal,
      registreCommerce: entreprise.registreCommerce,
      nomCommerceAr: entreprise.nomCommerceAr,
      nomCommerceEn: entreprise.nomCommerceEn,
      raisonSocialAr: entreprise.raisonSocialAr,
      raisonSocialEn: entreprise.raisonSocialEn,
      addresseAr: entreprise.addresseAr,
      addresseEn: entreprise.addresseEn,
      codePostal: entreprise.codePostal,
      tel: entreprise.tel,
      email: entreprise.email,
      fax: entreprise.fax,
      util: entreprise.util,
      dateop: entreprise.dateop ? entreprise.dateop.format(DATE_TIME_FORMAT) : null,
      modifiedBy: entreprise.modifiedBy,
      groupe: entreprise.groupe,
      pays: entreprise.pays,
      mere: entreprise.mere,
      devise: entreprise.devise,
    });

    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, entreprise.groupe);
    this.paysSharedCollection = this.paysService.addPaysToCollectionIfMissing(this.paysSharedCollection, entreprise.pays);
    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing(
      this.entreprisesSharedCollection,
      entreprise.mere
    );
    this.devisesSharedCollection = this.deviseService.addDeviseToCollectionIfMissing(this.devisesSharedCollection, entreprise.devise);
  }

  protected loadRelationshipsOptions(): void {
    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));

    this.paysService
      .query()
      .pipe(map((res: HttpResponse<IPays[]>) => res.body ?? []))
      .pipe(map((pays: IPays[]) => this.paysService.addPaysToCollectionIfMissing(pays, this.editForm.get('pays')!.value)))
      .subscribe((pays: IPays[]) => (this.paysSharedCollection = pays));

    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing(entreprises, this.editForm.get('mere')!.value)
        )
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.deviseService
      .query()
      .pipe(map((res: HttpResponse<IDevise[]>) => res.body ?? []))
      .pipe(map((devises: IDevise[]) => this.deviseService.addDeviseToCollectionIfMissing(devises, this.editForm.get('devise')!.value)))
      .subscribe((devises: IDevise[]) => (this.devisesSharedCollection = devises));
  }

  protected createFromForm(): IEntreprise {
    return {
      ...new Entreprise(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      matFiscal: this.editForm.get(['matFiscal'])!.value,
      registreCommerce: this.editForm.get(['registreCommerce'])!.value,
      nomCommerceAr: this.editForm.get(['nomCommerceAr'])!.value,
      nomCommerceEn: this.editForm.get(['nomCommerceEn'])!.value,
      raisonSocialAr: this.editForm.get(['raisonSocialAr'])!.value,
      raisonSocialEn: this.editForm.get(['raisonSocialEn'])!.value,
      addresseAr: this.editForm.get(['addresseAr'])!.value,
      addresseEn: this.editForm.get(['addresseEn'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      tel: this.editForm.get(['tel'])!.value,
      email: this.editForm.get(['email'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateop: this.editForm.get(['dateop'])!.value ? dayjs(this.editForm.get(['dateop'])!.value, DATE_TIME_FORMAT) : undefined,
      modifiedBy: this.editForm.get(['modifiedBy'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
      pays: this.editForm.get(['pays'])!.value,
      mere: this.editForm.get(['mere'])!.value,
      devise: this.editForm.get(['devise'])!.value,
    };
  }
}
