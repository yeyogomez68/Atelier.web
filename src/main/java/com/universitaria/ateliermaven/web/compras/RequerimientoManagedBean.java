/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import com.universitaria.atelier.web.jpa.Requestdeta;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.ateliermaven.ejb.compras.DetalleRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.compras.EncabezadoRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
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
    
    private DualListModel<MaterialRequerimientoUtil> listMateriales;
    private DualListModel<MaterialRequerimientoUtil> listMaterialesRq;
    private List<Requestdeta> listaItemRq;
    
    private int idReque;
    private String detaReque;    
    
    private String idRequerimientoModifica;
    private String desrequerimiento;
    private String estado;
    private boolean dialogModifi=false;
    private boolean dialogCrear=false;
    
    private double cantidad;
    private Usuario user;
    private int cantTarget=0;
    
    private MaterialRequerimientoUtil material;
    FacesMessage msg = null;
    /**
     * Creates a new instance of RequerimientoManagedBean
     */
    public RequerimientoManagedBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdRequerimientoModifica() {
        return idRequerimientoModifica;
    }

    public void setIdRequerimientoModifica(String idRequerimientoModifica) {
        this.idRequerimientoModifica = idRequerimientoModifica;
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
    
    public List<Requestdeta> getListaItemRq() {
        if (listaItemRq == null || listaItemRq.isEmpty()) {
            listaItemRq = new ArrayList<>();
        }
        return listaItemRq;
    }

    public void setListaItemRq(List<Requestdeta> listaItemRq) {
        this.listaItemRq = listaItemRq;
    }

    public int getIdReque() {
        return idReque;
    }

    public void setIdReque(int idReque) {
        this.idReque = idReque;
    }

    public String getDetaReque() {
        return detaReque;
    }

    public void setDetaReque(String detaReque) {
        this.detaReque = detaReque;
    }
   
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

    public DualListModel<MaterialRequerimientoUtil> getListMaterialesRq() {
        if (listMaterialesRq==null || (listMaterialesRq.getSource().isEmpty() && listMaterialesRq.getTarget().isEmpty())) {
            listMaterialesRq = new DualListModel<>(); 
        }
        return listMaterialesRq;
    }

    public void setListMaterialesRq(DualListModel<MaterialRequerimientoUtil> listMaterialesRq) {
        this.listMaterialesRq = listMaterialesRq;
    }

    public List<Encabezadorequerimiento> getListaRequerimientos() {
        if (listaRequerimientos==null || listaRequerimientos.isEmpty()) {
            listaRequerimientos = new ArrayList<>();
            setListaRequerimientos(encabezadoRequerimientoEJB.getRequerimientosByUser(user));
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
    
    public void modificarRequerimiento(Encabezadorequerimiento requeri){
        RequestContext req = RequestContext.getCurrentInstance();        
        setIdRequerimientoModifica(String.valueOf(requeri.getEncabezadoRequerimientoId()));
        listMaterialesRq = new DualListModel<>();   
        this.idReque = requeri.getEncabezadoRequerimientoId();
        this.detaReque = requeri.getEncabezadoRequerimientoDeta(); 
        this.estado = requeri.getEstadoId().getEstadoDescrip();
        if(listMaterialesRq == null || (listMaterialesRq.getSource().isEmpty() && listMaterialesRq.getTarget().isEmpty())){            
            List<MaterialRequerimientoUtil> source = new ArrayList<>();
            List<MaterialRequerimientoUtil> target = new ArrayList<>();      
            target = detalleRequerimientoEJB.obtenerDetalleRq(idRequerimientoModifica);
            source = materialEJB.getMaterialesRequerimientoCross(target);            
            setListMaterialesRq(new DualListModel<MaterialRequerimientoUtil>(source,target));
        }
        
        req.execute("PF('dlg2').show();"); 
    }
    
    public void limpiarLista(){           
        listMateriales.getSource().clear();
        listMateriales.getTarget().clear();
        listMaterialesRq.getSource().clear();
        listMaterialesRq.getTarget().clear();
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
        for (MaterialRequerimientoUtil listaMate : listMateriales.getTarget()) {
            if(listaMate.getMaterialId() == null ? material.getMaterialId() == null : listaMate.getMaterialId().equals(material.getMaterialId())){                
                setCantidad(0);
                RequestContext req = RequestContext.getCurrentInstance();
                req.execute("PF('dlg3').show();"); 
                break;
            }
        }        
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
    
    
    public void onTransferEdit(TransferEvent event){
        for(Object item : event.getItems()) {
            material = new MaterialRequerimientoUtil();
            material.setNombre(((MaterialRequerimientoUtil) item).getNombre());
            material.setMaterialId(((MaterialRequerimientoUtil) item).getMaterialId());
            material.setReferencia(((MaterialRequerimientoUtil) item).getReferencia());
            material.setMarcaId(((MaterialRequerimientoUtil) item).getMarcaId());
            material.setCantidad("0.0");
        }        
        for (MaterialRequerimientoUtil listaMate : listMaterialesRq.getTarget()) {
            if(listaMate.getMaterialId() == null ? material.getMaterialId() == null : listaMate.getMaterialId().equals(material.getMaterialId())){                
                setCantidad(0);
                RequestContext req = RequestContext.getCurrentInstance();
                req.execute("PF('dlg4').show();"); 
                break;
            }
        }        
    }

    
    public void cantidadesEdit(){
        for (MaterialRequerimientoUtil listaMate : listMaterialesRq.getTarget()) {
            if(listaMate.getMaterialId() == null ? material.getMaterialId() == null : listaMate.getMaterialId().equals(material.getMaterialId())){
                listaMate.setCantidad(String.valueOf(cantidad));
                break;
            }
        }
        setCantidad(0);
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg4').hide();"); 
    }
    
    public void actualizarRequerimiento(){
        RequestContext req = RequestContext.getCurrentInstance();
        if(detalleRequerimientoEJB.modificarDetalleRequerimiento(getIdRequerimientoModifica(),getListMaterialesRq().getTarget(), user)){
                limpiarLista();
                req.update(":form");
                req.execute("PF('dlg2').hide();");  
            }
    }
    
    public void verRequerimiento(Encabezadorequerimiento requi){
        RequestContext req = RequestContext.getCurrentInstance();        
        this.idReque = requi.getEncabezadoRequerimientoId();
        this.detaReque = requi.getEncabezadoRequerimientoDeta(); 
        this.estado = requi.getEstadoId().getEstadoDescrip();
        llenarDetaRq();
        req.execute("PF('dlg5').show();");
    }
    
    public void llenarDetaRq(){
        listaItemRq = new ArrayList<>();
        setListaItemRq(detalleRequerimientoEJB.obtenerDetalleByIdRq(idReque));
    }
    
}