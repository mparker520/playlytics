import { Component } from '@angular/core';
import {BaseChartDirective, provideCharts, withDefaultRegisterables} from 'ng2-charts';
import { ChartData } from 'chart.js';

@Component({
  selector: 'app-analytics-component',
  standalone: true,
  imports: [
    BaseChartDirective
  ],
  providers: [provideCharts(withDefaultRegisterables())],
  template:  `
    <h2>Test Chart</h2>
    <canvas baseChart
      [data]="chartData"
      [type]="'line'">
    </canvas>
  `,
  styleUrl: './analytics-component.css'
})
export class AnalyticsComponent {
  chartData: ChartData<'line'> = {
    labels: ['Red', 'Blue', 'Yellow'],
    datasets: [
      { data: [5, 10, 7],
        label: 'Votes',
      backgroundColor: ['#F7B267', '#F4845F', '#F25C54']},
      { data: [12, 19, 3],
        label: 'Votes',
        backgroundColor: ['#555555', '#F4845F', '#F25C54']}
    ]
  };
}
