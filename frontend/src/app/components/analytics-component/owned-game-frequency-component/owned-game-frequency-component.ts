import {Component, Input, OnInit} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {FormsModule} from "@angular/forms";
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

  selectedOwnedGame: OwnedGameResponseDTO | null = null;
  barChartColors: string[] = [];
  chartHeight?: number;

  selectedView: string = "topFive";

  stepSize?: number;

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

  //<editor-fold desc="Build Colors">
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
  //</editor-fold>

  setChartHeight(responseLength: number) {
    const barWidth = 10;
    return barWidth * 210;

  }


  //<editor-fold desc="Chart Data">
  chartData: ChartData<'bar'> = {
    labels: [],
    datasets: [
      { data: [],
        label: 'Owned Game Play Frequency'}
    ]
  };
  //</editor-fold>

  //<editor-fold desc="On Init">
  ngOnInit() {

    const params = this.buildParams();



    this.analyticsService.getOwnedGameFrequency(params).subscribe({
      next: (winLossResponse: BasicAnalyticsResponseDto) => {

        this.buildColors(winLossResponse.data.length);

        const max = Math.max(...winLossResponse.data);
        this.stepSize = (max / 10);

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
    this.chartHeight = this.setChartHeight(21);


    this.analyticsService.getOwnedGameFrequency(params).subscribe({
      next: (winLossResponse: BasicAnalyticsResponseDto) => {

        this.buildColors(winLossResponse.data.length);


        const max = Math.max(...winLossResponse.data);
       // this.stepSize = (max / 10);

        this.chartData = {
          labels: winLossResponse.labels,
          datasets: [{
            data: winLossResponse.data,
            label: winLossResponse.label ?? 'Win Rate',
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

  //<editor-fold desc="Options">
  chartOptions: ChartOptions<'bar'> = {
    indexAxis: 'x',
    responsive: true,
    maintainAspectRatio: false,
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

        ticks: {
          autoSkip: false,
          font: {size: 10}
        }
      },
      y: {
        beginAtZero: true,
        suggestedMax: 5,
        ticks: {
          stepSize: 1,
          precision: 0
        }
      }
    }

  };
  //</editor-fold>


}
