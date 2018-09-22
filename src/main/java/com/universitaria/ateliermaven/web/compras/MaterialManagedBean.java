/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.utils.MaterialUtil;
import com.universitaria.ateliermaven.ejb.administrador.MarcaEJB;
import com.universitaria.ateliermaven.ejb.administrador.MaterialTipoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author jeisson.gomez
 */
public class MaterialManagedBean {

    /**
     * Creates a new instance of MaterialManagedBean
     */
    public MaterialManagedBean() {
        materialUtil = new MaterialUtil();
    }

    @EJB
    private MaterialEJB materialEJB;
    @EJB
    private MaterialTipoEJB materialTipoEJB;
    @EJB
    private MarcaEJB marcaEJB;

    private List<Material> materiales;
    private List<SelectItem> materialTipo;
    private List<SelectItem> marcas;

    private MaterialUtil materialUtil;
    private String materialTipoId;
    private String marca;

    public MarcaEJB getMarcaEJB() {
        return marcaEJB;
    }

    public void setMarcaEJB(MarcaEJB marcaEJB) {
        this.marcaEJB = marcaEJB;
    }

    public List<SelectItem> getMarcas() {
        if (marcas == null) {
            marcas = new ArrayList<>();
            setMarcas(marcaEJB.getSelectItemMarca());

        }
        return marcas;
    }

    public void setMarcas(List<SelectItem> marcas) {
        this.marcas = marcas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<Material> getMateriales() {
        if (materiales == null || materiales.isEmpty()) {
            materiales = new ArrayList<>();
            setMateriales(materialEJB.getMateriales());
        }
        return materiales;
    }

    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }

    public MaterialUtil getMaterialUtil() {
        return materialUtil;
    }

    public void setMaterialUtil(MaterialUtil materialUtil) {
        this.materialUtil = materialUtil;
    }

    public String getMaterialTipoId() {
        return materialTipoId;
    }

    public void setMaterialTipoId(String materialTipoId) {
        this.materialTipoId = materialTipoId;
    }

    public MaterialEJB getMaterialEJB() {
        return materialEJB;
    }

    public void setMaterialEJB(MaterialEJB materialEJB) {
        this.materialEJB = materialEJB;
    }

    public MaterialTipoEJB getMaterialTipoEJB() {
        return materialTipoEJB;
    }

    public void setMaterialTipoEJB(MaterialTipoEJB materialTipoEJB) {
        this.materialTipoEJB = materialTipoEJB;
    }

    public List<SelectItem> getMaterialTipo() {
        if (materialTipo == null) {
            materialTipo = new ArrayList<>();
            setMaterialTipo(materialTipoEJB.getSelectItemMaterialTipo());
        }
        return materialTipo;
    }

    public void setMaterialTipo(List<SelectItem> materialTipo) {
        this.materialTipo = materialTipo;
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((Material) event.getObject()).getMaterialNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {

        materialUtil.setNombre(((Material) event.getObject()).getMaterialNombre());
        materialUtil.setReferencia((((Material) event.getObject()).getMaterialReference()));
        materialUtil.setTipoId(materialTipoId);
        if (materialEJB.setModificarMaterial(materialUtil)) {

        } else {

        }

    }

    public void crearMaterial() {
        if (!materialEJB.existeMaterial(materialUtil.getReferencia())) {
            materialUtil.setMarcaId(marca);
            materialUtil.setTipoId(materialTipoId);
            Comunes.mensaje((materialEJB.setCrearMaterial(materialUtil) ? "Se ha creado el material correctamente" : "Error creando el material"), materialUtil.getNombre());
        } else {
            Comunes.mensaje("El material ya se encuentra registrado", materialUtil.getNombre());
        }

    }
}
