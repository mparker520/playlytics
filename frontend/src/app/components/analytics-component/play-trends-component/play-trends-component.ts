import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {AnalyticsService} from '../../../services/analytics-service';
import {ChartConfiguration, ChartData, ChartOptions} from 'chart.js';

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

  //<editor-fold desc="Color Palette">
  colorPalette = [

    '#ff3254', '#f54659', '#ff5a5f', '#ff7365', '#ff8867',

   '#fa7c5f', '#F27059', '#F4845F', '#F79D65', '#F7B267',

    '#f25c2a', '#f2701e', '#f48428', '#f79d32', '#f7b232',


    '#f89777', '#f2ac6e', '#f4c06e', '#f7d978', '#f7ee78',





  ];
  //</editor-fold>


  //<editor-fold desc="Time Mode View">

  selectedGranularity: string = "year";


  baseIndex!: number;
  selectedBaseYear!: number;


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
        selectedBaseYear: this.selectedBaseYear,

      }


    }


    if(this.selectedGameView === "byGame") {

      return {
        selectedGameView: this.selectedGameView,
        selectedGranularity: this.selectedGranularity,
        selectedBaseYear: this.selectedBaseYear,
        selectedGame1Id: this.selectedGame1!.gameId


      }

    }

    if(this.selectedGameView === "compareGames") {

      return {
        selectedGameView: this.selectedGameView,
        selectedGranularity: this.selectedGranularity,
        selectedBaseYear: this.selectedBaseYear,
        selectedGame1Id: this.selectedGame1!.gameId,
        selectedGame2Id: this.selectedGame2!.gameId

      }

    }


  }
  //</editor-fold>

  //<editor-fold desc="Chart Data">
  chartData: ChartData<'bar'> = {
    labels: [],
    datasets: [ ]
  };
  //</editor-fold>

  //<editor-fold desc="On Init">
  ngOnInit() {


    this.baseIndex = this.rangeOfYears.length - 2;
    this.selectedBaseYear = this.rangeOfYears[this.baseIndex];

    const params = this.buildParams();


    this.analyticsService.getPlayTrends(params).subscribe({
      next: (playTrendsResponse: AdvancedAnalyticsResponseDTO) => {


        const datasets = Object.entries(playTrendsResponse.data).map(
          ([title, values], index) => ({
            label: title,
            data: values,
            backgroundColor: this.colorPalette[index],
            barPercentage: 0.8,
            categoryPercentage: 0.8
          })
        );


        this.chartData = {
          labels: playTrendsResponse.labels,
          datasets: datasets,

        }

      },
      error: (error: any) => console.error('fail', error)

    });
  }

  //</editor-fold>

  //<editor-fold desc="Filter Results">
  filterResults() {

    const params = this.buildParams();


    this.analyticsService.getPlayTrends(params).subscribe({
      next: (playTrendsResponse: AdvancedAnalyticsResponseDTO) => {

        const datasets = Object.entries(playTrendsResponse.data).map(
          ([title, values], index) => ({
            label: title,
            data: values,
            backgroundColor: this.colorPalette[index],
            barPercentage: 0.8,
            categoryPercentage: 1
          })
        );

        this.chartData = {
          labels:playTrendsResponse.labels,
          datasets: datasets,
        }

      },
      error: (error: any) => console.error('fail', error)

    });
  }
  //</editor-fold>

  //<editor-fold desc="Options">
  chartOptions: ChartConfiguration<'bar'>['options'] = {

    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'bottom',
        labels: {
          font: { size: 12 },
        },
      }
    },
    scales: {
      x: {
        stacked: true,

        ticks: {
          autoSkip: false,
          precision: 0
        }
      },
      y: {
        stacked: true,
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
