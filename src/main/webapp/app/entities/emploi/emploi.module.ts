import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmploiComponent } from './list/emploi.component';
import { EmploiDetailComponent } from './detail/emploi-detail.component';
import { EmploiUpdateComponent } from './update/emploi-update.component';
import { EmploiDeleteDialogComponent } from './delete/emploi-delete-dialog.component';
import { EmploiRoutingModule } from './route/emploi-routing.module';

@NgModule({
  imports: [SharedModule, EmploiRoutingModule],
  declarations: [EmploiComponent, EmploiDetailComponent, EmploiUpdateComponent, EmploiDeleteDialogComponent],
  entryComponents: [EmploiDeleteDialogComponent],
})
export class EmploiModule {}
