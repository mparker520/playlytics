import { Injectable } from '@angular/core';
import {BasicAnalyticsResponseDto} from '../dtos/analytic-dtos/basic-analytics-response-dto';
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
  public getWinLossRatio(params: any): Observable<BasicAnalyticsResponseDto> {
        return this.http.get<BasicAnalyticsResponseDto>(`${environment.apiUrl}/win-loss-ratio`, {params, withCredentials: true})
  }
  //</editor-fold>

  public getOwnedGameFrequency(): Observable<BasicAnalyticsResponseDto> {
    return this.http.get<BasicAnalyticsResponseDto>(`${environment.apiUrl}/owned-game-frequency`, {withCredentials: true})
  }


}
