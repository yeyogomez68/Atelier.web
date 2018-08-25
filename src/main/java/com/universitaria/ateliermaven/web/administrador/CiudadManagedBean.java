/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Ciudad;
import com.universitaria.atelier.web.utils.CiudadUtil;
import com.universitaria.ateliermaven.ejb.administrador.CiudadEJB;
import com.universitaria.ateliermaven.ejb.administrador.DepartamentoEJB;
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
 * @author SoulHunter
 */
public class CiudadManagedBean implements
        Serializable {

    public CiudadManagedBean() {
        ciudadCrear = new CiudadUtil();
    }
    @EJB
    private CiudadEJB ciudadEJB;

    @EJB
    private DepartamentoEJB departamentoEJB;

    private List<Ciudad> ciudades;
    private List<SelectItem> departamentos;

    private String departamentoId;

    private CiudadUtil ciudadCrear;


    public CiudadEJB getCiudadEJB() {
        return ciudadEJB;
    }

    public void setCiudadEJB(CiudadEJB ciudadEJB) {
        this.ciudadEJB = ciudadEJB;
    }

    public DepartamentoEJB getDepartamentoEJB() {
        return departamentoEJB;
    }

    public void setDepartamentoEJB(DepartamentoEJB departamentoEJB) {
        this.departamentoEJB = departamentoEJB;
    }

    public List<Ciudad> getCiudades() {
        if (ciudades == null) {
            ciudades = new ArrayList<>();
            setCiudades(ciudadEJB.getCiudades());
        }
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<SelectItem> getDepartamentos() {
        if (departamentos == null) {
            departamentos = new ArrayList<>();
            setDepartamentos(departamentoEJB.getSelectItemDepartamentos());
        }
        return departamentos;
    }

    public void setDepartamentos(List<SelectItem> departamentos) {
        this.departamentos = departamentos;
    }

    public String getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(String departamentoId) {
        this.departamentoId = departamentoId;
    }

    public CiudadUtil getCiudadCrear() {
        return ciudadCrear;
    }

    public void setCiudadCrear(CiudadUtil ciudadCrear) {
        this.ciudadCrear = ciudadCrear;
    }

    public void onRowEdit(RowEditEvent event) {
        Ciudad ciudad = (Ciudad) event.getObject();

        if (!ciudad.getCiudadNombre().equals("") && !ciudadEJB.existeCiudad(Comunes.getFormat(ciudad.getCiudadNombre()))) {
            Comunes.mensaje((ciudadEJB.setModificarCiudad(ciudad, departamentoId) ? "Se ha modificado la ciudad correctamente " : "Error modificando la ciudad "), ciudadCrear.getCiudadNombre());
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Ciudad) event.getObject()).getCiudadNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearCiudad() {
        if (!ciudadEJB.existeCiudad(Comunes.getFormat(ciudadCrear.getCiudadNombre()))) {
            ciudadCrear.setCiudadNombre(Comunes.getFormat(ciudadCrear.getCiudadNombre()));
            ciudadCrear.setDepartamentoId(departamentoId);
            Comunes.mensaje((ciudadEJB.setCrearCiudad(ciudadCrear) ? "Se ha creado la ciudad correctamente" : "Error creando la ciudad"), ciudadCrear.getCiudadNombre());
        } else {
            Comunes.mensaje("La ciudad ya se encuentra registrada", ciudadCrear.getCiudadNombre());
        }
    }

}
