import {Component, Input, OnInit} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {FormsModule} from "@angular/forms";
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {AnalyticsService} from '../../../services/analytics-service';
import {ChartData, ChartOptions} from 'chart.js';
import {BasicAnalyticsResponseDto} from '../../../dtos/analytic-dtos/basic-analytics-response-dto';
import {OwnedGameResponseDTO} from '../../../dtos/owned-game-response-dto';

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

  @Input() ownedGames: OwnedGameResponseDTO[]  = [];

  selectedOwnedGame: GameResponseDTO | null = null;
  selectedOwnedGameName: string | null = null;
  barChartColors: string[] = [];

  selectedView: string = "topFive";

  constructor(private analyticsService: AnalyticsService) {
  }
  //</editor-fold>

  //<editor-fold desc="Build Params">
  buildParams(): any {

    if (this.selectedView === "byGame")  {

      return {
        selectedView: this.selectedView,
        selectedGameId: this.selectedOwnedGame?.gameId

      }
    }

    else {
      return {
        selectedView: this.selectedView
      };
    }



  }
  //</editor-fold>



  buildColors(responseLength: number): void {
       this.barChartColors = [];
       const baseColors =  ['#F25C54','#F27059', '#F4845F','#F79D65', '#F7B267']
       const colorAllocation =  responseLength / 5;

       if(responseLength == 1) {

         function getRandomInt(min: number, max: number): number {
           return Math.floor(Math.random() * (max - min + 1)) + min;
         }

         const random = getRandomInt(0, 4);

         this.barChartColors.push(baseColors[random]);

       }

       else {
         for(let i = 0; i < 5; i++) {
           for (let j = 0; j < colorAllocation; j++)
             this.barChartColors.push(baseColors[i]);

         }
       }

}

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
      next: (winLossResponse: BasicAnalyticsResponseDto) => {

        this.buildColors(winLossResponse.data.length);

        this.chartData = {
          labels: winLossResponse.labels,
          datasets: [{
            data: winLossResponse.data,
            label: winLossResponse.label ?? 'Play Rate',
            backgroundColor: this.barChartColors,

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


    this.analyticsService.getOwnedGameFrequency(params).subscribe({
      next: (winLossResponse: BasicAnalyticsResponseDto) => {

        this.buildColors(winLossResponse.data.length);



        if(this.selectedOwnedGame) {
          this.selectedOwnedGameName = this.selectedOwnedGame.title;
        }


        this.chartData = {
          labels: winLossResponse.labels,
          datasets: [{
            data: winLossResponse.data,
            label: winLossResponse.label ?? 'Win Rate',
            backgroundColor: this.barChartColors,
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
        suggestedMax: 5,
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
