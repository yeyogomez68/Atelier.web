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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
public class OcasionManagedBean { 
    
 public OcasionManagedBean() {
    }
 
    @EJB
    private OcasionEJB ocasionEJB;
    private List<Ocasion> ocasiones;
    private String ocasionNombre;
    FacesMessage msg = null;
    
    
    
    public OcasionEJB getOcasionEJB() {
        return ocasionEJB;
    }

    public void setOcasionEJB(OcasionEJB ocasionEJB) {
        this.ocasionEJB = ocasionEJB;
    }

    public List<Ocasion> getOcasiones() {
        if (ocasiones == null|| ocasiones.isEmpty()) {
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
        this.ocasionNombre = Comunes.getFormat(ocasionNombre);
    }

        
    public void onRowEdit(RowEditEvent event) {
        if(ocasionEJB.setModificarOcasion(((Ocasion) event.getObject()).getOcasionId().toString(), ((Ocasion) event.getObject()).getOcasionDescrip())){
            msg = new FacesMessage("Mensaje", "Se modifico la Ocasion Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar la Ocasion");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Ocasion) event.getObject()).getOcasionDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

       public void crearOcasion(){  
        RequestContext req = RequestContext.getCurrentInstance();
           if(!ocasionEJB.getexisteOcasion(ocasionNombre)){
                if(ocasionEJB.setCrearOcasion(ocasionNombre)){
                    ocasiones.clear();
                    getOcasiones();
                    setOcasionNombre("");
                    req.update(":form");
                    req.execute("PF('dlg1').hide();");
                   msg = new FacesMessage("Mensaje", "Cargo Creado Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Cargo");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Cargo ya se encuentra registrado");
            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
}
