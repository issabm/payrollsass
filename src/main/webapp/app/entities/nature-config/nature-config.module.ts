import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureConfigComponent } from './list/nature-config.component';
import { NatureConfigDetailComponent } from './detail/nature-config-detail.component';
import { NatureConfigUpdateComponent } from './update/nature-config-update.component';
import { NatureConfigDeleteDialogComponent } from './delete/nature-config-delete-dialog.component';
import { NatureConfigRoutingModule } from './route/nature-config-routing.module';

@NgModule({
  imports: [SharedModule, NatureConfigRoutingModule],
  declarations: [NatureConfigComponent, NatureConfigDetailComponent, NatureConfigUpdateComponent, NatureConfigDeleteDialogComponent],
  entryComponents: [NatureConfigDeleteDialogComponent],
})
export class NatureConfigModule {}
