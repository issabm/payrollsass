import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntityAdhesion } from '../entity-adhesion.model';

@Component({
  selector: 'jhi-entity-adhesion-detail',
  templateUrl: './entity-adhesion-detail.component.html',
})
export class EntityAdhesionDetailComponent implements OnInit {
  entityAdhesion: IEntityAdhesion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entityAdhesion }) => {
      this.entityAdhesion = entityAdhesion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
