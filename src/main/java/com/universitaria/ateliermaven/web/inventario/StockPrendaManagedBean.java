/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.inventario;

import com.universitaria.atelier.web.jpa.Stockprenda;
import com.universitaria.ateliermaven.ejb.inventario.StockPrendaEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author SoulHunter
 */
public class StockPrendaManagedBean implements Serializable {
    
    @EJB
    private StockPrendaEJB stockPrendaEJB;
    
    private List<Stockprenda> stockPrendas;
    
    public StockPrendaEJB getStockPrendaEJB() {
        return stockPrendaEJB;
    }
    
    public void setStockPrendaEJB(StockPrendaEJB stockPrendaEJB) {
        this.stockPrendaEJB = stockPrendaEJB;
    }
    
    public List<Stockprenda> getStockPrendas() {
        if (stockPrendas == null) {
            stockPrendas = new ArrayList<>();
            setStockPrendas(stockPrendaEJB.getStockPrenda());
        }
        return stockPrendas;
    }
    
    public void setStockPrendas(List<Stockprenda> stockPrendas) {
        this.stockPrendas = stockPrendas;
    }
    
}
