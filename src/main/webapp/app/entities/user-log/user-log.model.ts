import * as dayjs from 'dayjs';

export interface IUserLog {
  id?: number;
  op?: string | null;
  util?: string | null;
  dateOp?: dayjs.Dayjs | null;
}

export class UserLog implements IUserLog {
  constructor(public id?: number, public op?: string | null, public util?: string | null, public dateOp?: dayjs.Dayjs | null) {}
}

export function getUserLogIdentifier(userLog: IUserLog): number | undefined {
  return userLog.id;
}
