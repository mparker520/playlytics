import { Routes } from '@angular/router';
import {ShellComponent} from './components/shell-component/shell-component';
import {HomeComponent} from './components/home-component/home-component';
import {AnalyticsComponent} from './components/analytics-component/analytics-component';
import {SessionsComponent} from './components/sessions-component/sessions-component';
import {ConnectionsComponent} from './components/connections-component/connections-component';
import {InventoryComponent} from './components/inventory-component/inventory-component';
import {LoginComponent} from './components/login-component/login-component';
import {AccountCreationComponent} from './components/account-creation-component/account-creation-component';
import {AuthGuardService} from './services/auth-guard-service';
import {ProfileComponent} from './components/profile-component/profile-component';

export const routes: Routes = [


  {path: '', component: ShellComponent ,
      children: [
              {path: 'login', component: LoginComponent},
              {path: 'sign-up', component: AccountCreationComponent},
              {path: '', component: HomeComponent, canActivate: [AuthGuardService] },
              {path: 'analytics', component: AnalyticsComponent, canActivate: [AuthGuardService]},
              {path: 'sessions', component: SessionsComponent, canActivate: [AuthGuardService]},
              {path: 'network', component: ConnectionsComponent, canActivate: [AuthGuardService]},
              {path: 'inventory', component: InventoryComponent, canActivate: [AuthGuardService]},
              {path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]},
              {path: 'logout', component: LoginComponent}
      ]
  },
{path: '**', redirectTo: ''}

];
