import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAffiliation } from '../affiliation.model';

@Component({
  selector: 'jhi-affiliation-detail',
  templateUrl: './affiliation-detail.component.html',
})
export class AffiliationDetailComponent implements OnInit {
  affiliation: IAffiliation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ affiliation }) => {
      this.affiliation = affiliation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
