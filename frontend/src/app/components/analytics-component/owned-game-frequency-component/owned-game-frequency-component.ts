import {Component, Input, OnInit} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {FormsModule} from "@angular/forms";
import {GameResponseDTO} from '../../../dtos/game-response-dto';
import {ScoringModelEnum} from '../../../enums/scoring-model-enum';
import {AnalyticsService} from '../../../services/analytics-service';
import {ChartData, ChartOptions} from 'chart.js';
import {WinLossResponseDTO} from '../../../dtos/analytic-dtos/win-loss-response-dto';

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

  @Input() playedGames: GameResponseDTO[]  = [];
  scoringModels: ScoringModelEnum[] = [ScoringModelEnum.RANKING,
    ScoringModelEnum.TEAM, ScoringModelEnum.COOPERATIVE]


  //<editor-fold desc="Constructor and Variables">
  selectedGame: GameResponseDTO | null = null;
  selectedGameName: string | null = null;
  selectedScoringModel: ScoringModelEnum | null=null;

  constructor(private analyticsService: AnalyticsService) {
  }
  //</editor-fold>

  //<editor-fold desc="Build Params">
  buildParams(): any {
    let params: any = {

    }

    if(this.selectedGame && this.selectedScoringModel) {

      params = {
        selectedGame: this.selectedGame.gameId,
        selectedScoringModel: this.selectedScoringModel
      }

    }

    else if(this.selectedGame) {

      params = {
        selectedGame: this.selectedGame.gameId
      }

    }

    else if(this.selectedScoringModel) {

      params = {
        selectedScoringModel: this.selectedScoringModel
      }

    }

    return params;

  }
  //</editor-fold>

  //<editor-fold desc="Chart Data">
  chartData: ChartData<'bar'> = {
    labels: ['Wins', 'Losses'],
    datasets: [
      { data: [],
        label: 'Win/Loss Ratio',
        backgroundColor: ['#F7B267', '#F79D65']}
    ]
  };
  //</editor-fold>

  //<editor-fold desc="On Init">
  ngOnInit() {

    const params = this.buildParams();

    this.analyticsService.getWinLossRatio(params).subscribe({
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
  //</editor-fold>

  //<editor-fold desc="Filter Results">
  filterResults() {


    console.log(this.selectedGame)
    console.log(this.selectedGameName)

    const params = this.buildParams();


    this.analyticsService.getWinLossRatio(params).subscribe({
      next: (winLossResponse: WinLossResponseDTO) => {

        if(this.selectedGame) {
          this.selectedGameName = this.selectedGame.title;
        }



        this.chartData = {
          labels: winLossResponse.labels,
          datasets: [{
            data: winLossResponse.data,
            label: winLossResponse.label ?? 'Win Rate',
            backgroundColor: ['#F7B267', '#F79D65']
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
    responsive: true,
    plugins: {
      legend: {
        position: 'bottom',
        labels: {
          font: { size: 18 }
        },
      }
    },

  };
  //</editor-fold>


}
