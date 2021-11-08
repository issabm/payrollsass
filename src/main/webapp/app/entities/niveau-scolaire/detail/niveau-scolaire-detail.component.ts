import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INiveauScolaire } from '../niveau-scolaire.model';

@Component({
  selector: 'jhi-niveau-scolaire-detail',
  templateUrl: './niveau-scolaire-detail.component.html',
})
export class NiveauScolaireDetailComponent implements OnInit {
  niveauScolaire: INiveauScolaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ niveauScolaire }) => {
      this.niveauScolaire = niveauScolaire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
