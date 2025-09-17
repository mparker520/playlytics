import { Routes } from '@angular/router';
import {HomePage} from './components/home-page/home-page';
import {Sessions} from './components/sessions/sessions';
import {Analytics} from './components/analytics/analytics';
import {Connections} from './components/connections/connections';
import {Inventory} from './components/inventory/inventory';
import {Shell} from './components/shell/shell';


export const routes: Routes = [


  {path: '', component: Shell,
      children: [
              {path: '', component: HomePage},
              {path: 'analytics', component: Analytics},
              {path: 'sessions', component: Sessions},
              {path: 'connections', component: Connections},
              {path: 'inventory', component: Inventory}
      ]
  },
{path: '**', redirectTo: ' '}

];
