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
import org.primefaces.context.RequestContext;
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
    FacesMessage msg = null;
    
    
    public List<Color> getColores() {
        if (colores == null || colores.isEmpty()) {
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
        this.colorNombre = colorNombre;
    }

    public void onRowEdit(RowEditEvent event) {
         if(colorEJB.setModificarColor(((Color) event.getObject()).getColorId().toString(), ((Color) event.getObject()).getColorDescrip())){
        msg = new FacesMessage("Mensaje", "Se modifico el Color Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el Color");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
               
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Color) event.getObject()).getColorDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

   public void crearColor(){  
        RequestContext req = RequestContext.getCurrentInstance();
            if(!colorEJB.getexisteColor(colorNombre)){
                if(colorEJB.setCrearColor(colorNombre)){
                    colores.clear();
                    getColores();
                    setColorNombre("");
                    req.update(":form");
                    req.execute("PF('dlg1').hide();");
                   msg = new FacesMessage("Mensaje", "Color Creado Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Color");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Color ya se encuentra registrado");
            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    

}
