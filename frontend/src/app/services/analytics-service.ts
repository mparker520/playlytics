import { Injectable } from '@angular/core';
import {BasicAnalyticsResponseDto} from '../dtos/analytic-dtos/basic-analytics-response-dto';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {List} from 'postcss/lib/list';
import {AdvancedAnalyticsResponseDTO} from '../dtos/analytic-dtos/advanced-analytics-response-dto';

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

  //<editor-fold desc="Get Owned Game Play Frequency">
  public getOwnedGameFrequency(params: any): Observable<BasicAnalyticsResponseDto> {
    return this.http.get<BasicAnalyticsResponseDto>(`${environment.apiUrl}/owned-game-frequency`, {params, withCredentials: true})
  }
  //</editor-fold>

  //<editor-fold desc="Get Play Trends">
  public getPlayTrends(params: any): Observable<AdvancedAnalyticsResponseDTO> {
    console.log(this.http.get<AdvancedAnalyticsResponseDTO>(`${environment.apiUrl}/play-trends`, {params, withCredentials: true}))
    return this.http.get<AdvancedAnalyticsResponseDTO>(`${environment.apiUrl}/play-trends`, {params, withCredentials: true})

  }
  //</editor-fold>


}
