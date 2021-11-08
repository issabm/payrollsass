import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISoldeAbsencePaie } from '../solde-absence-paie.model';

@Component({
  selector: 'jhi-solde-absence-paie-detail',
  templateUrl: './solde-absence-paie-detail.component.html',
})
export class SoldeAbsencePaieDetailComponent implements OnInit {
  soldeAbsencePaie: ISoldeAbsencePaie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeAbsencePaie }) => {
      this.soldeAbsencePaie = soldeAbsencePaie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
