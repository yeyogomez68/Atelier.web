/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Encabezadorequerimiento;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.ateliermaven.ejb.compras.EncabezadoRequerimientoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
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
    
    private List<Encabezadorequerimiento> listaRequerimientos;
    
    private String desrequerimiento;
    private boolean dialogModifi=false;
    private boolean dialogCrear=false;
    FacesMessage msg = null;
    /**
     * Creates a new instance of RequerimientoManagedBean
     */
    public RequerimientoManagedBean() {
    }
    
    private DualListModel<Material> listMateriales;

    public DualListModel<Material> getListMateriales() {
        if(listMateriales == null || listMateriales.getSource().isEmpty()){            
            List<Material> source = new ArrayList<>();
            List<Material> target = new ArrayList<>();      
            source = materialEJB.getMateriales();
            setListMateriales(new DualListModel<Material>(source,target));
        }
        return listMateriales;
    }

    public void setListMateriales(DualListModel<Material> listMateriales) {        
        this.listMateriales = listMateriales;
    }

    public List<Encabezadorequerimiento> getListaRequerimientos() {
        if (listaRequerimientos==null || listaRequerimientos.isEmpty()) {
            listaRequerimientos = new ArrayList<>();
            setListaRequerimientos(encabezadoRequerimientoEJB.getRequerimientos());
        }
        return listaRequerimientos;
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
    
    public void onRowEdit(RowEditEvent event) {
        Integer idRq = ((Encabezadorequerimiento) event.getObject()).getEncabezadoRequerimientoId();
        setDialogModifi(true);
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {       
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edición Cancelada", ((Encabezadorequerimiento) event.getObject()).getEncabezadoRequerimientoId().toString()));
    }
    
    public void crearRequerimiento(){
        RequestContext req = RequestContext.getCurrentInstance();
        if(true){
            setListMateriales(null);
            getListMateriales(); 
            req.execute("PF('dlg1').hide();");  
        }else{
            req.execute("PF('dlg1').show();");
        }
        
        
    }
    
    public void mostrarDialogo(){        
        setDesrequerimiento("");
        setListMateriales(null);
        getListMateriales(); 
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg1').hide();");        
    }
}
