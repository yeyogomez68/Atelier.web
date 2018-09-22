/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.produccion;

import com.universitaria.atelier.web.jpa.Prendatipo;
import com.universitaria.ateliermaven.ejb.produccion.PrendaTipoEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author SoulHunter
 */
public class PrendaTipoManagedBean {

    public PrendaTipoManagedBean() {
    }
    
    @EJB
    private PrendaTipoEJB prendaTipoEJB;
    private List<Prendatipo> prendaTipos;
    private String nombrePrendaTipo;
    FacesMessage msg = null;


    public PrendaTipoEJB getPrendaTipoEJB() {
        return prendaTipoEJB;
    }

    public void setPrendaTipoEJB(PrendaTipoEJB prendaTipoEJB) {
        this.prendaTipoEJB = prendaTipoEJB;
    }

    public List<Prendatipo> getPrendaTipos() {
        if (prendaTipos == null|| prendaTipos.isEmpty()) {
            prendaTipos = new ArrayList<>();
            setPrendaTipos(prendaTipoEJB.getPrendatipos());
        }
        return prendaTipos;
    }

    public void setPrendaTipos(List<Prendatipo> prendaTipos) {
        this.prendaTipos = prendaTipos;
    }

    public String getNombrePrendaTipo() {
        return nombrePrendaTipo;
    }

    public void setNombrePrendaTipo(String nombrePrendaTipo) {
        this.nombrePrendaTipo = nombrePrendaTipo;
    }

    public void onRowEdit(RowEditEvent event) {
        if(prendaTipoEJB.setModificarPrendaTipo(((Prendatipo) event.getObject()).getPrendaTipoId().toString(), ((Prendatipo) event.getObject()).getPrendaTipoDescripcion())){
            msg = new FacesMessage("Mensaje", "Se modifico el Tipo de Prenda Exitosamente");
        }else{
            msg = new FacesMessage("Error", "Error a modificar el Tipo de Prenda");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Prendatipo) event.getObject()).getPrendaTipoDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearPrendaTipo() {
        RequestContext req = RequestContext.getCurrentInstance();
        if(getNombrePrendaTipo()!=null){        
           if (!prendaTipoEJB.existePrendaTipo(nombrePrendaTipo)) {
               if(prendaTipoEJB.setCrearTipoPrenda(nombrePrendaTipo)){
                    prendaTipos.clear();
                    getPrendaTipos();
                    setNombrePrendaTipo("");
                    req.update(":form");
                    req.execute("PF('dlg1').hide();");
                   msg = new FacesMessage("Mensaje", "Tipo de Prenda Creado Exitosamente");
                }else{
                   msg = new FacesMessage("Error", "Error al crear el Tipo de Prenda ");
                }                 
            }else{
                msg = new FacesMessage("Mensaje", "El Tipo de Prenda ya se encuentra registrado");
            }
        }else{
            msg = new FacesMessage("Error", "El nombre del Tipo de Prenda es obligatorio");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
  }
