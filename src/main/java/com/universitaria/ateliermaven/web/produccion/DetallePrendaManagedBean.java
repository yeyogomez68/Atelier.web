/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Prendamaterial;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.produccion.DetallePrendaEJB;
import com.universitaria.ateliermaven.ejb.produccion.PrendasEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
public class DetallePrendaManagedBean implements Serializable {

    @EJB
    private DetallePrendaEJB detallePrendaEJB;
    @EJB
    private MaterialEJB materialEJB;
    @EJB
    private PrendasEJB prendasEJB;

    private List<Prendamaterial> detallePrendas;
    private List<SelectItem> prendas;
    private List<SelectItem> materiales;

    private String idMaterial;
    private String idPrenda;

    public DetallePrendaEJB getDetallePrendaEJB() {
        return detallePrendaEJB;
    }

    public void setDetallePrendaEJB(DetallePrendaEJB detallePrendaEJB) {
        this.detallePrendaEJB = detallePrendaEJB;
    }

    public MaterialEJB getMaterialEJB() {
        return materialEJB;
    }

    public void setMaterialEJB(MaterialEJB materialEJB) {
        this.materialEJB = materialEJB;
    }

    public PrendasEJB getPrendasEJB() {
        return prendasEJB;
    }

    public void setPrendaEJB(PrendasEJB prendaEJB) {
        this.prendasEJB = prendaEJB;
    }

    public List<Prendamaterial> getDetallePrendas() {
        if (detallePrendas == null) {

            detallePrendas = new ArrayList<>();
            setDetallePrendas(detallePrendaEJB.getDetallePrenda());
        }
        return detallePrendas;
    }

    public void setDetallePrendas(List<Prendamaterial> detallePrendas) {
        this.detallePrendas = detallePrendas;
    }

    public List<SelectItem> getPrendas() {
        if (prendas == null) {
            prendas = new ArrayList<>();
            setPrendas(prendasEJB.getSelectItemPrenda());
        }
        return prendas;
    }

    public void setPrendas(List<SelectItem> prendas) {
        this.prendas = prendas;
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

    public String getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(String idPrenda) {
        this.idPrenda = idPrenda;
    }

    public void crearDetallePrenda() {

        Comunes.mensaje((detallePrendaEJB.setCrearDetallePrenda(idMaterial, idPrenda) ? "Se ha creado el detalle de la prenda correctamente" : "Error creando el detalle de la prenda"), "");

    }

    public void eliminarDetallePrenda(Prendamaterial pm) {

        Comunes.mensaje((detallePrendaEJB.setBorrarDetallePrenda(pm) ? "Se ha eliminado el detalle de la prenda correctamente" : "Error eliminando el detalle de la prenda"), "");

    }

}
