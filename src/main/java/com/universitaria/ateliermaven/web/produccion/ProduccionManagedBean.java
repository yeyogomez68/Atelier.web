/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Produccion;

import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.DetalleProduccionUtil;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.atelier.web.utils.MaterialUtil;

import com.universitaria.atelier.web.utils.ProduccionUtil;
import com.universitaria.ateliermaven.ejb.UsuarioEJB;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.inventario.StockMaterialesEJB;
import com.universitaria.ateliermaven.ejb.produccion.DetalleProduccionEJB;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

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
    private MaterialEJB materialesEJB;
    @EJB
    private EstadoEJB estadoEJB;
    @EJB
    private UsuarioEJB usuarioEJB;
    @EJB
    private DetalleProduccionEJB detalleProduccionEJB;
    @EJB
    private PrendasEJB prendasEJB;
    @EJB
    private StockMaterialesEJB stockMaterialesEJB;

    private List<Produccion> producciones;

    private List<SelectItem> estados;
    private List<SelectItem> usuarios;
    private List<SelectItem> prendas;

    private DualListModel<MaterialRequerimientoUtil> materialesSelect;

    private String estadoId;
    private String usuarioId;
    private String prendaId;

    private ProduccionUtil produccionCrear;
    private MaterialRequerimientoUtil material;
    private int cantidad;

    public ProduccionEJB getProduccionEJB() {
        return produccionEJB;
    }

    public void setProduccionEJB(ProduccionEJB produccionEJB) {
        this.produccionEJB = produccionEJB;
    }

    public MaterialEJB getMaterialesEJB() {
        return materialesEJB;
    }

    public void setMaterialesEJB(MaterialEJB materialesEJB) {
        this.materialesEJB = materialesEJB;
    }

    public PrendasEJB getPrendasEJB() {
        return prendasEJB;
    }

    public void setPrendasEJB(PrendasEJB prendasEJB) {
        this.prendasEJB = prendasEJB;
    }

    public StockMaterialesEJB getStockMaterialesEJB() {
        return stockMaterialesEJB;
    }

    public void setStockMaterialesEJB(StockMaterialesEJB stockMaterialesEJB) {
        this.stockMaterialesEJB = stockMaterialesEJB;
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

    public DetalleProduccionEJB getDetalleProduccionEJB() {
        return detalleProduccionEJB;
    }

    public void setDetalleProduccionEJB(DetalleProduccionEJB detalleProduccionEJB) {
        this.detalleProduccionEJB = detalleProduccionEJB;
    }

    public PrendasEJB getPrendaEJB() {
        return prendasEJB;
    }

    public void setPrendaEJB(PrendasEJB prendaEJB) {
        this.prendasEJB = prendaEJB;
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

    public MaterialRequerimientoUtil getMaterial() {
        return material;
    }

    public void setMaterial(MaterialRequerimientoUtil material) {
        this.material = material;
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

    public MaterialRequerimientoUtil getPrenda() {
        return material;
    }

    public void setPrenda(MaterialRequerimientoUtil prenda) {
        this.material = prenda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void onRowEdit(RowEditEvent event) {
        Produccion produccion = (Produccion) event.getObject();
        if (!produccion.getProduccionDescripcion().equals("")) {
            Comunes.mensaje((produccionEJB.setModificarProduccion(produccion, estadoId) ? "Se ha modificado la produccion correctamente " : "Error modificando la produccion "), produccionCrear.getProduccionDescripcion());
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Produccion) event.getObject()).getProduccionDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearProduccion() {
        String mensaje = "Se ha creado la produccion correctamente";
        if (!produccionEJB.existeProduccion(Comunes.getFormat(produccionCrear.getProduccionDescripcion()))) {
            RequestContext req = RequestContext.getCurrentInstance();
            produccionCrear.setProduccionDescripcion(Comunes.getFormat(produccionCrear.getProduccionDescripcion()));
            produccionCrear.setEstadoId("1");
            produccionCrear.setAvance("0");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
            produccionCrear.setUsuarioCreador(user.getUsuarioId().toString());
            produccionCrear.setPrendaId(prendaId);
            if (produccionEJB.setCrearProduccion(produccionCrear)) {
                Produccion p = produccionEJB.traerProduccionDes(Comunes.getFormat(produccionCrear.getProduccionDescripcion()));
                for (MaterialRequerimientoUtil pu : materialesSelect.getTarget()) {

                    DetalleProduccionUtil dpu = new DetalleProduccionUtil();
                    dpu.setMaterialId(pu.getMaterialId());
                    dpu.setProduccionDetaCant(pu.getCantidad());
                    dpu.setProduccionDetaFecha(produccionCrear.getProduccionFechaDate());
                    dpu.setProduccionId(String.valueOf(p.getProduccionId()));
                    dpu.setUsuarioId(pu.getUsuarioId());
                    dpu.setEstadoId("1");
                    if (!detalleProduccionEJB.setCrearDetalleProduccion(dpu)) {
                        mensaje = "Se ha creado la produccion, verifique los detalles de la produccion ";
                    }
                }
                Comunes.mensaje(mensaje, produccionCrear.getProduccionDescripcion());
            } else {
                Comunes.mensaje("Error creando la produccion", produccionCrear.getProduccionDescripcion());
            }
            limpiarLista();
            setCantidad(0);
            req.update(":form");
            req.execute("PF('dlg1').hide();");
        } else {
            Comunes.mensaje("La produccion ya se encuentra registrada", produccionCrear.getProduccionDescripcion());
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
        for (MaterialRequerimientoUtil listaPrendas : materialesSelect.getTarget()) {
            if (listaPrendas.getMaterialId() == null ? listaPrendas.getMaterialId() == null : listaPrendas.getMaterialId().equals(material.getMaterialId())) {
                setCantidad(0);
                RequestContext req = RequestContext.getCurrentInstance();
                req.execute("PF('dlg2').show();");
                break;
            }
        }
    }

    public void limpiarLista() {
        materialesSelect.getSource().clear();
        materialesSelect.getTarget().clear();
        getMaterialesSelect();
    }

    public void cantidades() {
        for (MaterialRequerimientoUtil listaMateriales : materialesSelect.getTarget()) {
            if (listaMateriales.getMaterialId() == null ? material.getMaterialId() == null : listaMateriales.getMaterialId().equals(material.getMaterialId())) {
                listaMateriales.setCantidad(String.valueOf(cantidad));
                listaMateriales.setUsuarioId(usuarioId);
                break;
            }
        }
        setCantidad(0);
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg2').hide();");
    }

}
