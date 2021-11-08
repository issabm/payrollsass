import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureMvtPaie } from '../nature-mvt-paie.model';

@Component({
  selector: 'jhi-nature-mvt-paie-detail',
  templateUrl: './nature-mvt-paie-detail.component.html',
})
export class NatureMvtPaieDetailComponent implements OnInit {
  natureMvtPaie: INatureMvtPaie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureMvtPaie }) => {
      this.natureMvtPaie = natureMvtPaie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
