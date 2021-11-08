import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RebriqueComponent } from './list/rebrique.component';
import { RebriqueDetailComponent } from './detail/rebrique-detail.component';
import { RebriqueUpdateComponent } from './update/rebrique-update.component';
import { RebriqueDeleteDialogComponent } from './delete/rebrique-delete-dialog.component';
import { RebriqueRoutingModule } from './route/rebrique-routing.module';

@NgModule({
  imports: [SharedModule, RebriqueRoutingModule],
  declarations: [RebriqueComponent, RebriqueDetailComponent, RebriqueUpdateComponent, RebriqueDeleteDialogComponent],
  entryComponents: [RebriqueDeleteDialogComponent],
})
export class RebriqueModule {}
