import { Routes } from '@angular/router';
import {ShellComponent} from './components/shell-component/shell-component';
import {HomeComponent} from './components/home-component/home-component';
import {AnalyticsComponent} from './components/analytics-component/analytics-component';
import {SessionsComponent} from './components/sessions-component/sessions-component';
import {ConnectionsComponent} from './components/connections-component/connections-component';
import {InventoryComponent} from './components/inventory-component/inventory-component';


export const routes: Routes = [


  {path: '', component: ShellComponent ,
      children: [
              {path: '', component: HomeComponent},
              {path: 'analytics', component: AnalyticsComponent},
              {path: 'sessions', component: SessionsComponent},
              {path: 'connections', component: ConnectionsComponent},
              {path: 'inventory', component: InventoryComponent}
      ]
  },
{path: '**', redirectTo: ' '}

];
