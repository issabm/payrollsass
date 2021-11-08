import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFrequence } from '../frequence.model';

@Component({
  selector: 'jhi-frequence-detail',
  templateUrl: './frequence-detail.component.html',
})
export class FrequenceDetailComponent implements OnInit {
  frequence: IFrequence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ frequence }) => {
      this.frequence = frequence;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
