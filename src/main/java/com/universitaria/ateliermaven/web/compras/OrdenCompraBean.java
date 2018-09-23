/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Ordencompra;
import com.universitaria.atelier.web.jpa.Ordencompradeta;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.OrdenCompraDetaUtil;
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
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author jeisson.gomez
 */
public class OrdenCompraBean implements Serializable {

    @EJB
    private OrdenCompraEJB ordenCompraEJB;

    @EJB
    private ProveedorEJB proveedorEJB;

    @EJB
    private OrdenCompraDetaEJB ordenCompraDetaEJB;

    private List<Ordencompra> listOrdenesCompra;
    private List<SelectItem> listProveedores;
    private DualListModel<OrdenCompraDetaUtil> listRqOrdenCompraDeta;
    private List<Ordencompradeta> listDetaOc;
    private OrdenCompraDetaUtil util;
    private Integer idOc;
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

    public OrdenCompraDetaUtil getUtil() {
        return util;
    }

    public void setUtil(OrdenCompraDetaUtil util) {
        this.util = util;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Ordencompra> getListOrdenesCompra() {
        if (listOrdenesCompra == null || listOrdenesCompra.isEmpty()) {
            listOrdenesCompra = new ArrayList<>();
            setListOrdenesCompra(ordenCompraEJB.getOrdenesComprasByUser(user));
        }
        return listOrdenesCompra;
    }

    public void setListOrdenesCompra(List<Ordencompra> listOrdenesCompra) {

        this.listOrdenesCompra = listOrdenesCompra;
    }

    public List<Ordencompradeta> getListDetaOc() {
        if(listDetaOc==null || listDetaOc.isEmpty()){
            listDetaOc = new ArrayList<>();
        }
        return listDetaOc;
    }

    public void setListDetaOc(List<Ordencompradeta> listDetaOc) {
        this.listDetaOc = listDetaOc;
    }

    public List<SelectItem> getListProveedores() {
        if (listProveedores == null || listProveedores.isEmpty()) {
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

    public DualListModel<OrdenCompraDetaUtil> getListRqOrdenCompraDeta() {
        if (listRqOrdenCompraDeta == null || (listRqOrdenCompraDeta.getSource().isEmpty() && listRqOrdenCompraDeta.getTarget().isEmpty())) {
            List<OrdenCompraDetaUtil> source = new ArrayList<>();
            List<OrdenCompraDetaUtil> target = new ArrayList<>();
            source = ordenCompraDetaEJB.getDetalleRqForOc();
            setListRqOrdenCompraDeta(new DualListModel<OrdenCompraDetaUtil>(source, target));
        }
        return listRqOrdenCompraDeta;
    }

    public void setListRqOrdenCompraDeta(DualListModel<OrdenCompraDetaUtil> listRqOrdenCompraDeta) {
        this.listRqOrdenCompraDeta = listRqOrdenCompraDeta;
    }

    public void agregarAOC(Ordencompradeta itemOC) {
        RequestContext req = RequestContext.getCurrentInstance();

        req.execute("PF('dlg4').show();");
    }

    public void agregarAOC() {
        setListOrdenesCompra(null);
        System.out.println("com.universitaria.ateliermaven.web.compras.OrdenCompraBean.agregarAOC()");
    }

    public void verOrdenCompra(Ordencompra orCompra) {
        RequestContext req = RequestContext.getCurrentInstance();
        idOc = orCompra.getOrdenCompraId();
        llenarDetalleOc();
        req.execute("PF('dlg2').show();");
    }
    
    public void llenarDetalleOc(){
        listDetaOc = new ArrayList<>();
        listDetaOc = ordenCompraDetaEJB.obtenerOrdenesCompraPorId(idOc);
    }
    
    public Double sumOc(int op){
        Double sum= new Double(0);
        for (OrdenCompraDetaUtil ordenCompraDetaUtil : listRqOrdenCompraDeta.getTarget()) {
            switch (op) {
                case 1:
                    sum += new Double(ordenCompraDetaUtil.getValorBruto());
                    break;
                case 2:
                    sum += new Double(ordenCompraDetaUtil.getValorIva());
                    break;
                case 3:
                    sum += new Double(ordenCompraDetaUtil.getValorTotal());
                    break;
                default:
                    break;
            }
        }
        return sum;
    }

    public void crearOrdenCompra() {
        RequestContext req = RequestContext.getCurrentInstance();
        Integer ordenCompra = ordenCompraEJB.crearOrdenCompra(descOC, proveedorId, sumOc(1), sumOc(2), sumOc(3), user);
        if(ordenCompra!=null){
            if(ordenCompraDetaEJB.crearDetalleCompra(ordenCompra, getListRqOrdenCompraDeta().getTarget())){
                limpiarDatos();
                req.update(":form");
                req.execute("PF('dlg1').hide();");  
            }
            
        }
    }

    public void crearOrdenCompraPanel() {
        RequestContext req = RequestContext.getCurrentInstance();
        limpiarDatos();
        req.execute("PF('dlg1').show();");
    }

    public void onTransfer(TransferEvent event) {
        util = new OrdenCompraDetaUtil();
        for (Object item : event.getItems()) {
            util.setEncabezado(((OrdenCompraDetaUtil) item).getEncabezado());
            util.setMaterialId(((OrdenCompraDetaUtil) item).getMaterialId());
            util.setMaterial(((OrdenCompraDetaUtil) item).getMaterial());
            util.setCatidad(((OrdenCompraDetaUtil) item).getCatidad());
            util.setValorUnitario("0");
            util.setValorBruto("0");
            util.setValorIva("0");
            util.setValorTotal("0");
        }
        for (OrdenCompraDetaUtil listaocDeta : listRqOrdenCompraDeta.getTarget()) {
            if (listaocDeta.getEncabezado().equals(util.getEncabezado()) && listaocDeta.getMaterialId().equals(util.getMaterialId())) {
                RequestContext req = RequestContext.getCurrentInstance();
                req.execute("PF('dlg4').show();");
                break;
            }
        }
    }

    public void handleKeyEventVal() {
        util.setValorBruto(String.valueOf(new Double (util.getValorUnitario())*(new Double (util.getCatidad()))));
        util.setValorIva(String.valueOf(new Double (util.getValorUnitario())*0.16));
        util.setValorTotal(String.valueOf((new Double (util.getValorBruto())+new Double (util.getValorIva()))));        
    }
    
    public void cantidadesEdit(){
        for (OrdenCompraDetaUtil listaocDeta : listRqOrdenCompraDeta.getTarget()) {
            if(listaocDeta.getEncabezado().equals(util.getEncabezado()) && listaocDeta.getMaterialId().equals(util.getMaterialId())){
                listaocDeta.setValorUnitario(util.getValorUnitario());
                listaocDeta.setValorBruto(util.getValorBruto());
                listaocDeta.setValorIva(util.getValorIva());
                listaocDeta.setValorTotal(util.getValorTotal());
                break;
            }
        }
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg4').hide();"); 
    }
    
    private void limpiarDatos(){
        listRqOrdenCompraDeta.getSource().clear();
        listRqOrdenCompraDeta.getTarget().clear();
    }
}
