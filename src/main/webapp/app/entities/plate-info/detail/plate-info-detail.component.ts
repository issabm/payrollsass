import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlateInfo } from '../plate-info.model';

@Component({
  selector: 'jhi-plate-info-detail',
  templateUrl: './plate-info-detail.component.html',
})
export class PlateInfoDetailComponent implements OnInit {
  plateInfo: IPlateInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plateInfo }) => {
      this.plateInfo = plateInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
