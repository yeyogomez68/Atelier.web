/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Departamento;
import com.universitaria.atelier.web.utils.DepartamentoUtil;
import com.universitaria.ateliermaven.ejb.administrador.DepartamentoEJB;
import com.universitaria.ateliermaven.ejb.administrador.PaisEJB;
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
 * @author SoulHunter
 */
public class DepartamentoManagedBean {
    
    public DepartamentoManagedBean() {
        departamentoCrear = new DepartamentoUtil();
    }
    
    @EJB
    private DepartamentoEJB departamentoEJB;
    @EJB
    private PaisEJB paisEJB;
    
    private List<Departamento> departamentos;
    private List<SelectItem> paises;
    
    private String paisId;
    
    private DepartamentoUtil departamentoCrear;
    
    public DepartamentoEJB getDepartamentoEJB() {
        return departamentoEJB;
    }
    
    public void setDepartamentoEJB(DepartamentoEJB departamentoEJB) {
        this.departamentoEJB = departamentoEJB;
    }
    
    public PaisEJB getPaisEJB() {
        return paisEJB;
    }
    
    public void setPaisEJB(PaisEJB paisEJB) {
        this.paisEJB = paisEJB;
    }
    
    public List<Departamento> getDepartamentos() {
        if (departamentos == null) {
            departamentos = new ArrayList<>();
            setDepartamentos(departamentoEJB.getDepartamentos());
        }
        return departamentos;
    }
    
    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
    
    public List<SelectItem> getPaises() {
        if (paises == null) {
            paises = new ArrayList<>();
            setPaises(paisEJB.getSelectItemPaises());
        }
        return paises;
    }
    
    public void setPaises(List<SelectItem> paises) {
        this.paises = paises;
    }
    
    public String getPaisId() {
        return paisId;
    }
    
    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }
    
    public DepartamentoUtil getDepartamentoCrear() {
        return departamentoCrear;
    }
    
    public void setDepartamentoCrear(DepartamentoUtil departamentoCrear) {
        this.departamentoCrear = departamentoCrear;
    }
    
    public void onRowEdit(RowEditEvent event) {
        
        if (!departamentoCrear.getDepartamentoNombre().equals("") && !departamentoEJB.existeDepartamento(Comunes.getFormat(departamentoCrear.getDepartamentoNombre()))) {
            
            
        }
        
       // Comunes.mensaje((departamentoEJB.setModificarDepartamento(((Departamento) event.getObject()).getPaisId().toString(), Comunes.getFormat(((Departamento) event.getObject()).getDepartamentoNombre())) ? "Se ha modificado el departamento correctamente " : "Error modificando el departamento "), departamentoCrear.getDepartamentoNombre());
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Departamento) event.getObject()).getDepartamentoNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void crearDepartamento() {
        if (!departamentoEJB.existeDepartamento(Comunes.getFormat(departamentoCrear.getDepartamentoNombre()))) {
            departamentoCrear.setDepartamentoNombre(Comunes.getFormat(departamentoCrear.getDepartamentoNombre()));
            departamentoCrear.setPaisId(paisId);
            Comunes.mensaje((departamentoEJB.setCrearDepartamento(departamentoCrear) ? "Se ha creado el departamento correctamente" : "Error creando el pais"), departamentoCrear.getDepartamentoNombre());
        } else {
            Comunes.mensaje("El departamento ya se encuentra registrado", departamentoCrear.getDepartamentoNombre());
        }
    }
}
