import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SexeComponent } from './list/sexe.component';
import { SexeDetailComponent } from './detail/sexe-detail.component';
import { SexeUpdateComponent } from './update/sexe-update.component';
import { SexeDeleteDialogComponent } from './delete/sexe-delete-dialog.component';
import { SexeRoutingModule } from './route/sexe-routing.module';

@NgModule({
  imports: [SharedModule, SexeRoutingModule],
  declarations: [SexeComponent, SexeDetailComponent, SexeUpdateComponent, SexeDeleteDialogComponent],
  entryComponents: [SexeDeleteDialogComponent],
})
export class SexeModule {}
