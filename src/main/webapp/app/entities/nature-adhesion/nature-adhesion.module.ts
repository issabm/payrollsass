import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureAdhesionComponent } from './list/nature-adhesion.component';
import { NatureAdhesionDetailComponent } from './detail/nature-adhesion-detail.component';
import { NatureAdhesionUpdateComponent } from './update/nature-adhesion-update.component';
import { NatureAdhesionDeleteDialogComponent } from './delete/nature-adhesion-delete-dialog.component';
import { NatureAdhesionRoutingModule } from './route/nature-adhesion-routing.module';

@NgModule({
  imports: [SharedModule, NatureAdhesionRoutingModule],
  declarations: [
    NatureAdhesionComponent,
    NatureAdhesionDetailComponent,
    NatureAdhesionUpdateComponent,
    NatureAdhesionDeleteDialogComponent,
  ],
  entryComponents: [NatureAdhesionDeleteDialogComponent],
})
export class NatureAdhesionModule {}
