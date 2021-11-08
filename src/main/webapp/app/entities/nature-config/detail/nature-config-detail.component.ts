import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureConfig } from '../nature-config.model';

@Component({
  selector: 'jhi-nature-config-detail',
  templateUrl: './nature-config-detail.component.html',
})
export class NatureConfigDetailComponent implements OnInit {
  natureConfig: INatureConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureConfig }) => {
      this.natureConfig = natureConfig;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
