package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Cargo;
import com.universitaria.ateliermaven.ejb.administrador.CargoEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JorgeWilson
 */
public class CargoManagedBean {

    /**
     * Creates a new instance of CargoManagedBean
     */
    public CargoManagedBean() {
    }
    @EJB
    private CargoEJB cargoEJB;
    private List<Cargo> cargos;
    private String desCargo;
    FacesMessage msg = null;

    public List<Cargo> getCargos() {
        if(cargos==null || cargos.isEmpty()){
            cargos = new ArrayList<>();  
            setCargos(cargoEJB.getCargos());
        }
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public String getDesCargo() {        
        return desCargo;
    }

    public void setDesCargo(String desCargo) {
        this.desCargo = desCargo;
    }    
    
    public void onRowEdit(RowEditEvent event) {
        if(cargoEJB.setModificarCargo(((Cargo) event.getObject()).getCargoId().toString(), ((Cargo) event.getObject()).getCargoDesc())){
            msg = new FacesMessage("Mensaje", "Se modifico el Cargo Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el Cargo");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Cargo) event.getObject()).getCargoDesc());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    
    }
    
        public void crearCargo(){  
        RequestContext req = RequestContext.getCurrentInstance();
             if(!cargoEJB.getExisteCargo(desCargo)){
                if(cargoEJB.setCrearCargo(desCargo)){
                    cargos.clear();
                    getCargos();
                    setDesCargo("");
                    req.update(":form");
                    req.execute("PF('dlg1').hide();");
                   msg = new FacesMessage("Mensaje", "Cargo Creado Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Cargo");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Cargo ya se encuentra registrado");
            }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
