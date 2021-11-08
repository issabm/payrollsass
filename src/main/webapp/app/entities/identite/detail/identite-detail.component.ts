import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdentite } from '../identite.model';

@Component({
  selector: 'jhi-identite-detail',
  templateUrl: './identite-detail.component.html',
})
export class IdentiteDetailComponent implements OnInit {
  identite: IIdentite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ identite }) => {
      this.identite = identite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
