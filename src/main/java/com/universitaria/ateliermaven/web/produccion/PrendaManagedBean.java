/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.utils.PrendaUtil;
import com.universitaria.ateliermaven.ejb.administrador.ColorEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.administrador.OcasionEJB;
import com.universitaria.ateliermaven.ejb.produccion.PrendaTipoEJB;
import com.universitaria.ateliermaven.ejb.produccion.PrendasEJB;
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
 * @author Jeisson Gomez
 */
public class PrendaManagedBean implements Serializable {

    public PrendaManagedBean() {
        this.prendaCrear = new PrendaUtil();
    }

    @EJB
    private PrendasEJB prendaEJB;

    @EJB
    private PrendaTipoEJB prendaTipoEJB;

    @EJB
    private MaterialEJB materialEJB;

    @EJB
    private ColorEJB colorEJB;

    @EJB
    private OcasionEJB ocasionEJB;

    @EJB
    private EstadoEJB estadoEJB;

    private List<Prenda> prendas;

    private List<SelectItem> prendasTipo;
    private List<SelectItem> materiales;
    private List<SelectItem> colores;
    private List<SelectItem> ocasiones;
    private List<SelectItem> estados;

    private PrendaUtil prendaCrear;

    private String prendaTipoId;
    private String materialId;
    private String colorId;
    private String ocasionId;
    private String estadoId;

    public PrendasEJB getPrendaEJB() {
        return prendaEJB;
    }

    public void setPrendaEJB(PrendasEJB prendaEJB) {
        this.prendaEJB = prendaEJB;
    }

    public PrendaTipoEJB getPrendaTipoEJB() {
        return prendaTipoEJB;
    }

    public void setPrendaTipoEJB(PrendaTipoEJB prendaTipoEJB) {
        this.prendaTipoEJB = prendaTipoEJB;
    }

    public MaterialEJB getMaterialEJB() {
        return materialEJB;
    }

    public void setMaterialEJB(MaterialEJB materialEJB) {
        this.materialEJB = materialEJB;
    }

    public ColorEJB getColorEJB() {
        return colorEJB;
    }

    public void setColorEJB(ColorEJB colorEJB) {
        this.colorEJB = colorEJB;
    }

    public OcasionEJB getOcasionEJB() {
        return ocasionEJB;
    }

    public void setOcasionEJB(OcasionEJB ocasionEJB) {
        this.ocasionEJB = ocasionEJB;
    }

    public EstadoEJB getEstadoEJB() {
        return estadoEJB;
    }

    public void setEstadoEJB(EstadoEJB estadoEJB) {
        this.estadoEJB = estadoEJB;
    }

    public List<Prenda> getPrendas() {
        if (prendas == null) {
            prendas = new ArrayList<>();
            setPrendas(prendaEJB.getPrendas());
        }
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public List<SelectItem> getPrendasTipo() {
        if (prendasTipo == null) {
            prendasTipo = new ArrayList<>();
            setPrendasTipo(prendaTipoEJB.getSelectItemPrendaTipos());
        }
        return prendasTipo;
    }

    public void setPrendasTipo(List<SelectItem> prendasTipo) {
        this.prendasTipo = prendasTipo;
    }

    public List<SelectItem> getMateriales() {
        if (materiales == null) {
            materiales = new ArrayList<>();
            setMateriales(materialEJB.getSelectItemMaterial());
        }
        return materiales;
    }

    public void setMateriales(List<SelectItem> materiales) {
        this.materiales = materiales;
    }

    public List<SelectItem> getColores() {
        if (colores == null) {
            colores = new ArrayList<>();
            setColores(colorEJB.getSelectItemColores());
        }
        return colores;
    }

    public void setColores(List<SelectItem> colores) {
        this.colores = colores;
    }

    public List<SelectItem> getOcasiones() {
        if (ocasiones == null) {
            ocasiones = new ArrayList<>();
            setOcasiones(ocasionEJB.getSelectItemOcasiones());
        }
        return ocasiones;
    }

    public void setOcasiones(List<SelectItem> ocasiones) {
        this.ocasiones = ocasiones;
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

    public PrendaUtil getPrendaCrear() {
        return prendaCrear;
    }

    public void setPrendaCrear(PrendaUtil prendaCrear) {
        this.prendaCrear = prendaCrear;
    }

    public String getPrendaTipoId() {
        return prendaTipoId;
    }

    public void setPrendaTipoId(String prendaTipoId) {
        this.prendaTipoId = prendaTipoId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getOcasionId() {
        return ocasionId;
    }

    public void setOcasionId(String ocasionId) {
        this.ocasionId = ocasionId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public void onRowEdit(RowEditEvent event) {
        Prenda prenda = (Prenda) event.getObject();

        if (!prenda.getPrendaNombre().equals("") && !prendaEJB.getexistePrenda(Comunes.getFormat(prenda.getPrendaNombre()))) {
            Comunes.mensaje((prendaEJB.setModificarPrenda(prenda) ? "Se ha modificado la prenda correctamente " : "Error modificando la prenda "), prendaCrear.getPrendaNombre());
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Prenda) event.getObject()).getPrendaNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearPrenda() {

        if (!prendaEJB.getexistePrenda(Comunes.getFormat(prendaCrear.getPrendaNombre()))) {
            prendaCrear.setPrendaNombre(Comunes.getFormat(prendaCrear.getPrendaNombre()));
            prendaCrear.setPrendaTipoId(prendaTipoId);
            prendaCrear.setMaterialId(materialId);
            prendaCrear.setColorId(colorId);
            prendaCrear.setPrendaDescripcion(Comunes.getFormat(prendaCrear.getPrendaDescripcion()));
            prendaCrear.setOcasionId(ocasionId);
            prendaCrear.setEstadoId("1");

            Comunes.mensaje((prendaEJB.setCrearPrenda(prendaCrear) ? "Se ha creado la prenda correctamente" : "Error creando la prenda"), prendaCrear.getPrendaNombre());
        } else {
            Comunes.mensaje("La prenda ya se encuentra registrada", prendaCrear.getPrendaNombre());
        }
    }
}
