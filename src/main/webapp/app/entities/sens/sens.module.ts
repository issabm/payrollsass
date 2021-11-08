import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SensComponent } from './list/sens.component';
import { SensDetailComponent } from './detail/sens-detail.component';
import { SensUpdateComponent } from './update/sens-update.component';
import { SensDeleteDialogComponent } from './delete/sens-delete-dialog.component';
import { SensRoutingModule } from './route/sens-routing.module';

@NgModule({
  imports: [SharedModule, SensRoutingModule],
  declarations: [SensComponent, SensDetailComponent, SensUpdateComponent, SensDeleteDialogComponent],
  entryComponents: [SensDeleteDialogComponent],
})
export class SensModule {}
