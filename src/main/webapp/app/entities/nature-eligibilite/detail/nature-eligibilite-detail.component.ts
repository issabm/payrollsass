import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureEligibilite } from '../nature-eligibilite.model';

@Component({
  selector: 'jhi-nature-eligibilite-detail',
  templateUrl: './nature-eligibilite-detail.component.html',
})
export class NatureEligibiliteDetailComponent implements OnInit {
  natureEligibilite: INatureEligibilite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureEligibilite }) => {
      this.natureEligibilite = natureEligibilite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
