/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;



import com.universitaria.atelier.web.jpa.Color;
import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Ocasion;
import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.atelier.web.jpa.Prendatipo;
import com.universitaria.ateliermaven.ejb.administrador.ColorEJB;
import com.universitaria.ateliermaven.ejb.compras.MaterialEJB;
import com.universitaria.ateliermaven.ejb.administrador.OcasionEJB;
import com.universitaria.ateliermaven.ejb.administrador.PrendaEJB;
import com.universitaria.ateliermaven.ejb.administrador.PrendaTipoEJB;

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
public class PrendaManagedBean {

    /**
     * Creates a new instance of PrendaManagedBean
     */
    public PrendaManagedBean() {
    }
    
    @EJB
    private PrendaEJB prendaEJB;
    
    @EJB
    private ColorEJB colorEJB;
    
    @EJB
    private MaterialEJB materialEJB;
    
    @EJB
    private PrendaTipoEJB prendatipoEJB;
    
    @EJB
    private OcasionEJB ocasionEJB;
    
    /*Listas*/    
    private List<Prenda> prendas;
    private List<SelectItem> colores;
    private List<SelectItem> materiales;
    private List<SelectItem> tipoPrendas;
    private List<SelectItem> ocasiones;
    
    private String prendaNombre;
    private String prendaDescrip;
    
    private String colorId;
    private String materialId;
    private String tipoPrendaId;
    private String ocasionId;
    
    
    private Prenda prendaUtil;

    public Prenda getPrendaUtil() {
        return prendaUtil;
    }

    public void setPrendaUtil(Prenda prendaUtil) {
        this.prendaUtil = prendaUtil;
    }   
    
    public String getPrendaNombre() {
        return prendaNombre;
    }

    public void setPrendaNombre(String prendaNombre) {
        this.prendaNombre = prendaNombre;
    }

    public String getPrendaDescrip() {
        return prendaDescrip;
    }

    public void setPrendaDescrip(String prendaDescrip) {
        this.prendaDescrip = prendaDescrip;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getTipoPrendaId() {
        return tipoPrendaId;
    }

    public void setTipoPrendaId(String tipoPrendaId) {
        this.tipoPrendaId = tipoPrendaId;
    }

    public List<SelectItem> getOcasiones() {
        if(ocasiones==null || ocasiones.isEmpty()){
            ocasiones = new ArrayList<>();           
        }else{
            ocasiones.clear();           
        }
        for (Ocasion ocasio : ocasionEJB.getOcasiones()) {
            ocasiones.add(new SelectItem(ocasio.getOcasionId(),ocasio.getOcasionDescrip()));
        }
        return ocasiones;
    }

    public void setOcasiones(List<SelectItem> ocasiones) {
        this.ocasiones = ocasiones;
    }

    public String getOcasionId() {
        return ocasionId;
    }

    public void setOcasionId(String ocasionId) {
        this.ocasionId = ocasionId;
    }
    
    
    
    public List<Prenda> getPrendas() {        
        if(prendas==null || prendas.isEmpty()){
            prendas = new ArrayList<>();           
        }else{
            prendas.clear();           
        }      
        setPrendas(prendaEJB.getPrendas());
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }  

    public List<SelectItem> getColores() {
        if(colores==null || colores.isEmpty()){
            colores = new ArrayList<>();           
        }else{
            colores.clear();           
        }
        for (Color colore : colorEJB.getColores()) {
            colores.add(new SelectItem(colore.getColorId(),colore.getColorDescrip()));
        }
        return colores;
    }

    public void setColores(List<SelectItem> colores) {
        this.colores = colores;
    }

    public List<SelectItem> getMateriales() {
        if(materiales==null || materiales.isEmpty()){
            materiales = new ArrayList<>();           
        }else{
            materiales.clear();           
        } 
        for (Material materiale : materialEJB.getMateriales()) {
            materiales.add(new SelectItem(materiale.getMaterialId(),materiale.getMaterialNombre()));
        }
        return materiales;
    }

    public void setMateriales(List<SelectItem> materiales) {
        this.materiales = materiales;
    }

    public List<SelectItem> getTipoPrendas() {
        if(tipoPrendas==null || tipoPrendas.isEmpty()){
            tipoPrendas = new ArrayList<>();           
        }else{
            tipoPrendas.clear();           
        }      
        for (Prendatipo prendatipo : prendatipoEJB.getPrendatipos()) {
            tipoPrendas.add(new SelectItem(prendatipo.getPrendaTipoId(), prendatipo.getPrendaTipoDescripcion()));
        }
        return tipoPrendas;
    }

    public void setTipoPrendas(List<SelectItem> tipoPrendas) {
        this.tipoPrendas = tipoPrendas;
    }
    
    
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edited", ((Prenda) event.getObject()).getPrendaNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelled", ((Prenda) event.getObject()).getPrendaNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearPrenda(){
        
    }
}
