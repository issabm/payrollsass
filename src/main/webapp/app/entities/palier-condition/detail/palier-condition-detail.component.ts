import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPalierCondition } from '../palier-condition.model';

@Component({
  selector: 'jhi-palier-condition-detail',
  templateUrl: './palier-condition-detail.component.html',
})
export class PalierConditionDetailComponent implements OnInit {
  palierCondition: IPalierCondition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ palierCondition }) => {
      this.palierCondition = palierCondition;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
