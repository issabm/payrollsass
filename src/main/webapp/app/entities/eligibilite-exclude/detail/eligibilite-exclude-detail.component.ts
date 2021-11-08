import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEligibiliteExclude } from '../eligibilite-exclude.model';

@Component({
  selector: 'jhi-eligibilite-exclude-detail',
  templateUrl: './eligibilite-exclude-detail.component.html',
})
export class EligibiliteExcludeDetailComponent implements OnInit {
  eligibiliteExclude: IEligibiliteExclude | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eligibiliteExclude }) => {
      this.eligibiliteExclude = eligibiliteExclude;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
