const industrySentiment = [];
const growthPotential = [];
const productsAndServices = [];
const lis = document.querySelectorAll('li');
for (const li of lis) {
    const text = li.textContent;
    if (text.includes('INDUSTRY_SENTIMENT')) {
        const value = parseFloat(text.match(/= (\d+\.\d+)/)[1]);
        industrySentiment.push(value);
    }
    if (text.includes('GROWTH_POTENTIAL')) {
        const value = parseFloat(text.match(/= (\d+\.\d+)/)[1]);
        growthPotential.push(value);
    }
    if (text.includes('PRODUCTS_AND_SERVICES')) {
        const value = parseFloat(text.match(/= (\d+\.\d+)/)[1]);
        productsAndServices.push(value);
    }
}
const ctx = document.getElementById('sentimentChart').getContext('2d');
const chart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['0', '1', '2', '3', '4', '5', '6'],
        datasets: [{
            label: 'Industry Sentiment',
            data: industrySentiment,
            borderColor: 'blue',
            fill: false
        }, {
            label: 'Growth Potential',
            data: growthPotential,
            borderColor: 'green',
            fill: false
        }, {
            label: 'Products & Services',
            data: productsAndServices,
            borderColor: 'red',
            fill: false
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    min: 0,
                    max: 1
                }
            }]
        }
    }
});