import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ManagementResourceFunComponent } from './list/management-resource-fun.component';
import { ManagementResourceFunDetailComponent } from './detail/management-resource-fun-detail.component';
import { ManagementResourceFunUpdateComponent } from './update/management-resource-fun-update.component';
import { ManagementResourceFunDeleteDialogComponent } from './delete/management-resource-fun-delete-dialog.component';
import { ManagementResourceFunRoutingModule } from './route/management-resource-fun-routing.module';

@NgModule({
  imports: [SharedModule, ManagementResourceFunRoutingModule],
  declarations: [
    ManagementResourceFunComponent,
    ManagementResourceFunDetailComponent,
    ManagementResourceFunUpdateComponent,
    ManagementResourceFunDeleteDialogComponent,
  ],
  entryComponents: [ManagementResourceFunDeleteDialogComponent],
})
export class ManagementResourceFunModule {}
