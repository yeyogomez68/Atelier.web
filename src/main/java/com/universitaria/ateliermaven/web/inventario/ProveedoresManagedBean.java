/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.inventario;

import com.universitaria.ateliermaven.ejb.inventario.ProveedorEJB;
import com.universitaria.atelier.web.utils.ProveedorUtil;
import com.universitaria.ateliermaven.ejb.administrador.CiudadEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.atelier.web.jpa.Proveedor;
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
 * @author SoulHunter
 */
public class ProveedoresManagedBean implements Serializable {

    public ProveedoresManagedBean() {
        this.proveedorCrear = new ProveedorUtil();
    }

    @EJB
    private ProveedorEJB proveedorEJB;

    @EJB
    private CiudadEJB ciudadEJB;

    @EJB
    private EstadoEJB estadoEJB;

    private List<Proveedor> proveedores;
    private List<SelectItem> estados;
    private List<SelectItem> ciudades;

    private String estadoId;
    private String ciudadId;

    private ProveedorUtil proveedorCrear;

    public ProveedorEJB getProveedorEJB() {
        return proveedorEJB;
    }

    public void setProveedorEJB(ProveedorEJB proveedorEJB) {
        this.proveedorEJB = proveedorEJB;
    }

    public CiudadEJB getCiudadEJB() {
        return ciudadEJB;
    }

    public void setCiudadEJB(CiudadEJB ciudadEJB) {
        this.ciudadEJB = ciudadEJB;
    }

    public EstadoEJB getEstadoEJB() {
        return estadoEJB;
    }

    public void setEstadoEJB(EstadoEJB estadoEJB) {
        this.estadoEJB = estadoEJB;
    }

    public List<Proveedor> getProvedor() {
        if (proveedores == null) {
            proveedores = new ArrayList<>();
            setProvedor(proveedorEJB.getProveedores());
        }

        return proveedores;
    }

    public void setProvedor(List<Proveedor> provedor) {
        this.proveedores = provedor;
    }

    public List<SelectItem> getEstados() {
        if (estados == null || estados.isEmpty()) {
            estados = new ArrayList<>();
            setEstados(estadoEJB.getSelectItemEstados());
        }

        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }

    public List<SelectItem> getCiudades() {
        if (ciudades == null || ciudades.isEmpty()) {
            ciudades = new ArrayList<>();
            setCiudades(ciudadEJB.getSelectItemCiudad());
        }

        return ciudades;
    }

    public void setCiudades(List<SelectItem> ciudades) {
        this.ciudades = ciudades;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public ProveedorUtil getProveedorCrear() {
        return proveedorCrear;
    }

    public void setProveedorCrear(ProveedorUtil proveedorCrear) {
        this.proveedorCrear = proveedorCrear;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edited", ((Proveedor) event.getObject()).getEstadoId().getEstadoDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled", ((Proveedor) event.getObject()).getEstadoId().getEstadoDescrip());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearProveedor() {
        FacesMessage msg;
        RequestContext req = RequestContext.getCurrentInstance();
        proveedorCrear.setEstadoId("1");
        proveedorCrear.setCiudadId(ciudadId);
        msg = new FacesMessage("Mensaje", (proveedorEJB.setCrearProveedor(proveedorCrear) ? "Proveedor Creado con exito" : "Error al crear el proveedor"));
        proveedores.clear();
        setProvedor(proveedorEJB.getProveedores());
        proveedorCrear = new ProveedorUtil();
        req.update(":form");
        req.execute("PF('dlg1').hide();");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
