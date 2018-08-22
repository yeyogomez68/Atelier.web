/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.session;

import com.universitaria.atelier.web.jpa.Usuario;
import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author jeisson.gomez
 */
public class TemplateControllerManagedBean implements Serializable{
    private String nombre;
    private Usuario user;
    /**
     * Creates a new instance of TemplateControllerManagedBean
     */
    public TemplateControllerManagedBean() {
        
    }

    public String getNombre() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
        setNombre(user.getUsuarioNombre() + " " +user.getUsuarioApellido());
        return nombre;
    }

    public void setNombre(String nombre) {
        
        this.nombre = nombre;
    }

    public Usuario getUser() {
        
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    
    
    public void validateSession(){        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
        try {                        
            if (user==null) {
                facesContext.getExternalContext().redirect("../../../Login.xhtml");
            }
        } catch (Exception e) {
            System.out.println("com.universitaria.ateliermaven.web.session.TemplateControllerManagedBean.validateSession() Permisos insuficientes");
        }
    }
    
    public void cerrarSession(){
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().invalidateSession();
            facesContext.getExternalContext().redirect("../../../Login.xhtml");
        } catch (Exception e) {
        }
        
    }
    
}
