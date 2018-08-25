/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Color;
import com.universitaria.ateliermaven.ejb.administrador.ColorEJB;
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
public class ColorManagedBean implements Serializable {

    @EJB
    private ColorEJB colorEJB;
    private List<Color> colores;
    private String colorNombre;

    public ColorEJB getColorEJB() {
        return colorEJB;
    }

    public void setColorEJB(ColorEJB colorEJB) {
        this.colorEJB = colorEJB;
    }

    public List<Color> getColores() {
        if (colores == null) {
            colores = new ArrayList<>();
            setColores(colorEJB.getColores());
        }
        return colores;
    }

    public void setColores(List<Color> colores) {
        this.colores = colores;
    }

    public String getColorNombre() {
        return colorNombre;
    }

    public void setColorNombre(String colorNombre) {
        this.colorNombre = Comunes.getFormat(colorNombre);
    }

    public void onRowEdit(RowEditEvent event) {
        Comunes.mensaje((colorEJB.setModificarColor(((Color) event.getObject()).getColorId().toString(), Comunes.getFormat(((Color) event.getObject()).getColorDescrip())) ? "Se ha modificado el color correctamente" : "Error modificando el color"), colorNombre);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Color) event.getObject()).getColorDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearColor() {
        if (!colorEJB.existeColor(colorNombre)) {
            Comunes.mensaje((colorEJB.setCrearColor(colorNombre) ? "Se ha creado el estado correctamente" : "Error creando el estado"), colorNombre);
        } else {
            Comunes.mensaje("El color ya se encuentra registrado", colorNombre);
        }
    }

}
