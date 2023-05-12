import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewZeroComponent } from './components/view-zero.component';
import { ViewOneComponent } from './components/view-one.component';
import { ViewTwoComponent } from './components/view-two.component';

const routes: Routes = [
  // { path:'', component:ViewZeroComponent},
  { path:'', component:ViewOneComponent},
  { path:'view2/:bundleId', component:ViewTwoComponent},
  { path:'**', redirectTo:'/', pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
