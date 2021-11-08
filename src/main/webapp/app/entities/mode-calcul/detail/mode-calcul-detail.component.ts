import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModeCalcul } from '../mode-calcul.model';

@Component({
  selector: 'jhi-mode-calcul-detail',
  templateUrl: './mode-calcul-detail.component.html',
})
export class ModeCalculDetailComponent implements OnInit {
  modeCalcul: IModeCalcul | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeCalcul }) => {
      this.modeCalcul = modeCalcul;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
