import {Component, OnInit} from '@angular/core';
import {ChartData, ChartOptions} from 'chart.js';
import {BaseChartDirective, provideCharts, withDefaultRegisterables} from 'ng2-charts';
import {AnalyticsService} from '../../../services/analytics-service';

import {WinLossResponseDTO} from '../../../dtos/analytic-dtos/win-loss-response-dto';

@Component({
  selector: 'app-win-loss-component',
  imports: [
    BaseChartDirective
  ],
  providers: [provideCharts(withDefaultRegisterables())],
  templateUrl: './win-loss-component.html',
  styleUrl: './win-loss-component.css'
})
export class WinLossComponent implements OnInit {

  constructor(private analyticsService: AnalyticsService) {
  }


  chartData: ChartData<'pie'> = {
    labels: ['Wins', 'Losses'],
    datasets: [
      { data: [],
        label: 'Win/Loss Ratio',
        backgroundColor: ['#F7B267', '#F4845F']}
    ]
  };

  chartOptions: ChartOptions<'pie'> = {
    responsive: true,
    plugins: {
      legend: {
        position: 'bottom',
        labels: {
          font: { size: 18 }
        }
      }
    }

  };

  ngOnInit() {

    this.analyticsService.getWinLossRatio().subscribe({
        next: (winLossResponse: WinLossResponseDTO) => {
          this.chartData = {
            labels: winLossResponse.labels,
            datasets: [{
              data: winLossResponse.data,
              label: winLossResponse.label ?? 'Win Rate',
              backgroundColor: ['#F7B267', '#F4845F']
              }
            ]
          }
        },
      error: (error: any) => console.error('fail', error)
    })

  }



}
