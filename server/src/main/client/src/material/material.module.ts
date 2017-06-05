import { NgModule } from '@angular/core';

import { 
  MdToolbarModule, 
  MdButtonModule, 
  MdMenuModule,
  MdCardModule,
  MdSnackBarModule
} from '@angular/material';

@NgModule({
  imports: [
    MdToolbarModule,
    MdButtonModule,
    MdMenuModule,
    MdCardModule,
    MdSnackBarModule
  ],
  exports: [
    MdToolbarModule,
    MdButtonModule,
    MdMenuModule,
    MdCardModule,
    MdSnackBarModule
  ]
})
export class AppMaterialModule { }