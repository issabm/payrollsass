import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcerne } from '../concerne.model';

@Component({
  selector: 'jhi-concerne-detail',
  templateUrl: './concerne-detail.component.html',
})
export class ConcerneDetailComponent implements OnInit {
  concerne: IConcerne | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concerne }) => {
      this.concerne = concerne;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
