import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ModeInputComponent } from './list/mode-input.component';
import { ModeInputDetailComponent } from './detail/mode-input-detail.component';
import { ModeInputUpdateComponent } from './update/mode-input-update.component';
import { ModeInputDeleteDialogComponent } from './delete/mode-input-delete-dialog.component';
import { ModeInputRoutingModule } from './route/mode-input-routing.module';

@NgModule({
  imports: [SharedModule, ModeInputRoutingModule],
  declarations: [ModeInputComponent, ModeInputDetailComponent, ModeInputUpdateComponent, ModeInputDeleteDialogComponent],
  entryComponents: [ModeInputDeleteDialogComponent],
})
export class ModeInputModule {}
