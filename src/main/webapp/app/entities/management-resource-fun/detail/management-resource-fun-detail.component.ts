import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManagementResourceFun } from '../management-resource-fun.model';

@Component({
  selector: 'jhi-management-resource-fun-detail',
  templateUrl: './management-resource-fun-detail.component.html',
})
export class ManagementResourceFunDetailComponent implements OnInit {
  managementResourceFun: IManagementResourceFun | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ managementResourceFun }) => {
      this.managementResourceFun = managementResourceFun;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
