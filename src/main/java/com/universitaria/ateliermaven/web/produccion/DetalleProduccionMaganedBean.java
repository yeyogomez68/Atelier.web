/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Produccion;
import com.universitaria.atelier.web.jpa.Producciondeta;
import com.universitaria.atelier.web.utils.DetalleProduccionUtil;
import com.universitaria.ateliermaven.ejb.UsuarioEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.constantes.EstadoEnum;
import com.universitaria.ateliermaven.ejb.produccion.DetalleProduccionEJB;
import com.universitaria.ateliermaven.ejb.produccion.ProduccionEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
public class DetalleProduccionMaganedBean implements Serializable {

    public DetalleProduccionMaganedBean() {
        this.detalleProduccionCrear = new DetalleProduccionUtil();
    }

    @EJB
    private DetalleProduccionEJB detalleProduccionEJB;

    @EJB
    private MaterialEJB materialEJB;

    @EJB
    private ProduccionEJB produccionEJB;

    @EJB
    private EstadoEJB estadoEJB;
    @EJB
    private UsuarioEJB usuarioEJB;

    private List<SelectItem> estados;
    private List<SelectItem> usuarios;
    private List<Producciondeta> produccionDetalles;

    private List<SelectItem> producciones;
    private List<SelectItem> materiales;

    private String produccionId;
    private String materialId;
    private String estadoId;
    private String usuarioId;

    private DetalleProduccionUtil detalleProduccionCrear;

    public DetalleProduccionEJB getDetalleProduccionEJB() {
        return detalleProduccionEJB;
    }

    public void setDetalleProduccionEJB(DetalleProduccionEJB detalleProduccionEJB) {
        this.detalleProduccionEJB = detalleProduccionEJB;
    }

    public MaterialEJB getMaterialEJB() {
        return materialEJB;
    }

    public void setMaterialEJB(MaterialEJB materialEJB) {
        this.materialEJB = materialEJB;
    }

    public UsuarioEJB getUsuarioEJB() {
        return usuarioEJB;
    }

    public void setUsuarioEJB(UsuarioEJB usuarioEJB) {
        this.usuarioEJB = usuarioEJB;
    }

    public EstadoEJB getEstadoEJB() {
        return estadoEJB;
    }

    public void setEstadoEJB(EstadoEJB estadoEJB) {
        this.estadoEJB = estadoEJB;
    }

    public ProduccionEJB getProduccionEJB() {
        return produccionEJB;
    }

    public void setProduccionEJB(ProduccionEJB produccionEJB) {
        this.produccionEJB = produccionEJB;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
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

    public List<Producciondeta> getProduccionDetalles() {
        if (produccionDetalles == null) {

            produccionDetalles = new ArrayList<>();
            setProduccionDetalles(detalleProduccionEJB.getDetalleProduccion());
        }
        return produccionDetalles;
    }

    public void setProduccionDetalles(List<Producciondeta> produccionDetalles) {
        this.produccionDetalles = produccionDetalles;
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

    public List<SelectItem> getProducciones() {
        if (producciones == null) {
            producciones = new ArrayList<>();
            setProducciones(produccionEJB.getSelectItemProduccion());
        }
        return producciones;
    }

    public void setProducciones(List<SelectItem> producciones) {
        this.producciones = producciones;
    }

    public List<SelectItem> getMateriales() {
        if (materiales == null) {
            materiales = new ArrayList<>();
            setMateriales(materialEJB.getSelectItemMaterial());
        }
        return materiales;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setMateriales(List<SelectItem> materiales) {
        this.materiales = materiales;
    }

    public String getProduccionId() {
        return produccionId;
    }

    public void setProduccionId(String produccionId) {
        this.produccionId = produccionId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public DetalleProduccionUtil getDetalleProduccionCrear() {
        return detalleProduccionCrear;
    }

    public void setDetalleProduccionCrear(DetalleProduccionUtil detalleProduccionCrear) {
        this.detalleProduccionCrear = detalleProduccionCrear;
    }

    public void onRowEdit(RowEditEvent event) {
        Producciondeta produccionDeta = (Producciondeta) event.getObject();

        Comunes.mensaje((detalleProduccionEJB.setModificarDetalleProduccion(produccionDeta, usuarioId, estadoId) ? "Se ha modificado el detalle de la produccion correctamente " : "Error modificando el detalle de la produccion "), "");

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearProduccionDetalle() {

        detalleProduccionCrear.setProduccionDetaCant(detalleProduccionCrear.getProduccionDetaCant());
        detalleProduccionCrear.setMaterialId(materialId);
        detalleProduccionCrear.setProduccionId(produccionId);
        detalleProduccionCrear.setEstadoId("1");
        detalleProduccionCrear.setUsuarioId(usuarioId);
        Comunes.mensaje((detalleProduccionEJB.setCrearDetalleProduccion(detalleProduccionCrear) ? "Se ha creado el detalle de la produccion correctamente" : "Error creando el detalle de la la produccion"), "");

    }
}
