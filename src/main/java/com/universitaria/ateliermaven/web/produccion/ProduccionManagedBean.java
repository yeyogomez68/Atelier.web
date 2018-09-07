/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.ProduccionUtil;
import com.universitaria.ateliermaven.ejb.UsuarioEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.produccion.PrendasEJB;

import com.universitaria.ateliermaven.ejb.produccion.ProduccionEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
public class ProduccionManagedBean implements Serializable {

    public ProduccionManagedBean() {
        produccionCrear = new ProduccionUtil();
    }

    @EJB
    private ProduccionEJB produccionEJB;
    @EJB
    private PrendasEJB prendaEJB;
    @EJB
    private EstadoEJB estadoEJB;
    @EJB
    private UsuarioEJB usuarioEJB;

    private List<Produccion> producciones;

    private List<SelectItem> prendas;
    private List<SelectItem> estados;
    private List<SelectItem> usuarios;

    private String estadoId;
    private String usuarioId;
    private String prendaId;

    private ProduccionUtil produccionCrear;

    public ProduccionEJB getProduccionEJB() {
        return produccionEJB;
    }

    public void setProduccionEJB(ProduccionEJB produccionEJB) {
        this.produccionEJB = produccionEJB;
    }

    public PrendasEJB getPrendaEJB() {
        return prendaEJB;
    }

    public void setPrendaEJB(PrendasEJB prendaEJB) {
        this.prendaEJB = prendaEJB;
    }

    public EstadoEJB getEstadoEJB() {
        return estadoEJB;
    }

    public void setEstadoEJB(EstadoEJB estadoEJB) {
        this.estadoEJB = estadoEJB;
    }

    public UsuarioEJB getUsuarioEJB() {
        return usuarioEJB;
    }

    public void setUsuarioEJB(UsuarioEJB usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public List<Produccion> getProducciones() {
        if (producciones == null) {
            producciones = new ArrayList<>();
            setProducciones(produccionEJB.getProduccion());
        }
        return producciones;
    }

    public void setProducciones(List<Produccion> producciones) {
        this.producciones = producciones;
    }

    public List<SelectItem> getPrendas() {
        if (prendas == null) {
            prendas = new ArrayList<>();
            setPrendas(prendaEJB.getSelectItemPrenda());
        }
        return prendas;
    }

    public void setPrendas(List<SelectItem> prendas) {
        this.prendas = prendas;
    }

    public List<SelectItem> getEstados() {
        if (estados == null) {
            estados = new ArrayList<>();
            setEstados(estadoEJB.getSelectItemEstados());
        }
        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }

    public List<SelectItem> getUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            setUsuarios(usuarioEJB.getSelectItemUsuarios());
        }
        return usuarios;
    }

    public void setUsuarios(List<SelectItem> usuarios) {
        this.usuarios = usuarios;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPrendaId() {
        return prendaId;
    }

    public void setPrendaId(String prendaId) {
        this.prendaId = prendaId;
    }

    public ProduccionUtil getProduccionCrear() {
        return produccionCrear;
    }

    public void setProduccionCrear(ProduccionUtil produccionCrear) {
        this.produccionCrear = produccionCrear;
    }

    public void onRowEdit(RowEditEvent event) {
        Produccion produccion = (Produccion) event.getObject();

        if (!produccion.getProduccionDescripcion().equals("") && !prendaEJB.existePrenda(Comunes.getFormat(produccion.getProduccionDescripcion()))) {
            Comunes.mensaje((produccionEJB.setModificarProduccion(produccion) ? "Se ha modificado la produccion correctamente " : "Error modificando la produccion "), produccionCrear.getProduccionDescripcion());
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Produccion) event.getObject()).getProduccionDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearProduccion() {

        if (!produccionEJB.existeProduccion(Comunes.getFormat(produccionCrear.getProduccionDescripcion()))) {

            produccionCrear.setProduccionDescripcion(Comunes.getFormat(produccionCrear.getProduccionDescripcion()));
            produccionCrear.setEstadoId("1");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
            produccionCrear.setUsuarioId(user.getUsuarioId().toString());
            produccionCrear.setUsuarioCreador(user.getUsuarioId().toString());
            produccionCrear.setPrendaId(prendaId);
            Comunes.mensaje((produccionEJB.setCrearProduccion(produccionCrear) ? "Se ha creado la produccion correctamente" : "Error creando la produccion"), produccionCrear.getProduccionDescripcion());
        } else {
            Comunes.mensaje("La produccion ya se encuentra registrada", produccionCrear.getProduccionDescripcion());
        }
    }

}
