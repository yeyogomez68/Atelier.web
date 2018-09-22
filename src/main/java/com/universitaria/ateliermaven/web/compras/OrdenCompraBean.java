/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Ordencompra;
import com.universitaria.atelier.web.jpa.Ordencompradeta;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.ateliermaven.ejb.compras.OrdenCompraDetaEJB;
import com.universitaria.ateliermaven.ejb.compras.OrdenCompraEJB;
import com.universitaria.ateliermaven.ejb.inventario.ProveedorEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jeisson.gomez
 */
public class OrdenCompraBean implements Serializable{

    @EJB
    private OrdenCompraEJB ordenCompraEJB;
    
    @EJB
    private ProveedorEJB proveedorEJB;
    
    @EJB
    private OrdenCompraDetaEJB ordenCompraDetaEJB;
    
    private List<Ordencompra> listOrdenesCompra;
    private List<SelectItem> listProveedores;
    private List<Ordencompradeta> listRqOrdenCompraDeta;
    private List<Ordencompradeta> listRqOrdenCompraDetaAdd;
    
    private String proveedorId;
    private String descOC;
    
    private double cantidad;
    
    private Usuario user;
    /**
     * Creates a new instance of OrdenCompraBean
     */
    public OrdenCompraBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Ordencompra> getListOrdenesCompra() {
        if(listOrdenesCompra == null || listOrdenesCompra.isEmpty()){            
            listOrdenesCompra = new ArrayList<>();
            setListOrdenesCompra(ordenCompraEJB.getOrdenesComprasByUser(user));
        }
        return listOrdenesCompra;
    }

    public void setListOrdenesCompra(List<Ordencompra> listOrdenesCompra) {
        
        this.listOrdenesCompra = listOrdenesCompra;
    }

    public List<SelectItem> getListProveedores() {
        if(listProveedores==null || listProveedores.isEmpty()){
            listProveedores = new ArrayList<>();
            setListProveedores(proveedorEJB.getSelectItemProveedor());
        }
        return listProveedores;
    }

    public void setListProveedores(List<SelectItem> listProveedores) {
        this.listProveedores = listProveedores;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }           

    public String getDescOC() {
        return descOC;
    }

    public void setDescOC(String descOC) {
        this.descOC = descOC;
    }

    public List<Ordencompradeta> getListRqOrdenCompraDeta() {
        if(listRqOrdenCompraDeta==null){
            listRqOrdenCompraDeta = new ArrayList<>();            
        }
        return listRqOrdenCompraDeta;
    }

    public void setListRqOrdenCompraDeta(List<Ordencompradeta> listRqOrdenCompraDeta) {
        this.listRqOrdenCompraDeta = listRqOrdenCompraDeta;
    }

    public List<Ordencompradeta> getListRqOrdenCompraDetaAdd() {
         if(listRqOrdenCompraDetaAdd==null){
            listRqOrdenCompraDetaAdd = new ArrayList<>();            
        }
        return listRqOrdenCompraDetaAdd;
    }

    public void setListRqOrdenCompraDetaAdd(List<Ordencompradeta> listRqOrdenCompraDetaAdd) {
        this.listRqOrdenCompraDetaAdd = listRqOrdenCompraDetaAdd;
    }
    
    public void agregarAOC(Ordencompradeta itemOC){
        RequestContext req = RequestContext.getCurrentInstance();
     
        req.execute("PF('dlg4').show();"); 
    }
    
    public void agregarAOC(){
        setListOrdenesCompra(null);
        System.out.println("com.universitaria.ateliermaven.web.compras.OrdenCompraBean.agregarAOC()");
    }
    
    
    public void verOrdenCompra (Ordencompra orCompra){
        RequestContext req = RequestContext.getCurrentInstance();     
        req.execute("PF('dlg4').show();"); 
    }
    
    public void crearOrdenCompra(){
    
    }
    
    public void crearOrdenCompraPanel(){
        RequestContext req = RequestContext.getCurrentInstance();
        limpiarLista();
        setListRqOrdenCompraDeta(ordenCompraDetaEJB.getDetalleRqForOc());        
        req.execute("PF('dlg1').show();"); 
    }
    public void limpiarLista(){
        listRqOrdenCompraDeta = new ArrayList<>();
        listRqOrdenCompraDetaAdd = new ArrayList<>();
    }
}
