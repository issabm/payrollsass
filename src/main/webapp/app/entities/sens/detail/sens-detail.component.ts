import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISens } from '../sens.model';

@Component({
  selector: 'jhi-sens-detail',
  templateUrl: './sens-detail.component.html',
})
export class SensDetailComponent implements OnInit {
  sens: ISens | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sens }) => {
      this.sens = sens;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
