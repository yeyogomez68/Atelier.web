/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.ateliermaven.ejb.compras.DetalleRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.compras.EncabezadoRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.web.session.TemplateControllerManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author jeisson.gomez
 */
public class RequerimientoManagedBean implements Serializable{
    
    @EJB
    private MaterialEJB materialEJB;
    
    @EJB
    private EncabezadoRequerimientoEJB encabezadoRequerimientoEJB;
    
    @EJB
    private DetalleRequerimientoEJB detalleRequerimientoEJB;
    
    private List<Encabezadorequerimiento> listaRequerimientos;
    
    private String desrequerimiento;
    private boolean dialogModifi=false;
    private boolean dialogCrear=false;
    
    private double cantidad;
    private Usuario user;
    
    private MaterialRequerimientoUtil material;
    FacesMessage msg = null;
    /**
     * Creates a new instance of RequerimientoManagedBean
     */
    public RequerimientoManagedBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
    private DualListModel<MaterialRequerimientoUtil> listMateriales;

    public DualListModel<MaterialRequerimientoUtil> getListMateriales() {
        if(listMateriales == null || (listMateriales.getSource().isEmpty() && listMateriales.getTarget().isEmpty())){            
            List<MaterialRequerimientoUtil> source = new ArrayList<>();
            List<MaterialRequerimientoUtil> target = new ArrayList<>();      
            source = materialEJB.getMaterialesRequerimiento();
            setListMateriales(new DualListModel<MaterialRequerimientoUtil>(source,target));
        }
        return listMateriales;
    }

    public void setListMateriales(DualListModel<MaterialRequerimientoUtil> listMateriales) {        
        this.listMateriales = listMateriales;
    }

    public List<Encabezadorequerimiento> getListaRequerimientos() {
        if (listaRequerimientos==null || listaRequerimientos.isEmpty()) {
            listaRequerimientos = new ArrayList<>();
            setListaRequerimientos(encabezadoRequerimientoEJB.getRequerimientos());
        }
        return listaRequerimientos;
    }

    public MaterialRequerimientoUtil getMaterial() {
        return material;
    }

    public void setMaterial(MaterialRequerimientoUtil material) {
        this.material = material;
    }    
    
    public void setListaRequerimientos(List<Encabezadorequerimiento> listaRequerimientos) {
        this.listaRequerimientos = listaRequerimientos;
    }

    public String getDesrequerimiento() {
        return desrequerimiento;
    }

    public void setDesrequerimiento(String desrequerimiento) {
        this.desrequerimiento = desrequerimiento;
    }

    public boolean isDialogModifi() {
        return dialogModifi;
    }

    public void setDialogModifi(boolean dialogModifi) {
        this.dialogModifi = dialogModifi;
    }      

    public boolean isDialogCrear() {
        return dialogCrear;
    }

    public void setDialogCrear(boolean dialogCrear) {
        this.dialogCrear = dialogCrear;
    }
    
    public void crearRequerimiento(){
        RequestContext req = RequestContext.getCurrentInstance();
        Integer encabezado = encabezadoRequerimientoEJB.setCrearRequerimiento(getDesrequerimiento(),user);
        if(encabezado!=null){
            if(detalleRequerimientoEJB.crearDetalleRequerimiento(encabezado, getListMateriales().getTarget(), user)){
                limpiarLista();
                req.update(":form");
                req.execute("PF('dlg1').hide();");  
            }
            
        }
    } 
    
    public void modificarRequerimiento(String id){
        RequestContext req = RequestContext.getCurrentInstance();        
        setDesrequerimiento(id);
        req.execute("PF('dlg2').show();"); 
    }
    
    public void limpiarLista(){           
        listMateriales.getSource().clear();
        listMateriales.getTarget().clear();
        listaRequerimientos.clear();
        getListMateriales(); 
        getListaRequerimientos();
        setDesrequerimiento("");         
    }
    public void crearRequerimientoPanel(){
        limpiarLista();
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg1').show();"); 
    }
    
    public void onTransfer(TransferEvent event){
        for(Object item : event.getItems()) {
            material = new MaterialRequerimientoUtil();
            material.setNombre(((MaterialRequerimientoUtil) item).getNombre());
            material.setMaterialId(((MaterialRequerimientoUtil) item).getMaterialId());
            material.setReferencia(((MaterialRequerimientoUtil) item).getReferencia());
            material.setMarcaId(((MaterialRequerimientoUtil) item).getMarcaId());
            material.setCantidad("0.0");
        }   
        setCantidad(0);
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg3').show();"); 
    }

    
    public void cantidades(){
        for (MaterialRequerimientoUtil listaMate : listMateriales.getTarget()) {
            if(listaMate.getMaterialId() == null ? material.getMaterialId() == null : listaMate.getMaterialId().equals(material.getMaterialId())){
                listaMate.setCantidad(String.valueOf(cantidad));
                break;
            }
        }
        setCantidad(0);
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg3').hide();"); 
    }
}
