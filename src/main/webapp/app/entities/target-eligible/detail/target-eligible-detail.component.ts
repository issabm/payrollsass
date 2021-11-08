import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITargetEligible } from '../target-eligible.model';

@Component({
  selector: 'jhi-target-eligible-detail',
  templateUrl: './target-eligible-detail.component.html',
})
export class TargetEligibleDetailComponent implements OnInit {
  targetEligible: ITargetEligible | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ targetEligible }) => {
      this.targetEligible = targetEligible;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
