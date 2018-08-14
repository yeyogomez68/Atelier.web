/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Talla;
import com.universitaria.ateliermaven.ejb.administrador.TallaEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author JorgeWilson
 */
public class TallaManagedBean {

    /**
     * Creates a new instance of TallaManagedBean
     */
    public TallaManagedBean() {
    }
    
    @EJB
    private TallaEJB tallaEJB;
    
    private List<Talla> tallas;
    
    private String desTalla;
    FacesMessage msg = null;

    public String getDesTalla() {
        return desTalla;
    }

    public void setDesTalla(String desTalla) {
        this.desTalla = desTalla;
    }
  
    public List<Talla> getTallas() {
        if(tallas==null || tallas.isEmpty()){
            tallas = new ArrayList<>();  
            setTallas(tallaEJB.getTallas());
        }
        return tallas;
    }

    public void setTallas(List<Talla> tallas) {
        this.tallas = tallas;
    }   
    
    public void onRowEdit(RowEditEvent event) {
        if(tallaEJB.setModifiTalla(((Talla) event.getObject()).getTallaId().toString(), ((Talla) event.getObject()).getTallaDescrip())){
            msg = new FacesMessage("Mensaje", "Se modifico la talla Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar la talla");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((Talla) event.getObject()).getTallaDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    public void crearTalla(){        
        if(getDesTalla()!=null){
            if(!tallaEJB.getExisteTalla(desTalla)){
                if(tallaEJB.setCrearTalla(desTalla)){
                   msg = new FacesMessage("Mensaje", "Talla Creada Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear la Talla");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "La Talla ya se encuentra registrada");
            }
        }else{
            msg = new FacesMessage("Error", "El nombre de la Talla es obligatorio");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
