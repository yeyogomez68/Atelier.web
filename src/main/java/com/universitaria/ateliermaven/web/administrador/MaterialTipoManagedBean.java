/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Materialtipo;
import com.universitaria.ateliermaven.ejb.administrador.MaterialTipoEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class MaterialTipoManagedBean implements Serializable {

    @EJB
    private MaterialTipoEJB materialTipoEJB;

    private List<Materialtipo> materialTipo;

    private String materialTipoDescripcion;

    public MaterialTipoManagedBean() {
    }

    public List<Materialtipo> getMaterialTipo() {

        if (materialTipo == null) {
            materialTipo = new ArrayList<>();
            setMaterialTipo(materialTipoEJB.getMaterialTipos());
        }
        return materialTipo;
    }

    public void setMaterialTipo(List<Materialtipo> materialTipo) {
        this.materialTipo = materialTipo;
    }

    public String getMaterialTipoDescripcion() {
        return materialTipoDescripcion;
    }

    public void setMaterialTipoDescripcion(String materialTipoDescripcion) {
        this.materialTipoDescripcion = materialTipoDescripcion;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Materialtipo) event.getObject()).getMaterialTipoDescript());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Materialtipo) event.getObject()).getMaterialTipoDescript());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearMaterialTIpo() {
        Comunes.mensaje((materialTipoEJB.setCrearMaterialTipo(materialTipoDescripcion) ? "Se ha creado el estado correctamente" : "Error creando el estado"), materialTipoDescripcion);
    }

}
