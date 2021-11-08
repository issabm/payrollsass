import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConjoint } from '../conjoint.model';

@Component({
  selector: 'jhi-conjoint-detail',
  templateUrl: './conjoint-detail.component.html',
})
export class ConjointDetailComponent implements OnInit {
  conjoint: IConjoint | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conjoint }) => {
      this.conjoint = conjoint;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
