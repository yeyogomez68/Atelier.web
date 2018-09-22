/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
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
 * @author jeisson.gomez
 */
public class EstadoManagedBean {

    public EstadoManagedBean() {
    }
    
    @EJB
    private EstadoEJB estadoEJB;
    private List<Estado> estados;
    private String nomEstado;
    FacesMessage msg = null;
    /**
     * Creates a new instance of EstadoManagedBean
     */
    
    public String getNomEstado() {
        return nomEstado;
    }

    public void setNomEstado(String nomEstado) {
        this.nomEstado = nomEstado;
    }

    public List<Estado> getEstados() {
        if (estados == null || estados.isEmpty()) {
            estados = new ArrayList<>();
        } else {
            estados.clear();
        }
        setEstados(estadoEJB.getEstados());
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void onRowEdit(RowEditEvent event) {
         if(estadoEJB.setModificarEstado(((Estado) event.getObject()).getEstadoId().toString(), ((Estado) event.getObject()).getEstadoDescrip())){
            msg = new FacesMessage("Mensaje", "Se modifico el Estado Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el Estado");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Estado) event.getObject()).getEstadoDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearEstado() {
        RequestContext req = RequestContext.getCurrentInstance();
        if(getNomEstado()!=null){
            if(!estadoEJB.getExisteEstado(nomEstado)){
                if(estadoEJB.crearEstado(nomEstado)){
                    estados.clear();
                    getEstados();
                    setNomEstado("");
                    req.update(":form");
                    req.execute("PF('dlg1').hide();");
                   msg = new FacesMessage("Mensaje", "Estado Creado Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Estado");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Estado ya se encuentra registrado");
            }
        }else{
            msg = new FacesMessage("Error", "El nombre del Estado es obligatorio");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}