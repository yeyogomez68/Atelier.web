/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Pais;
import com.universitaria.ateliermaven.ejb.administrador.PaisEJB;
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
public class PaisManagedBean {

     public PaisManagedBean() {
    }
    @EJB
    private PaisEJB paisEJB;
    private List<Pais> paises;
    private String nombrePais;
    FacesMessage msg = null;

    public PaisEJB getPaisEJB() {
        return paisEJB;
    }

    public void setPaisEJB(PaisEJB paisEJB) {
        this.paisEJB = paisEJB;
    }

    public List<Pais> getPaises() {
        if (paises == null || paises.isEmpty()) {
            paises = new ArrayList<>();
            setPaises(paisEJB.getPaises());
        }
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public void onRowEdit(RowEditEvent event) {
    if(paisEJB.setModificarPais(((Pais) event.getObject()).getPaisId().toString(), ((Pais) event.getObject()).getPaisNombre())){
            msg = new FacesMessage("Mensaje", "Se modifico el Pais Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el pais");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Pais) event.getObject()).getPaisNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearPais() {
      RequestContext req = RequestContext.getCurrentInstance();
        if(getNombrePais()!=null){
            if(!paisEJB.getexistePais(nombrePais)){
                if(paisEJB.setCrearPais(nombrePais)){
                    paises.clear();
                    getPaises();
                    setNombrePais("");
                    req.update(":form");
                    req.execute("PF('dlg1').hide();");
                   msg = new FacesMessage("Mensaje", "Pais Creado Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Pais");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Pais ya se encuentra registrado");
            }
        }else{
            msg = new FacesMessage("Error", "El nombre del Pais es obligatorio");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
