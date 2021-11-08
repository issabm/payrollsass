import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IdentiteComponent } from './list/identite.component';
import { IdentiteDetailComponent } from './detail/identite-detail.component';
import { IdentiteUpdateComponent } from './update/identite-update.component';
import { IdentiteDeleteDialogComponent } from './delete/identite-delete-dialog.component';
import { IdentiteRoutingModule } from './route/identite-routing.module';

@NgModule({
  imports: [SharedModule, IdentiteRoutingModule],
  declarations: [IdentiteComponent, IdentiteDetailComponent, IdentiteUpdateComponent, IdentiteDeleteDialogComponent],
  entryComponents: [IdentiteDeleteDialogComponent],
})
export class IdentiteModule {}
