import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISousTypeContrat } from '../sous-type-contrat.model';

@Component({
  selector: 'jhi-sous-type-contrat-detail',
  templateUrl: './sous-type-contrat-detail.component.html',
})
export class SousTypeContratDetailComponent implements OnInit {
  sousTypeContrat: ISousTypeContrat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sousTypeContrat }) => {
      this.sousTypeContrat = sousTypeContrat;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
