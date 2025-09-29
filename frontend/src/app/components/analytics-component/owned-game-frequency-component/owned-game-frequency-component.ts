import {Component, Input, OnInit} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {FormsModule} from "@angular/forms";
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {AnalyticsService} from '../../../services/analytics-service';
import {ChartData, ChartOptions} from 'chart.js';
import {BasicAnalyticsResponseDto} from '../../../dtos/analytic-dtos/basic-analytics-response-dto';

@Component({
  selector: 'app-owned-game-frequency-component',
    imports: [
        BaseChartDirective,
        FormsModule
    ],
  templateUrl: './owned-game-frequency-component.html',
  styleUrl: './owned-game-frequency-component.css'
})
export class OwnedGameFrequencyComponent implements OnInit{


  //<editor-fold desc="Constructor and Variables">

  @Input() ownedGames: GameResponseDTO[]  = [];

  selectedGame: GameResponseDTO | null = null;
  selectedGameName: string | null = null;
  selectedView: string = "topTen";

  constructor(private analyticsService: AnalyticsService) {
  }
  //</editor-fold>

  //<editor-fold desc="Build Params">
  buildParams(): any {

    return {
      selectedView: this.selectedView
    };

  }
  //</editor-fold>

  //<editor-fold desc="Chart Data">
  chartData: ChartData<'bar'> = {
    labels: [],
    datasets: [
      { data: [],
        label: 'Owned Game Play Frequency',
        backgroundColor: ['#F25C54','#F27059', '#F4845F','#F79D65', '#F7B267']}
    ]
  };
  //</editor-fold>

  //<editor-fold desc="On Init">
  ngOnInit() {

    const params = this.buildParams();

    this.analyticsService.getOwnedGameFrequency(params).subscribe({
      next: (ownedGameFrequencyResponse: BasicAnalyticsResponseDto) => {

        console.log(ownedGameFrequencyResponse);

        this.chartData = {
          labels: ownedGameFrequencyResponse.labels,
          datasets: [{
            data: ownedGameFrequencyResponse.data,
            label: ownedGameFrequencyResponse.label ?? 'Play Rate',
            backgroundColor: ['#F25C54','#F27059', '#F4845F','#F79D65', '#F7B267'],

            barPercentage: 0.8,
            categoryPercentage: 1
          }
          ]
        }
      },
      error: (error: any) => console.error('fail', error)
    })

  }
  //</editor-fold>

  //<editor-fold desc="Filter Results">
  filterResults() {


    const params = this.buildParams();
    console.log(params);

    this.analyticsService.getOwnedGameFrequency(params).subscribe({
      next: (winLossResponse: BasicAnalyticsResponseDto) => {

        if(this.selectedGame) {
          this.selectedGameName = this.selectedGame.title;
        }


        this.chartData = {
          labels: winLossResponse.labels,
          datasets: [{
            data: winLossResponse.data,
            label: winLossResponse.label ?? 'Win Rate',
            backgroundColor:  ['#F25C54','#F27059', '#F4845F','#F79D65', '#F7B267']
          }
          ]
        }
      },
      error: (error: any) => console.error('fail', error)
    })
  }
  //</editor-fold>

  //<editor-fold desc="Options">
  chartOptions: ChartOptions<'bar'> = {
    indexAxis: 'y',
    responsive: true,
    plugins: {
      legend: {
        display: false,
        position: 'bottom',
        labels: {
          font: { size: 18 }
        },
      }
    },
    scales: {
      x: {
        beginAtZero: true,
        ticks: {
          autoSkip: false,
          stepSize: 1,
          precision: 0
        }
      }
    }

  };
  //</editor-fold>


}
