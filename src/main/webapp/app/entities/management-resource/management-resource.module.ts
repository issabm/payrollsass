import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ManagementResourceComponent } from './list/management-resource.component';
import { ManagementResourceDetailComponent } from './detail/management-resource-detail.component';
import { ManagementResourceUpdateComponent } from './update/management-resource-update.component';
import { ManagementResourceDeleteDialogComponent } from './delete/management-resource-delete-dialog.component';
import { ManagementResourceRoutingModule } from './route/management-resource-routing.module';

@NgModule({
  imports: [SharedModule, ManagementResourceRoutingModule],
  declarations: [
    ManagementResourceComponent,
    ManagementResourceDetailComponent,
    ManagementResourceUpdateComponent,
    ManagementResourceDeleteDialogComponent,
  ],
  entryComponents: [ManagementResourceDeleteDialogComponent],
})
export class ManagementResourceModule {}
