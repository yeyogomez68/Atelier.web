/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.UsuarioUtil;

import com.universitaria.ateliermaven.ejb.UsuarioEJB;
import com.universitaria.ateliermaven.ejb.administrador.CiudadEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.administrador.RollEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Jeisson Gomez
 */
public class UsuarioManagedBean implements Serializable{

    /**
     * Creates a new instance of UsuarioManagedBean
     */
    public UsuarioManagedBean() {
        this.usuarioCrear = new UsuarioUtil();
    }
    
    @EJB
    private UsuarioEJB usuarioEJB;
    
    @EJB
    private EstadoEJB estadoEJB;
    
    @EJB
    private CiudadEJB ciudadEJB;
    
    @EJB
    private RollEJB rollEJB;
    
    private List<Usuario> usuarios;
    private List<SelectItem> estados;
    private List<SelectItem> ciudades;
    private List<SelectItem> roles;
    FacesMessage msg = null;
    private String estadoId;
    private String ciudadId;
    private String rollId;
            
    private UsuarioUtil usuarioCrear;
    

    public UsuarioUtil getUsuarioCrear() {
        return usuarioCrear;
    }

    public void setUsuarioCrear(UsuarioUtil usuarioCrear) {
        this.usuarioCrear = usuarioCrear;
    }

    public List<SelectItem> getCiudades() {
        if(ciudades==null || ciudades.isEmpty()){
            ciudades = new ArrayList<>();           
        }     
        setCiudades(ciudadEJB.getSelectItemCiudad());
        return ciudades;
    }

    public void setCiudades(List<SelectItem> ciudades) {        
        this.ciudades = ciudades;
    }

    public List<SelectItem> getRoles() {
        if(roles==null || roles.isEmpty()){
            roles = new ArrayList<>();           
        }else{
            roles.clear();           
        }      
        setRoles(rollEJB.getSelectItemRoles());
        return roles;
    }

    public void setRoles(List<SelectItem> roles) {
        this.roles = roles;
    }

    public String getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getRollId() {
        return rollId;
    }

    public void setRollId(String rollId) {
        this.rollId = rollId;
    }

    public List<Usuario> getUsuarios() {
        if(usuarios==null || usuarios.isEmpty()){
            usuarios = new ArrayList<>();           
            setUsuarios(usuarioEJB.getUsuarios());
        }    
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<SelectItem> getEstados() {
        if(estados==null || estados.isEmpty()){
            estados = new ArrayList<>();   
            setEstados(estadoEJB.getSelectItemEstados());
        }
        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }   
    
        public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }
    
       
    public void onRowEdit(RowEditEvent event) {        
        UsuarioUtil util = new UsuarioUtil();
        util.setUsuarioId(((Usuario) event.getObject()).getUsuarioId());
        util.setNombre(((Usuario) event.getObject()).getUsuarioNombre());
        util.setApellido(((Usuario) event.getObject()).getUsuarioApellido());
        util.setEmail(((Usuario) event.getObject()).getUsuarioEmail());
        util.setCelular(((Usuario) event.getObject()).getUsuarioCel());
        usuarioEJB.setModificarUsuario(util);  
        usuarios.clear();
        getUsuarios();
    }
     
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((Usuario) event.getObject()).getEstadoId().getEstadoDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearUsuario(){
        FacesMessage msg;
        RequestContext req = RequestContext.getCurrentInstance();
        usuarioCrear.setEstadoId("1");
        usuarioCrear.setCiudadId(ciudadId);
        usuarioCrear.setRollId(rollId);
        if(usuarioEJB.setCrearUsuario(usuarioCrear)){
            msg = new FacesMessage("Mensaje", "Usuario Creado con exito"); 
            usuarios.clear();
            getUsuarios();
            limpiarCapos();
            req.update(":form");
            req.execute("PF('dlg1').hide();");  
        }else{
            msg = new FacesMessage("Mensaje", "Error al crear el usuario");            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    private void limpiarCapos(){
        usuarioCrear = new UsuarioUtil();
    }
}
