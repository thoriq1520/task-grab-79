import React, { useEffect, useState } from 'react';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';
import { Doughnut } from 'react-chartjs-2';
import axios from 'axios';

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export const options = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    title: {
      display: true,
      text: 'Transaction Status',
      color: 'white',
      font: {
        size: 16,
      },
    },
    legend: {
      display: true,
      position: 'top',
      labels: {
        color: 'white',
      },
    },
  },
  tooltips: {
    callbacks: {
      label: (tooltipItem, data) => {
        const dataset = data.datasets[tooltipItem.datasetIndex];
        const total = dataset.data.reduce((sum, value) => sum + value, 0);
        const value = dataset.data[tooltipItem.index];
        const percentage = ((value / total) * 100).toFixed(2) + '%';
        return `${data.labels[tooltipItem.index]}: ${percentage}`;
      },
    },
  },
};

export function DonutChart() {
  const [groupedData, setGroupedData] = useState({ labels: [], datasets: [] });

  useEffect(() => {
    axios.get('http://localhost:8080/transactions')
      .then((response) => {
        const apiData = response.data.data;
        const grouped = {};

        apiData.forEach((transaction) => {
          const status = transaction.status;

          if (!grouped[status]) {
            grouped[status] = 1;
          } else {
            grouped[status]++;
          }
        });

        const labels = Object.keys(grouped);
        const data = Object.values(grouped);

        const dataset = {
          data,
          backgroundColor: [
            'rgba(255, 99, 132, 0.5)',
            'rgba(54, 162, 235, 0.5)',
            'rgba(75, 192, 192, 0.5)',
          ],
        };

        setGroupedData({ labels, datasets: [dataset] });
      })
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }, []);

  return (
    <div className="donut-chart">
      <Doughnut data={groupedData} options={options} width={250} height={250}/>
    </div>
  );
}

export default DonutChart;
