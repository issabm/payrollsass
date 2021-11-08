import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConcerneComponent } from './list/concerne.component';
import { ConcerneDetailComponent } from './detail/concerne-detail.component';
import { ConcerneUpdateComponent } from './update/concerne-update.component';
import { ConcerneDeleteDialogComponent } from './delete/concerne-delete-dialog.component';
import { ConcerneRoutingModule } from './route/concerne-routing.module';

@NgModule({
  imports: [SharedModule, ConcerneRoutingModule],
  declarations: [ConcerneComponent, ConcerneDetailComponent, ConcerneUpdateComponent, ConcerneDeleteDialogComponent],
  entryComponents: [ConcerneDeleteDialogComponent],
})
export class ConcerneModule {}
