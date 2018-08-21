/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.session;

import com.universitaria.atelier.web.jpa.Usuario;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 *
 * @author jeisson.gomez
 */
public class TemplateControllerManagedBean {
    private String nombre;
    private Usuario user;
    FacesContext facesContext;
    /**
     * Creates a new instance of TemplateControllerManagedBean
     */
    public TemplateControllerManagedBean() {
        
    }
    @PostConstruct
    public void init(){
        facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
        nombre = user.getUsuarioNombre() + " " +user.getUsuarioApellido();
    }

    public String getNombre() {
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
        try {                        
            if (user==null) {
                facesContext.getExternalContext().redirect("../../../Login.xhtml");
            }
        } catch (Exception e) {
            System.out.println("com.universitaria.ateliermaven.web.session.TemplateControllerManagedBean.validateSession() Permisos insuficientes");
        }
    }
    
    public String cerrarSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "ok";
    }
    
}
