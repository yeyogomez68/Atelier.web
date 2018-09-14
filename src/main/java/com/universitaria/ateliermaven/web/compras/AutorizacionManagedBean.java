/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import com.universitaria.atelier.web.jpa.Requestdeta;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.ateliermaven.ejb.compras.DetalleRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.compras.EncabezadoRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jeisson.gomez
 */
public class AutorizacionManagedBean {

    @EJB
    private EncabezadoRequerimientoEJB encabezadoRequerimientoEJB;

    @EJB
    private DetalleRequerimientoEJB detalleRequerimientoEJB;

    private Usuario user;
    private List<Encabezadorequerimiento> listaRequerimientos;
    private List<Requestdeta> listaItemRq;
    private int idReque;
    private String DetaReque;
    private boolean editable=true;

    /**
     * Creates a new instance of AutorizacionManagedBean
     */
    public AutorizacionManagedBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
    }

    public List<Encabezadorequerimiento> getListaRequerimientos() {
        if (listaRequerimientos == null || listaRequerimientos.isEmpty()) {
            listaRequerimientos = new ArrayList<>();
            setListaRequerimientos(encabezadoRequerimientoEJB.getRequerimientosByUser(user));
        }
        return listaRequerimientos;
    }

    public void setListaRequerimientos(List<Encabezadorequerimiento> listaRequerimientos) {
        this.listaRequerimientos = listaRequerimientos;
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
        return DetaReque;
    }

    public void setDetaReque(String DetaReque) {
        this.DetaReque = DetaReque;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public void limpiarDatos() {
        listaRequerimientos.clear();
        listaItemRq.clear();
        DetaReque = "";
        idReque = 0;
    }
    
    public void limpiarListas(){
        listaRequerimientos.clear();
        listaItemRq.clear();
    }

    public void verRequerimiento(Encabezadorequerimiento requi) {
        RequestContext req = RequestContext.getCurrentInstance();
        if(requi.getEstadoId().getEstadoId()==EstadoEnum.APROBADO.getId() || 
                requi.getEstadoId().getEstadoId()==EstadoEnum.CONDICIONADO.getId() ||
                requi.getEstadoId().getEstadoId()==EstadoEnum.RECHAZADO.getId()){
            setEditable(false);
        }else{
            setEditable(true);
        }
        this.idReque = requi.getEncabezadoRequerimientoId();
        this.DetaReque = requi.getEncabezadoRequerimientoDeta(); 
        llenarDetaRq();
        req.execute("PF('dlg1').show();");
    }
    
    public void llenarDetaRq(){
        listaItemRq = new ArrayList<>();
        setListaItemRq(detalleRequerimientoEJB.obtenerDetalleByIdRq(idReque));
    }

    public void aprobarItemRequerimiento(Requestdeta rqDeta) {
        FacesMessage msg;
        if (detalleRequerimientoEJB.aprobarItem(rqDeta)) {
            msg = new FacesMessage("Aprobado", "Se ha aprobado correctamente el item");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "Ha ocurrido un error al momento de aprobar el item");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        limpiarListas();
        llenarDetaRq();
        validarPendientes();
    }

    public void rechazarItemRequerimiento(Requestdeta rqDeta) {
        FacesMessage msg;
        if (detalleRequerimientoEJB.rechazarItem(rqDeta)) {
            msg = new FacesMessage("Rechazado", "Se ha rechazado correctamente el item");
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        } else {
            msg = new FacesMessage("Error", "Ha ocurrido un error al momento de rechazar el item");
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        }
        limpiarListas();        
        llenarDetaRq();
        validarPendientes();
    }
    
    private void validarPendientes(){
        RequestContext req = RequestContext.getCurrentInstance();
        int pe=0;
        for (Requestdeta requestdeta : listaItemRq) {
            if (requestdeta.getEstadoId().getEstadoId()==EstadoEnum.PENDIENTE.getId()){
                pe++;
            }            
        }
        if(pe==0){
            req.execute("PF('dlg1').hide();");
            setEditable(false);            
        }        
    }

    public void aprobarRequerimiento() {
        RequestContext req = RequestContext.getCurrentInstance();
        for (Requestdeta requestdeta : listaItemRq) {
            if(requestdeta.getEstadoId().getEstadoId()==EstadoEnum.PENDIENTE.getId()){
                detalleRequerimientoEJB.aprobarItem(requestdeta);
            }
        }
        limpiarListas();
        req.execute("PF('dlg1').hide();");
    }

    public void rechazarRequerimiento() {
        RequestContext req = RequestContext.getCurrentInstance();
        for (Requestdeta requestdeta : listaItemRq) {
            if(requestdeta.getEstadoId().getEstadoId()==EstadoEnum.PENDIENTE.getId()){
                detalleRequerimientoEJB.rechazarItem(requestdeta);
            }
        }
        limpiarListas();
        req.execute("PF('dlg1').hide();");
    }
}
