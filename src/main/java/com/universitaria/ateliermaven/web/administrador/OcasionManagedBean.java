/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Ocasion;
import com.universitaria.ateliermaven.ejb.administrador.OcasionEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
public class OcasionManagedBean implements Serializable {

    @EJB
    private OcasionEJB ocasionEJB;
    private List<Ocasion> ocasiones;
    private String ocasionNombre;

    public OcasionEJB getOcasionEJB() {
        return ocasionEJB;
    }

    public void setOcasionEJB(OcasionEJB ocasionEJB) {
        this.ocasionEJB = ocasionEJB;
    }

    public List<Ocasion> getOcasiones() {
        if (ocasiones == null) {
            ocasiones = new ArrayList<>();
            setOcasiones(ocasionEJB.getOcasiones());
        }
        return ocasiones;
    }

    public void setOcasiones(List<Ocasion> ocasiones) {
        this.ocasiones = ocasiones;
    }

    public String getOcasionNombre() {
        return ocasionNombre;
    }

    public void setOcasionNombre(String ocasionNombre) {
        this.ocasionNombre = ocasionNombre;
    }

    
       public void onRowEdit(RowEditEvent event) {
           FacesMessage msg = new FacesMessage("Car Edited", ((Ocasion) event.getObject()).getOcasionDescrip());
           FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Ocasion) event.getObject()).getOcasionDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearOcasion( ) {
        Comunes.mensaje((ocasionEJB.setCrearOcasion(ocasionNombre)? "Se ha creado el estado correctamente":"Error creando el estado" ), ocasionNombre);
    }
    
}
