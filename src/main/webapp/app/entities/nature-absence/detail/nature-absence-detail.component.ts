import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureAbsence } from '../nature-absence.model';

@Component({
  selector: 'jhi-nature-absence-detail',
  templateUrl: './nature-absence-detail.component.html',
})
export class NatureAbsenceDetailComponent implements OnInit {
  natureAbsence: INatureAbsence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAbsence }) => {
      this.natureAbsence = natureAbsence;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
