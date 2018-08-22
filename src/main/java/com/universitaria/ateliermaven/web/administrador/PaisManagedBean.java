/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Pais;
import com.universitaria.ateliermaven.ejb.administrador.PaisEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
public class PaisManagedBean implements Serializable {

    @EJB
    private PaisEJB paisEJB;
    private List<Pais> paises;
    private String nombrePais;

    public PaisEJB getPaisEJB() {
        return paisEJB;
    }

    public void setPaisEJB(PaisEJB paisEJB) {
        this.paisEJB = paisEJB;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Pais) event.getObject()).getPaisNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Pais) event.getObject()).getPaisNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearPais() {
        Comunes.mensaje((paisEJB.setCrearPais(Comunes.getFormat(nombrePais)) ? "Se ha creado el estado correctamente" : "Error creando el estado"), Comunes.getFormat(nombrePais));
    }
}
