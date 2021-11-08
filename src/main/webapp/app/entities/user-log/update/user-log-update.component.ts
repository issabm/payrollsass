import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IUserLog, UserLog } from '../user-log.model';
import { UserLogService } from '../service/user-log.service';

@Component({
  selector: 'jhi-user-log-update',
  templateUrl: './user-log-update.component.html',
})
export class UserLogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    op: [],
    util: [],
    dateOp: [],
  });

  constructor(protected userLogService: UserLogService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userLog }) => {
      if (userLog.id === undefined) {
        const today = dayjs().startOf('day');
        userLog.dateOp = today;
      }

      this.updateForm(userLog);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userLog = this.createFromForm();
    if (userLog.id !== undefined) {
      this.subscribeToSaveResponse(this.userLogService.update(userLog));
    } else {
      this.subscribeToSaveResponse(this.userLogService.create(userLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserLog>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(userLog: IUserLog): void {
    this.editForm.patchValue({
      id: userLog.id,
      op: userLog.op,
      util: userLog.util,
      dateOp: userLog.dateOp ? userLog.dateOp.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IUserLog {
    return {
      ...new UserLog(),
      id: this.editForm.get(['id'])!.value,
      op: this.editForm.get(['op'])!.value,
      util: this.editForm.get(['util'])!.value,
      dateOp: this.editForm.get(['dateOp'])!.value ? dayjs(this.editForm.get(['dateOp'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
