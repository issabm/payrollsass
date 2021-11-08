import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmploi } from '../emploi.model';

@Component({
  selector: 'jhi-emploi-detail',
  templateUrl: './emploi-detail.component.html',
})
export class EmploiDetailComponent implements OnInit {
  emploi: IEmploi | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emploi }) => {
      this.emploi = emploi;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
