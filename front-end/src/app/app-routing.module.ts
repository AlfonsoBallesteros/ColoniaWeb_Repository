import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//components
import { HomeComponent } from './components/pages/home/home.component';
import { ProfileComponent } from './components/pages/profile/profile.component';
import { SigninComponent } from './components/pages/signin/signin.component';
import { SignupComponent } from './components/pages/signup/signup.component';
import { ConfirmMailComponent } from './components/pages/confirm-mail/confirm-mail.component';
import { ConfirmedEmailComponent } from './components/pages/confirmed-email/confirmed-email.component';
import { ResetPasswordEmailComponent } from './components/pages/reset-password-email/reset-password-email.component';
import { ChangePasswordComponent } from './components/pages/change-password/change-password.component';
import { AuthGuard } from './guards/auth.guard';




const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'profile/:username', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'signin', component: SigninComponent },
  {path: 'signup', component: SignupComponent},
  { path: 'confirm-mail/:username', component: ConfirmMailComponent },
  { path: 'confirmed-mail', component: ConfirmedEmailComponent },
  { path: 'reset-password-email', component: ResetPasswordEmailComponent },
  { path: 'change-password', component: ChangePasswordComponent },

  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
