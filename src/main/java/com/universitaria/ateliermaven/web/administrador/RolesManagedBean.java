/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Roll;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.administrador.RollEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author jeisson.gomez
 */
public class RolesManagedBean {

    @EJB
    private RollEJB rollEJB;
    
    @EJB
    private EstadoEJB estadoEJB;
    
    private List<Roll> roles;    
    private List<SelectItem> estados;
    
    private String estadoId;
    private String rollDesc;
    
    public RolesManagedBean() {
        
    }

    public String getRollDesc() {
        return rollDesc;
    }

    public void setRollDesc(String rollDesc) {
        this.rollDesc = rollDesc;
    }    
    
    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public List<Roll> getRoles() {
        if(roles==null || roles.isEmpty()){
            roles = new ArrayList<>();           
        }else{
            roles.clear();           
        }      
        setRoles(rollEJB.getRoles());
        return roles;
    }

    public void setRoles(List<Roll> roles) {
        this.roles = roles;
    }

    public List<SelectItem> getEstados() {
        if(estados==null || estados.isEmpty()){
            estados = new ArrayList<>();           
        }else{
            estados.clear();           
        }      
        setEstados(estadoEJB.getSelectItemEstados());
        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }   
    
    public void onRowEdit(RowEditEvent event) {
        Integer rollId = ((Roll) event.getObject()).getRollId();
        System.out.println("com.universitaria.ateliermaven.web.administrador.RolesManagedBean.onRowEdit()" + rollId);
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelado", ((Roll) event.getObject()).getRollDesc());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearRoll(){
        FacesMessage msg;
        
        if(rollEJB.setCrearRoll(rollDesc)){
            msg = new FacesMessage("Mensaje", "Roll Creado con exito"); 
        }else{
            msg = new FacesMessage("Mensaje", "Error al crear el Roll");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);       
    }
    
}
