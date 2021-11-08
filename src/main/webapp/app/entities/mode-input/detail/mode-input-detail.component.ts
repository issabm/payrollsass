import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModeInput } from '../mode-input.model';

@Component({
  selector: 'jhi-mode-input-detail',
  templateUrl: './mode-input-detail.component.html',
})
export class ModeInputDetailComponent implements OnInit {
  modeInput: IModeInput | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeInput }) => {
      this.modeInput = modeInput;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
