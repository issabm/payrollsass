import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeIdentite } from '../type-identite.model';

@Component({
  selector: 'jhi-type-identite-detail',
  templateUrl: './type-identite-detail.component.html',
})
export class TypeIdentiteDetailComponent implements OnInit {
  typeIdentite: ITypeIdentite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeIdentite }) => {
      this.typeIdentite = typeIdentite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
