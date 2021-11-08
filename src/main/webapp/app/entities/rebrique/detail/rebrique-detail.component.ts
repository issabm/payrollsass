import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRebrique } from '../rebrique.model';

@Component({
  selector: 'jhi-rebrique-detail',
  templateUrl: './rebrique-detail.component.html',
})
export class RebriqueDetailComponent implements OnInit {
  rebrique: IRebrique | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rebrique }) => {
      this.rebrique = rebrique;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
