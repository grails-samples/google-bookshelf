import { NgModule } from '@angular/core';

import { 
  MdToolbarModule, 
  MdButtonModule, 
  MdMenuModule,
  MdCardModule
} from '@angular/material';

@NgModule({
  imports: [
    MdToolbarModule,
    MdButtonModule,
    MdMenuModule,
    MdCardModule
  ],
  exports: [
    MdToolbarModule,
    MdButtonModule,
    MdMenuModule,
    MdCardModule
  ]
})
export class AppMaterialModule { }