import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureAdhesion } from '../nature-adhesion.model';

@Component({
  selector: 'jhi-nature-adhesion-detail',
  templateUrl: './nature-adhesion-detail.component.html',
})
export class NatureAdhesionDetailComponent implements OnInit {
  natureAdhesion: INatureAdhesion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAdhesion }) => {
      this.natureAdhesion = natureAdhesion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
