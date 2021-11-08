import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ModeCalculComponent } from './list/mode-calcul.component';
import { ModeCalculDetailComponent } from './detail/mode-calcul-detail.component';
import { ModeCalculUpdateComponent } from './update/mode-calcul-update.component';
import { ModeCalculDeleteDialogComponent } from './delete/mode-calcul-delete-dialog.component';
import { ModeCalculRoutingModule } from './route/mode-calcul-routing.module';

@NgModule({
  imports: [SharedModule, ModeCalculRoutingModule],
  declarations: [ModeCalculComponent, ModeCalculDetailComponent, ModeCalculUpdateComponent, ModeCalculDeleteDialogComponent],
  entryComponents: [ModeCalculDeleteDialogComponent],
})
export class ModeCalculModule {}
