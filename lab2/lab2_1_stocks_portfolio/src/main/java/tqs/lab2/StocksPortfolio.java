package tqs.lab2;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stocks = new ArrayList<>();
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getTotalValue() {
        return stocks.stream().reduce(0.0, (total, stock) -> total + stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity(), Double::sum);
    }
}
