import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMouvementPaie } from '../mouvement-paie.model';

@Component({
  selector: 'jhi-mouvement-paie-detail',
  templateUrl: './mouvement-paie-detail.component.html',
})
export class MouvementPaieDetailComponent implements OnInit {
  mouvementPaie: IMouvementPaie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementPaie }) => {
      this.mouvementPaie = mouvementPaie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
