import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConjointComponent } from './list/conjoint.component';
import { ConjointDetailComponent } from './detail/conjoint-detail.component';
import { ConjointUpdateComponent } from './update/conjoint-update.component';
import { ConjointDeleteDialogComponent } from './delete/conjoint-delete-dialog.component';
import { ConjointRoutingModule } from './route/conjoint-routing.module';

@NgModule({
  imports: [SharedModule, ConjointRoutingModule],
  declarations: [ConjointComponent, ConjointDetailComponent, ConjointUpdateComponent, ConjointDeleteDialogComponent],
  entryComponents: [ConjointDeleteDialogComponent],
})
export class ConjointModule {}
