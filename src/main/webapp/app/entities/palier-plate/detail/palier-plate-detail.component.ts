import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPalierPlate } from '../palier-plate.model';

@Component({
  selector: 'jhi-palier-plate-detail',
  templateUrl: './palier-plate-detail.component.html',
})
export class PalierPlateDetailComponent implements OnInit {
  palierPlate: IPalierPlate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ palierPlate }) => {
      this.palierPlate = palierPlate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
