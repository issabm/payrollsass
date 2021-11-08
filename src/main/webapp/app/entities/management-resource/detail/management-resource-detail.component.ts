import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManagementResource } from '../management-resource.model';

@Component({
  selector: 'jhi-management-resource-detail',
  templateUrl: './management-resource-detail.component.html',
})
export class ManagementResourceDetailComponent implements OnInit {
  managementResource: IManagementResource | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managementResource }) => {
      this.managementResource = managementResource;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
