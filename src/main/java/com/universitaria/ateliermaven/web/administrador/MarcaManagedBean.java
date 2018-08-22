/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Marca;
import com.universitaria.ateliermaven.ejb.administrador.MarcaEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
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
public class MarcaManagedBean {

    public MarcaManagedBean() {
    }

    @EJB
    private MarcaEJB marcaEJB;
    private List<Marca> marca;
    private String nombreMarca;

    public MarcaEJB getMarcaEJB() {
        return marcaEJB;
    }

    public void setMarcaEJB(MarcaEJB marcaEJB) {
        this.marcaEJB = marcaEJB;
    }

    public List<Marca> getMarca() {
        if (marca == null) {
            marca = new ArrayList<>();
            setMarca(marcaEJB.getMarcas());
        }
        return marca;
    }

    public void setMarca(List<Marca> marca) {
        this.marca = marca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((Marca) event.getObject()).getMarcaNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Marca) event.getObject()).getMarcaNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearMarca() {
        Comunes.mensaje((marcaEJB.setCrearMarca(nombreMarca) ? "Se ha creado el estado correctamente" : "Error creando el estado"), nombreMarca);
    }

}
