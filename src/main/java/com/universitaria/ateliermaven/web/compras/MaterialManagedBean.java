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
 * @author jeisson.gomez
 */
public class MaterialManagedBean implements Serializable{

    /**
     * Creates a new instance of MaterialManagedBean
     */
    public MaterialManagedBean() {
        this.materialCrear= new MaterialUtil();
    }
    
    

    
    @EJB
    private MaterialEJB materialEJB;
    private MaterialTipoEJB materialTiposEJB;
    
    private List<Material> materiales;
    private List<SelectItem> materialTipos;
    private List<SelectItem> marcas;
    private MaterialUtil materialUtil;
    private MaterialUtil materialCrear;
    private MarcaEJB marcaEJB;
    FacesMessage msg = null;
    private String marcaId;
    private String materialTipoId;
    
    private MaterialUtil materiaCrear;
    
    

    public List<SelectItem> getMarcas() {
        if(marcas==null || marcas.isEmpty()){
            marcas = new ArrayList<>();
            }
            setMarcas(marcaEJB.getSelectItemMarcas());
        
        return marcas;
    }

    public void setMarcas(List<SelectItem> marcas) {
        this.marcas = marcas;
    }

               
    public String getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(String marcaId) {
        this.marcaId = marcaId;
    }

    public String getMaterialTipoId() {
        return materialTipoId;
    }

    public void setMaterialTipoId(String materialTipoId) {
        this.materialTipoId = materialTipoId;
    }

    public List<SelectItem> getMaterialTipos() {
        if(materialTipos==null || materialTipos.isEmpty()){
            materialTipos = new ArrayList<>();   
            setMaterialTipos(materialTiposEJB.getSelectItemMaterialTipo());
        }
        return materialTipos;
    }


    public void setMaterialTipos(List<SelectItem> materialTipos) {
        this.materialTipos = materialTipos;
    }

     
    public MaterialUtil getMaterialCrear() {
        return materialCrear;
    }

    public void setMaterialCrear(MaterialUtil materialCrear) {
        this.materialCrear = materialCrear;
    }

        
    public List<Material> getMateriales() {
        if(materiales == null || materiales.isEmpty()){
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
                
    public void onRowEdit(RowEditEvent event) {
        
        MaterialUtil util = new MaterialUtil();
        util.setMaterialId(((Material) event.getObject()).getMaterialId());
        util.setNombre(((Material) event.getObject()).getMaterialNombre());
        util.setReferencia(((Material) event.getObject()).getMaterialReference());
        materialEJB.setModificarMaterial(util); 
        materiales.clear();
        getMateriales();
    }
     
        
        /*materialUtil.setNombre(((Material) event.getObject()).getMaterialNombre());
        materialUtil.setReferencia((((Material) event.getObject()).getMaterialReference()));
        
        if(materialEJB.setModificarMaterial(materialUtil)){
            msg = new FacesMessage("Mensaje", "Se modifico el Material Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el Material");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }*/
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((Material) event.getObject()).getMaterialNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearMaterial(){
        FacesMessage msg;
        RequestContext req = RequestContext.getCurrentInstance();
        materialCrear.setMarcaId(marcaId);
        materialCrear.setMaterialTipoId(materialTipoId);
        if(materialEJB.setCrearMaterial(materialCrear)){
            msg = new FacesMessage("Mensaje", "Material Creado con exito"); 
            materiales.clear();
            getMateriales();
            limpiarCapos();
            req.update(":form");
            req.execute("PF('dlg1').hide();");  
        }else{
            msg = new FacesMessage("Mensaje", "Error al crear el Material");            
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    private void limpiarCapos(){
        materialCrear = new MaterialUtil();
    }
}
