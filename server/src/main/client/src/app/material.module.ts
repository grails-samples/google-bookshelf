import { NgModule } from '@angular/core';

import { MdToolbarModule } from '@angular/material';
import { MdButtonModule } from '@angular/material';
import { MdMenuModule } from '@angular/material';

@NgModule({
  imports: [
    MdToolbarModule,
    MdButtonModule,
    MdMenuModule
  ],
  exports: [
    MdToolbarModule,
    MdButtonModule,
    MdMenuModule
  ]
})
export class AppMaterialModule { }