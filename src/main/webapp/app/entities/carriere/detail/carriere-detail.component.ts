import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarriere } from '../carriere.model';

@Component({
  selector: 'jhi-carriere-detail',
  templateUrl: './carriere-detail.component.html',
})
export class CarriereDetailComponent implements OnInit {
  carriere: ICarriere | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carriere }) => {
      this.carriere = carriere;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
