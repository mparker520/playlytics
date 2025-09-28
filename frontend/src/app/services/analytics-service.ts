import { Injectable } from '@angular/core';
import {WinLossResponseDTO} from '../dtos/analytic-dtos/win-loss-response-dto';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  //<editor-fold desc="Constructor">
  constructor(private http: HttpClient) {

  }
  //</editor-fold>

  //<editor-fold desc="Get Win/Loss Ratio">
  public getWinLossRatio(): Observable<WinLossResponseDTO> {
        return this.http.get<WinLossResponseDTO>(`${environment.apiUrl}/win-loss-ratio`, {withCredentials: true})
  }
  //</editor-fold>


}
