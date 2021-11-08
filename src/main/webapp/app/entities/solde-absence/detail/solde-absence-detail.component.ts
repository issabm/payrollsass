import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISoldeAbsence } from '../solde-absence.model';

@Component({
  selector: 'jhi-solde-absence-detail',
  templateUrl: './solde-absence-detail.component.html',
})
export class SoldeAbsenceDetailComponent implements OnInit {
  soldeAbsence: ISoldeAbsence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ soldeAbsence }) => {
      this.soldeAbsence = soldeAbsence;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
