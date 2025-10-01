import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {OwnedGameResponseDTO} from '../../../dtos/owned-game-response-dto';
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {AnalyticsService} from '../../../services/analytics-service';
import {ChartConfiguration, ChartData, ChartOptions} from 'chart.js';
import {BasicAnalyticsResponseDto} from '../../../dtos/analytic-dtos/basic-analytics-response-dto';
import {AdvancedAnalyticsResponseDTO} from '../../../dtos/analytic-dtos/advanced-analytics-response-dto';

@Component({
  selector: 'app-play-trends-component',
  imports: [
    BaseChartDirective,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './play-trends-component.html',
  styleUrl: './play-trends-component.css'
})
export class PlayTrendsComponent implements OnInit {


  //<editor-fold desc="Constructor and Variables">

  @Input() playedGames: GameResponseDTO[]  = [];
  @Input() rangeOfYears!: number[];


  //<editor-fold desc="Game Views">
  selectedGameView: string = "all";

  selectedGame1: GameResponseDTO | null = null;

  selectedGame2: GameResponseDTO | null = null;

  //</editor-fold>


  //<editor-fold desc="Time Mode View">

  selectedGranularity: string = "year";

  startIndex!: number;
  selectedStartingYear!: number;

  endIndex!: number;
  selectedEndingYear!: number;


  //</editor-fold>



  constructor(private analyticsService: AnalyticsService) {
  }

  //</editor-fold>

  //<editor-fold desc="Build Params">
  buildParams(): any {


    if(this.selectedGameView === "all") {



      return {
        selectedGameView: this.selectedGameView,
        selectedGranularity: this.selectedGranularity,
        selectedStartingYear: this.selectedStartingYear,
        selectedEndingYear: this.selectedEndingYear,

      }


    }


    if(this.selectedGameView === "byGame") {

      return {
        selectedGameView: this.selectedGameView,
        selectedGranularity: this.selectedGranularity,
        selectedStartingYear: this.selectedStartingYear,
        selectedEndingYear: this.selectedEndingYear,
        selectedGame1Id: this.selectedGame1!.gameId


      }

    }

    if(this.selectedGameView === "compareGames") {

      return {
        selectedGameView: this.selectedGameView,
        selectedGranularity: this.selectedGranularity,
        selectedStartingYear: this.selectedStartingYear,
        selectedEndingYear: this.selectedEndingYear,
        selectedGame1Id: this.selectedGame1!.gameId,
        selectedGame2Id: this.selectedGame2!.gameId

      }

    }


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

    this.startIndex = this.rangeOfYears.length -3;
    this.endIndex = this.rangeOfYears.length - 1;
    this.selectedStartingYear = this.rangeOfYears[this.startIndex];
    this.selectedEndingYear = this.rangeOfYears[this.endIndex];

    const params = this.buildParams();

    this.analyticsService.getPlayTrends(params).subscribe({
      next: (playTrendsResponse: AdvancedAnalyticsResponseDTO) => {
          console.log(playTrendsResponse)
       // this.chartData = {
          //labels: playTrendsResponse.labels,
        //  datasets: [{
          //  data: playTrendsResponse.data,
         //   label: playTrendsResponse.label ?? 'Play Rate',
          //  backgroundColor: ['#F25C54','#F27059', '#F4845F','#F79D65', '#F7B267'],

          //  barPercentage: 0.8,
          //  categoryPercentage: 1
         // }
          //]
       // }
      },
      error: (error: any) => console.error('fail', error)
    })

  }
  //</editor-fold>

  //<editor-fold desc="Filter Results">
  filterResults() {


    const params = this.buildParams();
    console.log(params)

    this.analyticsService.getPlayTrends(params).subscribe({
      next: (playTrendsResponse: AdvancedAnalyticsResponseDTO) => {

        console.log(playTrendsResponse)

       // this.chartData = {
          //labels: playTrendsResponse.labels,
          //datasets: [{
            //data: playTrendsResponse.data,
           // label: playTrendsResponse.label ?? 'Win Rate',
//backgroundColor:  ['#F25C54','#F27059', '#F4845F','#F79D65', '#F7B267']
        //  }
         // ]
        //}
      },
      error: (error: any) => console.error('fail', error)
    })
  }
  //</editor-fold>

  //<editor-fold desc="Options">
  chartOptions: ChartConfiguration<'bar'>['options'] = {

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
        stacked: true,
        beginAtZero: true,
        suggestedMax: 5,
        ticks: {
          autoSkip: false,
          stepSize: 1,
          precision: 0
        }
      },
      y: {
        stacked: true,
      }
    }

  };
  //</editor-fold>


}
