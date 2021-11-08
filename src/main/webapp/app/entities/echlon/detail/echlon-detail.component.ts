import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEchlon } from '../echlon.model';

@Component({
  selector: 'jhi-echlon-detail',
  templateUrl: './echlon-detail.component.html',
})
export class EchlonDetailComponent implements OnInit {
  echlon: IEchlon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ echlon }) => {
      this.echlon = echlon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
