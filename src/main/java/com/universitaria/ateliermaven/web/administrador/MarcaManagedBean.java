/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Marca;
import com.universitaria.ateliermaven.ejb.administrador.MarcaEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author jeisson.gomez
 */
public class MarcaManagedBean {

    /**
     * Creates a new instance of MarcaManagedBean
     */
    public MarcaManagedBean() {
    }
    
    @EJB
    private MarcaEJB marcaEJB;
    private List<Marca> marcas;
    private String marcaDesc;
    FacesMessage msg = null;

    public List<Marca> getMarcas() {
        if(marcas==null || marcas.isEmpty()){
            marcas = new ArrayList<>();
            setMarcas(marcaEJB.getMarcas());
        }
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public String getMarcaDesc() {
        return marcaDesc;
    }

    public void setMarcaDesc(String marcaDesc) {
        this.marcaDesc = marcaDesc;
    }
    
    
    public void onRowEdit(RowEditEvent event) {
        if(marcaEJB.setModifiMarca(((Marca) event.getObject()).getMarcaId().toString(), ((Marca) event.getObject()).getMarcaNombre())){
            msg = new FacesMessage("Mensaje", "Se modifico la Marca Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar la Marca");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Edición Cancelada", ((Marca) event.getObject()).getMarcaNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearMarca(){
        RequestContext req = RequestContext.getCurrentInstance();
        if(getMarcaDesc()!=null){
            if(!marcaEJB.getExisteMarca(marcaDesc)){
                if(marcaEJB.setCrearMarca(marcaDesc)){
                   marcas.clear();
                   getMarcas();
                   setMarcaDesc("");
                   req.update(":form");
                   req.execute("PF('dlg1').hide();"); 
                    
                   msg = new FacesMessage("Mensaje", "Marca Creada Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear la Marca");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "La Marca ya se encuentra registrada");
            }
        }else{
            msg = new FacesMessage("Error", "El nombre de la Marca es obligatorio");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
