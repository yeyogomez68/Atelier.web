/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.utils.MaterialUtil;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author jeisson.gomez
 */
public class MaterialManagedBean {

    /**
     * Creates a new instance of MaterialManagedBean
     */
    public MaterialManagedBean() {
        materialUtil = new MaterialUtil();
    }
    
    
    @EJB
    private MaterialEJB materialEJB;
    
    private List<Material> materiales;
    
    private MaterialUtil materialUtil;
    FacesMessage msg = null;

    public List<Material> getMateriales() {
        if(materiales == null || materiales.isEmpty()){
            materiales = new ArrayList<>();
            setMateriales(materialEJB.getMateriales());
        }
        return materiales;
    }

    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }

    public MaterialUtil getMaterialUtil() {
        return materialUtil;
    }

    public void setMaterialUtil(MaterialUtil materialUtil) {
        this.materialUtil = materialUtil;
    }
    
        
    
    public void onRowEdit(RowEditEvent event) {
        
        materialUtil.setNombre(((Material) event.getObject()).getMaterialNombre());
        materialUtil.setReferencia((((Material) event.getObject()).getMaterialReference()));
        
        if(materialEJB.setModificarMaterial(materialUtil)){
            msg = new FacesMessage("Mensaje", "Se modifico el Material Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el Material");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((Material) event.getObject()).getMaterialNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearMaterial(){

    }
}
