/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.atelier.web.utils.PrendaUtil;
import com.universitaria.ateliermaven.ejb.administrador.ColorEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.administrador.OcasionEJB;
import com.universitaria.ateliermaven.ejb.administrador.TallaEJB;
import com.universitaria.ateliermaven.ejb.produccion.DetallePrendaEJB;
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
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

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

    @EJB
    private TallaEJB tallaEJB;

    @EJB
    private MaterialEJB materialesEJB;

    @EJB
    private DetallePrendaEJB detallePrendaEJB;

    private List<Prenda> prendas;

    private List<SelectItem> prendasTipo;
    private List<SelectItem> materiales;
    private List<SelectItem> colores;
    private List<SelectItem> ocasiones;
    private List<SelectItem> estados;
    private List<SelectItem> tallas;

    private DualListModel<MaterialRequerimientoUtil> materialesSelect;

    private PrendaUtil prendaCrear;

    private String prendaTipoId;
    private String colorId;
    private String tallaId;
    private String ocasionId;
    private String estadoId;

    private MaterialRequerimientoUtil material;

    public MaterialRequerimientoUtil getMaterial() {
        return material;
    }

    public void setMaterial(MaterialRequerimientoUtil material) {
        this.material = material;
    }

    public DetallePrendaEJB getDetallePrendaEJB() {
        return detallePrendaEJB;
    }

    public void setDetallePrendaEJB(DetallePrendaEJB detallePrendaEJB) {
        this.detallePrendaEJB = detallePrendaEJB;
    }

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

    public TallaEJB getTallaEJB() {
        return tallaEJB;
    }

    public void setTallaEJB(TallaEJB tallaEJB) {
        this.tallaEJB = tallaEJB;
    }

    public List<SelectItem> getTallas() {
        if (tallas == null) {
            tallas = new ArrayList<>();
            setTallas(tallaEJB.getSelectItemTallas());
        }
        return tallas;
    }

    public void setTallas(List<SelectItem> tallas) {
        this.tallas = tallas;
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

    public String getTallaId() {
        return tallaId;
    }

    public void setTallaId(String tallaId) {
        this.tallaId = tallaId;
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

    public DualListModel<MaterialRequerimientoUtil> getMaterialesSelect() {

        if (materialesSelect == null || (materialesSelect.getSource().isEmpty() && materialesSelect.getTarget().isEmpty())) {
            List<MaterialRequerimientoUtil> source = new ArrayList<>();
            List<MaterialRequerimientoUtil> target = new ArrayList<>();
            source = materialesEJB.getMaterialesRequerimiento();
            setMaterialesSelect(new DualListModel<MaterialRequerimientoUtil>(source, target));
        }
        return materialesSelect;
    }

    public void setMaterialesSelect(DualListModel<MaterialRequerimientoUtil> materialesSelect) {
        this.materialesSelect = materialesSelect;
    }

    public MaterialEJB getMaterialesEJB() {
        return materialesEJB;
    }

    public void setMaterialesEJB(MaterialEJB materialesEJB) {
        this.materialesEJB = materialesEJB;
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
        String mensaje = "Se ha creado la prenda correctamente";
        if (!prendaEJB.getexistePrenda(Comunes.getFormat(prendaCrear.getPrendaNombre()))) {
            prendaCrear.setPrendaNombre(Comunes.getFormat(prendaCrear.getPrendaNombre()));
            prendaCrear.setPrendaTipoId(prendaTipoId);
            prendaCrear.setColorId(colorId);
            prendaCrear.setPrendaDescripcion(Comunes.getFormat(prendaCrear.getPrendaDescripcion()));
            prendaCrear.setOcasionId(ocasionId);
            prendaCrear.setEstadoId("1");
            prendaCrear.setTallaId(tallaId);

            if (prendaEJB.setCrearPrenda(prendaCrear)) {
                Prenda p = prendaEJB.traerPrendaNombre(prendaCrear.getPrendaNombre());
                for (MaterialRequerimientoUtil mru : materialesSelect.getTarget()) {

                    if (!detallePrendaEJB.setCrearDetallePrenda(mru.getMaterialId(), String.valueOf(p.getPrendaId()))) {
                        mensaje = "Se ha creado la prenda, verifique los detalles de la prenda ";
                    }
                }

            }
            Comunes.mensaje(mensaje, prendaCrear.getPrendaNombre());
        } else {
            Comunes.mensaje("La prenda ya se encuentra registrada", prendaCrear.getPrendaNombre());
        }
    }

    public void onTransfer(TransferEvent event) {
        for (Object item : event.getItems()) {
            material = new MaterialRequerimientoUtil();
            material.setNombre(((MaterialRequerimientoUtil) item).getNombre());
            material.setMaterialId(((MaterialRequerimientoUtil) item).getMaterialId());
            material.setReferencia(((MaterialRequerimientoUtil) item).getReferencia());
            material.setMarcaId(((MaterialRequerimientoUtil) item).getMarcaId());
            material.setCantidad("0.0");
        }
    }

}
