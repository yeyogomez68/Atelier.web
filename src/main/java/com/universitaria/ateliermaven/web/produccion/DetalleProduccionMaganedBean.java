/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Producciondeta;
import com.universitaria.atelier.web.utils.DetalleProduccionUtil;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.produccion.DetalleProduccionEJB;
import com.universitaria.ateliermaven.ejb.produccion.ProduccionEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
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
public class DetalleProduccionMaganedBean {

    public DetalleProduccionMaganedBean() {
        this.detalleProduccionCrear = new DetalleProduccionUtil();
    }

    @EJB
    private DetalleProduccionEJB detalleProduccionEJB;

    @EJB
    private MaterialEJB materialEJB;

    @EJB
    private ProduccionEJB produccionEJB;

    private List<Producciondeta> produccionDetalles;

    private List<SelectItem> producciones;
    private List<SelectItem> materiales;

    private String produccionId;
    private String materialId;

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

    public ProduccionEJB getProduccionEJB() {
        return produccionEJB;
    }

    public void setProduccionEJB(ProduccionEJB produccionEJB) {
        this.produccionEJB = produccionEJB;
    }

    public List<Producciondeta> getProduccionDetalles() {
        return produccionDetalles;
    }

    public void setProduccionDetalles(List<Producciondeta> produccionDetalles) {
        this.produccionDetalles = produccionDetalles;
    }

    public List<SelectItem> getProducciones() {
        return producciones;
    }

    public void setProducciones(List<SelectItem> producciones) {
        this.producciones = producciones;
    }

    public List<SelectItem> getMateriales() {
        return materiales;
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

        Comunes.mensaje((detalleProduccionEJB.setModificarDetalleProduccion(produccionDeta) ? "Se ha modificado el detalle de la produccion correctamente " : "Error modificando el detalle de la produccion "), "");

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearProduccion() {

        detalleProduccionCrear.setProduccionDetaCant(detalleProduccionCrear.getProduccionDetaCant());
        detalleProduccionCrear.setMaterialId(materialId);
        detalleProduccionCrear.setProduccionId(produccionId);
        Comunes.mensaje((detalleProduccionEJB.setCrearDetalleProduccion(detalleProduccionCrear) ? "Se ha creado el detalle de la produccion correctamente" : "Error creando el detalle de la la produccion"), "");

    }
}
