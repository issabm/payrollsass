import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SousTypeContratComponent } from './list/sous-type-contrat.component';
import { SousTypeContratDetailComponent } from './detail/sous-type-contrat-detail.component';
import { SousTypeContratUpdateComponent } from './update/sous-type-contrat-update.component';
import { SousTypeContratDeleteDialogComponent } from './delete/sous-type-contrat-delete-dialog.component';
import { SousTypeContratRoutingModule } from './route/sous-type-contrat-routing.module';

@NgModule({
  imports: [SharedModule, SousTypeContratRoutingModule],
  declarations: [
    SousTypeContratComponent,
    SousTypeContratDetailComponent,
    SousTypeContratUpdateComponent,
    SousTypeContratDeleteDialogComponent,
  ],
  entryComponents: [SousTypeContratDeleteDialogComponent],
})
export class SousTypeContratModule {}
