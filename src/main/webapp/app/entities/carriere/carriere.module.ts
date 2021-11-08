import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CarriereComponent } from './list/carriere.component';
import { CarriereDetailComponent } from './detail/carriere-detail.component';
import { CarriereUpdateComponent } from './update/carriere-update.component';
import { CarriereDeleteDialogComponent } from './delete/carriere-delete-dialog.component';
import { CarriereRoutingModule } from './route/carriere-routing.module';

@NgModule({
  imports: [SharedModule, CarriereRoutingModule],
  declarations: [CarriereComponent, CarriereDetailComponent, CarriereUpdateComponent, CarriereDeleteDialogComponent],
  entryComponents: [CarriereDeleteDialogComponent],
})
export class CarriereModule {}
