import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeHandicapComponent } from './list/type-handicap.component';
import { TypeHandicapDetailComponent } from './detail/type-handicap-detail.component';
import { TypeHandicapUpdateComponent } from './update/type-handicap-update.component';
import { TypeHandicapDeleteDialogComponent } from './delete/type-handicap-delete-dialog.component';
import { TypeHandicapRoutingModule } from './route/type-handicap-routing.module';

@NgModule({
  imports: [SharedModule, TypeHandicapRoutingModule],
  declarations: [TypeHandicapComponent, TypeHandicapDetailComponent, TypeHandicapUpdateComponent, TypeHandicapDeleteDialogComponent],
  entryComponents: [TypeHandicapDeleteDialogComponent],
})
export class TypeHandicapModule {}
