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
        if (paises == null) {
            paises = new ArrayList<>();
            setPaises(paisEJB.getPaises());
        }
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = Comunes.getFormat(nombrePais);
    }

    public void onRowEdit(RowEditEvent event) {
        Comunes.mensaje((paisEJB.setModificarPais(((Pais) event.getObject()).getPaisId().toString(), Comunes.getFormat(((Pais) event.getObject()).getPaisNombre())) ? "Se ha modificado el pais correctamente" : "Error modificando el pais"), nombrePais);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Pais) event.getObject()).getPaisNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearPais() {
        if (!paisEJB.existePais(nombrePais)) {
            Comunes.mensaje((paisEJB.setCrearPais(nombrePais) ? "Se ha creado el pais correctamente" : "Error creando el pais"), nombrePais);
        } else {
            Comunes.mensaje("El pais ya se encuentra registrado", nombrePais);
        }
    }
}
