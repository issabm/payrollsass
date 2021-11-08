import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEligibilite } from '../eligibilite.model';

@Component({
  selector: 'jhi-eligibilite-detail',
  templateUrl: './eligibilite-detail.component.html',
})
export class EligibiliteDetailComponent implements OnInit {
  eligibilite: IEligibilite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eligibilite }) => {
      this.eligibilite = eligibilite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
