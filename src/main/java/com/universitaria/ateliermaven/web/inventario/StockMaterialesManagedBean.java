/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.inventario;

import com.universitaria.atelier.web.jpa.Ordencompradeta;
import com.universitaria.atelier.web.jpa.Stockmateriales;
import com.universitaria.ateliermaven.ejb.compras.OrdenCompraDetaEJB;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;

/**
 *
 * @author SoulHunter
 */
public class StockMaterialesManagedBean {

    @EJB
    private StockMaterialesEJB stockMaterialesEJB;
    @EJB
    private OrdenCompraDetaEJB ordenCompraDetaEJB;

    private List<Stockmateriales> stockMateriales;
    private List<Ordencompradeta> ordenCompras;

    public StockMaterialesEJB getStockMaterialesEJB() {
        return stockMaterialesEJB;
    }

    public void setStockMaterialesEJB(StockMaterialesEJB stockMaterialesEJB) {
        this.stockMaterialesEJB = stockMaterialesEJB;
    }

    public OrdenCompraDetaEJB getOrdenCompraDetaEJB() {
        return ordenCompraDetaEJB;
    }

    public void setOrdenCompraDetaEJB(OrdenCompraDetaEJB ordenCompraDetaEJB) {
        this.ordenCompraDetaEJB = ordenCompraDetaEJB;
    }

    public List<Stockmateriales> getStockMateriales() {
        if (stockMateriales == null) {
            stockMateriales = new ArrayList<>();
            setStockMateriales(stockMaterialesEJB.getStockMaterial());
        }
        return stockMateriales;
    }

    public void setStockMateriales(List<Stockmateriales> stockMateriales) {
        this.stockMateriales = stockMateriales;
    }

    public List<Ordencompradeta> getOrdenCompras() {
        if (ordenCompras == null) {
            ordenCompras = new ArrayList<>();
            setOrdenCompras(ordenCompraDetaEJB.getOrdenCompraDetaPorEstado());
        }
        return ordenCompras;
    }

    public void setOrdenCompras(List<Ordencompradeta> ordenCompras) {
        this.ordenCompras = ordenCompras;
    }

    public void ingresarInsumo(Ordencompradeta ordenCompraDeta) {
        RequestContext req = RequestContext.getCurrentInstance();
        if (ordenCompraDetaEJB.setActualizarEstadoOrderCompraDeta(ordenCompraDeta, EstadoEnum.APROBADO.getId())) {
            Comunes.mensaje((stockMaterialesEJB.setModificarStockMaterial(ordenCompraDeta.getMaterialId(), ordenCompraDeta.getOrdenCompraCantidad().intValue()) ? "Se ha ingresado el insumo correctamente " : "Error ingresando el insumo"), ordenCompraDeta.getMaterialId().getMaterialNombre());
            actualizarLista();
            if (getOrdenCompras().isEmpty()) {
                stockMateriales.clear();
                setStockMateriales(stockMaterialesEJB.getStockMaterial());
                req.update(":form");
            }
        } else {
            Comunes.mensaje("No es posible ingresar el insumo", "");
        }

    }

    public void ingresarTodos() {

        for (Ordencompradeta ocd : ordenCompras) {
            if (ordenCompraDetaEJB.setActualizarEstadoOrderCompraDeta(ocd, EstadoEnum.APROBADO.getId())) {
                Comunes.mensaje((stockMaterialesEJB.setModificarStockMaterial(ocd.getMaterialId(), ocd.getOrdenCompraCantidad().intValue()) ? "Se ha ingresado el insumo correctamente " : "Error ingresando el insumo"), ocd.getMaterialId().getMaterialNombre());
            } else {
                Comunes.mensaje("No es posible ingresar el insumo", "");
            }
        }
        actualizarLista();
        stockMateriales.clear();
        setStockMateriales(stockMaterialesEJB.getStockMaterial());
        if (ordenCompras.isEmpty()) {
            RequestContext req = RequestContext.getCurrentInstance();
            req.execute("PF('dlg1').hide();");
        }
    }

    private void actualizarLista() {
        ordenCompras.clear();
        ordenCompras = null;
        getOrdenCompras();

    }

}
