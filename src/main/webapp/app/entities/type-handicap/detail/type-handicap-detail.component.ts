import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeHandicap } from '../type-handicap.model';

@Component({
  selector: 'jhi-type-handicap-detail',
  templateUrl: './type-handicap-detail.component.html',
})
export class TypeHandicapDetailComponent implements OnInit {
  typeHandicap: ITypeHandicap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeHandicap }) => {
      this.typeHandicap = typeHandicap;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
