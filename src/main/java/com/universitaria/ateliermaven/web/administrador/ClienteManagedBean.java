/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Cliente;
import com.universitaria.atelier.web.utils.ClienteUtil;

import com.universitaria.ateliermaven.ejb.ClienteEJB;
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
 * @author JorgeWilson
 */
public class ClienteManagedBean {

    /**
     * Creates a new instance of ClienteManagedBean
     */
    public ClienteManagedBean() {
         this.clienteCrear = new ClienteUtil();
    }
    
    @EJB
    private ClienteEJB clienteEJB;
    
    @EJB
    private EstadoEJB estadoEJB;
    
    @EJB
    private CiudadEJB ciudadEJB;
    
    @EJB
    private RollEJB rollEJB;
    
    private List<Cliente> clientes;
    private List<SelectItem> estados;
    private List<SelectItem> ciudades;
    private List<SelectItem> roles;
    FacesMessage msg = null;
    private String estadoId;
    private String ciudadId;
    private String rollId;
            
    private ClienteUtil clienteCrear;
    

    public ClienteUtil getClienteCrear() {
        return clienteCrear;
    }

    public void setClienteCrear(ClienteUtil clienteCrear) {
        this.clienteCrear = clienteCrear;
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

    public List<Cliente> getClientes() {
        if(clientes == null || clientes.isEmpty()){
            clientes = new ArrayList<>();           
            setClientes(clienteEJB.getClientes());
        }    
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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
        ClienteUtil util = new ClienteUtil();
        util.setClienteId(((Cliente) event.getObject()).getClienteId());
        util.setNombre(((Cliente) event.getObject()).getClienteNombre());
        util.setApellido(((Cliente) event.getObject()).getClienteApellido());
        util.setEmail(((Cliente) event.getObject()).getClienteEmail());
        util.setCelular(((Cliente) event.getObject()).getClienteCel());
        clienteEJB.setModificarCliente(util);  
        clientes.clear();
        getClientes();
    }
     
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((Cliente) event.getObject()).getEstadoId().getEstadoDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearCliente(){
        FacesMessage msg;
        RequestContext req = RequestContext.getCurrentInstance();
        clienteCrear.setEstadoId("1");
        clienteCrear.setCiudadId(ciudadId);
        clienteCrear.setRollId(rollId);
        if(clienteEJB.setCrearCliente(clienteCrear)){
            msg = new FacesMessage("Mensaje", "Cliente Creado con exito"); 
            clientes.clear();
            getClientes();
            limpiarCapos();
            req.update(":form");
            req.execute("PF('dlg1').hide();");  
        }else{
            msg = new FacesMessage("Mensaje", "Error al crear el Cliente");            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    private void limpiarCapos(){
        clienteCrear = new ClienteUtil();
    }
}
