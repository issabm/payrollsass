import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeIdentiteComponent } from './list/type-identite.component';
import { TypeIdentiteDetailComponent } from './detail/type-identite-detail.component';
import { TypeIdentiteUpdateComponent } from './update/type-identite-update.component';
import { TypeIdentiteDeleteDialogComponent } from './delete/type-identite-delete-dialog.component';
import { TypeIdentiteRoutingModule } from './route/type-identite-routing.module';

@NgModule({
  imports: [SharedModule, TypeIdentiteRoutingModule],
  declarations: [TypeIdentiteComponent, TypeIdentiteDetailComponent, TypeIdentiteUpdateComponent, TypeIdentiteDeleteDialogComponent],
  entryComponents: [TypeIdentiteDeleteDialogComponent],
})
export class TypeIdentiteModule {}
