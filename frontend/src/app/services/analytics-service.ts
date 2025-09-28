import { Injectable } from '@angular/core';
import {WinLossResponseDTO} from '../dtos/analytic-dtos/win-loss-response-dto';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {List} from 'postcss/lib/list';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>

  //<editor-fold desc="Get Win/Loss Ratio">
  public getWinLossRatio(params: any): Observable<WinLossResponseDTO> {
        return this.http.get<WinLossResponseDTO>(`${environment.apiUrl}/win-loss-ratio`, {params, withCredentials: true})
  }
  //</editor-fold>


}
