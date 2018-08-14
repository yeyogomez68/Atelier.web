/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Materialtipo;
import com.universitaria.ateliermaven.ejb.administrador.MaterialTipoEJB;
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
public class MaterialTipoManagedBean {

    /**
     * Creates a new instance of MaterialTipoManagedBean
     */
    public MaterialTipoManagedBean() {
    }
    
    
    @EJB
    private MaterialTipoEJB materialTipoEJB;
    
    private List<Materialtipo> materialestipos;
    private String materialDesc;
    private boolean modalDialog = false;
    
    FacesMessage msg = null;

    public boolean isModalDialog() {
        return modalDialog;
    }

    public void setModalDialog(boolean modalDialog) {
        this.modalDialog = modalDialog;
    }   
    
    public List<Materialtipo> getMaterialestipos() {
        if(materialestipos==null || materialestipos.isEmpty()){
            materialestipos = new ArrayList<>();
            setMaterialestipos(materialTipoEJB.getMaterialTipos());
        }
        return materialestipos;
    }

    public void setMaterialestipos(List<Materialtipo> materialestipos) {
        this.materialestipos = materialestipos;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }     
    
    public void onRowEdit(RowEditEvent event) {
        if(materialTipoEJB.setModifiDesMaterialTipo(((Materialtipo) event.getObject()).getMaterialTipoId().toString(), ((Materialtipo) event.getObject()).getMaterialTipoDescript())){
            msg = new FacesMessage("Mensaje", "Se modifico la Marca Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar la Marca");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Edición Cancelada", ((Materialtipo) event.getObject()).getMaterialTipoDescript());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearTipoMaterial(){
        if(getMaterialDesc()!=null || getMaterialDesc().isEmpty()){
            if(!materialTipoEJB.getExisteMaterialTipo(materialDesc)){
                if(materialTipoEJB.setCrearMaterialTipo(materialDesc)){
                   msg = new FacesMessage("Mensaje", "Material Tipo Creada Exitosamente");
                    setModalDialog(true);
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Tipo de Material");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Tipo de Material ya se encuentra registrado");
            }
        }else{
            msg = new FacesMessage("Error", "El nombre del Tipo de Material es obligatorio");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
