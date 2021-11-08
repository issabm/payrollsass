import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EntityAdhesionComponent } from './list/entity-adhesion.component';
import { EntityAdhesionDetailComponent } from './detail/entity-adhesion-detail.component';
import { EntityAdhesionUpdateComponent } from './update/entity-adhesion-update.component';
import { EntityAdhesionDeleteDialogComponent } from './delete/entity-adhesion-delete-dialog.component';
import { EntityAdhesionRoutingModule } from './route/entity-adhesion-routing.module';

@NgModule({
  imports: [SharedModule, EntityAdhesionRoutingModule],
  declarations: [
    EntityAdhesionComponent,
    EntityAdhesionDetailComponent,
    EntityAdhesionUpdateComponent,
    EntityAdhesionDeleteDialogComponent,
  ],
  entryComponents: [EntityAdhesionDeleteDialogComponent],
})
export class EntityAdhesionModule {}
