import { PlainObject } from "../models/plain-object";


export const DefaultOptionsPie: any = {
  responsive: false,
  maintainAspectRatio: false,
  plugins: {
    datalabels: {
      formatter: (value: any, ctx: any) => {
        let sum = 0;
        const dataarr = ctx.chart.data.datasets[0].data;
        dataarr.map((data: any) => {
          sum += data;
        });
        const percentage = (value * 100 / sum);
        return percentage !== 0 ? `${value} (${percentage.toFixed(1)}%)` : '';
      },
      color: '#fff',
    },
    legend: {
      labels: {
        generateLabels: (chart: any) => {
          const datasets = chart.data.datasets;
          return datasets[0].data.map((data: any, i: any) => ({
            text: `${chart.data.labels[i]}`,
            fillStyle: datasets[0].backgroundColor[i],
            index: i
          }))
        }
      }
    },
    tooltip: {
      display: false,
    },
  },
};

export const COLORS_MAP: PlainObject = {
  CREATED: "#80c1ed",
  PROCESSING: "#f9dc94",
  SHIPPING: "#2690a1",
  DELIVERED: "#76db9b",
  DECLINED: "#f99baf",
}
