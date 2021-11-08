import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeCalculPaie } from '../demande-calcul-paie.model';

@Component({
  selector: 'jhi-demande-calcul-paie-detail',
  templateUrl: './demande-calcul-paie-detail.component.html',
})
export class DemandeCalculPaieDetailComponent implements OnInit {
  demandeCalculPaie: IDemandeCalculPaie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeCalculPaie }) => {
      this.demandeCalculPaie = demandeCalculPaie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
