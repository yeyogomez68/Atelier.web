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
public class PrendaTipoManagedBean implements Serializable {

    @EJB
    private PrendaTipoEJB prendaTipoEJB;
    private List<Prendatipo> prendaTipos;
    private String nombrePrendaTipo;

    public PrendaTipoEJB getPrendaTipoEJB() {
        return prendaTipoEJB;
    }

    public void setPrendaTipoEJB(PrendaTipoEJB prendaTipoEJB) {
        this.prendaTipoEJB = prendaTipoEJB;
    }

    public List<Prendatipo> getPrendaTipos() {
        if (prendaTipos == null) {
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
        Comunes.mensaje((prendaTipoEJB.setModificarPrendaTipo(((Prendatipo) event.getObject()).getPrendaTipoId().toString(), Comunes.getFormat(((Prendatipo) event.getObject()).getPrendaTipoDescripcion())) ? "Se ha modificado el tipo de prenda correctamente" : "Error modificando el tipo de prenda"), nombrePrendaTipo);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Prendatipo) event.getObject()).getPrendaTipoDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void crearPrendaTipo() {
        RequestContext req = RequestContext.getCurrentInstance();
        if (!prendaTipoEJB.existePrendaTipo(nombrePrendaTipo)) {
            Comunes.mensaje((prendaTipoEJB.setCrearTipoPrenda(nombrePrendaTipo) ? "Se ha creado el tipo de prenda correctamente" : "Error creando el tipo de prenda"), nombrePrendaTipo);
            prendaTipos.clear();
            setPrendaTipos(prendaTipoEJB.getPrendatipos());
            nombrePrendaTipo = "";
            req.update(":form");
            req.execute("PF('dlg1').hide();");

        } else {
            Comunes.mensaje("El tipo de prenda ya se encuentra registrado", nombrePrendaTipo);
        }
    }

}
