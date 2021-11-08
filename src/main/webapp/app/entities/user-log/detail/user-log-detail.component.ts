import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserLog } from '../user-log.model';

@Component({
  selector: 'jhi-user-log-detail',
  templateUrl: './user-log-detail.component.html',
})
export class UserLogDetailComponent implements OnInit {
  userLog: IUserLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userLog }) => {
      this.userLog = userLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
