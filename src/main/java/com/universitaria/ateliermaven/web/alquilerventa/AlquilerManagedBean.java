/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.alquilerventa;

import com.universitaria.atelier.web.jpa.Renta;
import com.universitaria.atelier.web.jpa.Rentadeta;
import com.universitaria.atelier.web.jpa.Reservacion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.atelier.web.utils.AlquilarUtil;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.atelier.web.utils.PrendaUtil;
import com.universitaria.ateliermaven.ejb.alquilerventas.DetalleRentaEJB;
import com.universitaria.ateliermaven.ejb.alquilerventas.RentaEJB;
import com.universitaria.ateliermaven.ejb.alquilerventas.ReservaEJB;
import com.universitaria.ateliermaven.ejb.produccion.PrendasEJB;
import com.universitaria.ateliermaven.web.comunes.Comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author SoulHunter
 */
public class AlquilerManagedBean implements Serializable {

    public AlquilerManagedBean() {
        alquilarUtil = new AlquilarUtil();
        prendaUtil = new PrendaUtil();
    }

    @EJB
    private RentaEJB rentaEJB;
    @EJB
    private DetalleRentaEJB detalleRentaEJB;
    @EJB
    private ReservaEJB reservaEJB;
    @EJB
    private PrendasEJB prendasEJB;

    private List<Renta> rentasActivas;
    private List<Rentadeta> detalleRenta;
    private List<Reservacion> reservacionActivas;
    private List<Reservacion> reservacionClienteActivas;

    private DualListModel<PrendaUtil> prendasSelect;

    private AlquilarUtil alquilarUtil;
    private PrendaUtil prendaUtil;
    private int valor;

    public PrendaUtil getPrendaUtil() {
        return prendaUtil;
    }

    public void setPrendaUtil(PrendaUtil prendaUtil) {
        this.prendaUtil = prendaUtil;
    }

    public PrendasEJB getPrendasEJB() {
        return prendasEJB;
    }

    public void setPrendasEJB(PrendasEJB prendasEJB) {
        this.prendasEJB = prendasEJB;
    }

    public DualListModel<PrendaUtil> getPrendasSelect() {

        if (prendasSelect == null || (prendasSelect.getSource().isEmpty() && prendasSelect.getTarget().isEmpty())) {
            List<PrendaUtil> source = new ArrayList<>();
            List<PrendaUtil> target = new ArrayList<>();
            source = prendasEJB.getPrendasProduccionDisponibles();
            setPrendasSelect(new DualListModel<PrendaUtil>(source, target));
        }

        return prendasSelect;
    }

    public void setPrendasSelect(DualListModel<PrendaUtil> prendasSelect) {
        this.prendasSelect = prendasSelect;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public AlquilarUtil getAlquilarUtil() {
        return alquilarUtil;
    }

    public void setAlquilarUtil(AlquilarUtil alquilarUtil) {
        this.alquilarUtil = alquilarUtil;
    }

    public List<Reservacion> getReservacionClienteActivas() {
        return reservacionClienteActivas;
    }

    public void setReservacionClienteActivas(List<Reservacion> reservacionClienteActivas) {
        this.reservacionClienteActivas = reservacionClienteActivas;
    }

    public RentaEJB getRentaEJB() {
        return rentaEJB;
    }

    public void setRentaEJB(RentaEJB rentaEJB) {
        this.rentaEJB = rentaEJB;
    }

    public ReservaEJB getReservaEJB() {
        return reservaEJB;
    }

    public void setReservaEJB(ReservaEJB reservaEJB) {
        this.reservaEJB = reservaEJB;
    }

    public List<Reservacion> getReservacionActivas() {
        if (reservacionActivas == null) {
            reservacionActivas = new ArrayList<>();
            setReservacionActivas(reservaEJB.getReservacionActivas());
        }
        return reservacionActivas;
    }

    public void setReservacionActivas(List<Reservacion> reservacionActivas) {
        this.reservacionActivas = reservacionActivas;
    }

    public List<Renta> getRentasActivas() {
        if (rentasActivas == null) {
            rentasActivas = new ArrayList<>();
            setRentasActivas(rentaEJB.getRentasActivas());
        }
        return rentasActivas;
    }

    public void setRentasActivas(List<Renta> rentasActivas) {
        this.rentasActivas = rentasActivas;
    }

    public List<Rentadeta> getDetalleRenta() {
        if (detalleRenta == null) {
            detalleRenta = new ArrayList<>();
        }
        return detalleRenta;
    }

    public void setDetalleRenta(List<Rentadeta> detalleRenta) {
        this.detalleRenta = detalleRenta;
    }

    public DetalleRentaEJB getDetalleRentaEJB() {

        return detalleRentaEJB;
    }

    public void setDetalleRentaEJB(DetalleRentaEJB detalleRentaEJB) {
        if (detalleRenta == null) {
            detalleRenta = new ArrayList<>();
        }
        detalleRenta.clear();
        this.detalleRentaEJB = detalleRentaEJB;
    }

    public void verRentasActivas(Renta renta) {
        RequestContext req = RequestContext.getCurrentInstance();

        setDetalleRenta(detalleRentaEJB.getRentaDetalleRenta(renta));
        if (detalleRenta != null && !detalleRenta.isEmpty()) {
            req.execute("PF('dlg1').show();");
        }
    }

    public void reintegrarPrenda(Rentadeta rentadetalle) {
        if (detalleRentaEJB.reintegrarPrenda(rentadetalle)) {
            System.out.println("com.universitaria.ateliermaven.web.alquilerventa.AlquilerManagedBean.reintegrarPrenda()");
            detalleRenta.clear();
            setDetalleRenta(detalleRentaEJB.getRentaDetalleRenta(rentadetalle.getRentaId()));
            rentasActivas.clear();
            setRentasActivas(rentaEJB.getRentasActivas());
            if (detalleRenta.isEmpty()) {
                rentaEJB.reintegrarRenta(rentadetalle.getRentaId());
            }
        }
    }

    public void reintegrarTodo() {
        boolean error = false;
        for (Rentadeta rd : detalleRenta) {
            if (!detalleRentaEJB.reintegrarPrenda(rd)) {
                error = true;
            }
        }
        if (!error) {
            rentaEJB.reintegrarRenta(detalleRenta.get(0).getRentaId());
        }
        detalleRenta.clear();
    }

    public void verEntregarAlquiler(Reservacion reservacion) {
        RequestContext req = RequestContext.getCurrentInstance();

        setReservacionClienteActivas(reservaEJB.getReservacionClienteActivas(reservacion.getClienteId()));
        if (reservacionClienteActivas != null && !reservacionClienteActivas.isEmpty()) {
            req.execute("PF('dlg1').show();");
        }
    }

    public void entregarReservacionRenTa(Reservacion reservacion) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");

        if (reservaEJB.entregarReservacionRenTa(reservacion, user, valor)) {
            reservacionClienteActivas.clear();
            setReservacionClienteActivas(reservaEJB.getReservacionClienteActivas(reservacion.getClienteId()));
        }
        if (getReservacionClienteActivas().isEmpty()) {
            reservacionActivas.clear();
            setReservacionActivas(reservaEJB.getReservacionActivas());
            RequestContext req = RequestContext.getCurrentInstance();
            req.update(":form");
        }

    }

    public void liberarReservacion(Reservacion reservacion) {
        if (reservaEJB.liberarReservacion(reservacion)) {
            reservacionClienteActivas.clear();
            setReservacionClienteActivas(reservaEJB.getReservacionClienteActivas(reservacion.getClienteId()));
            reservacionActivas.clear();
            setReservacionActivas(reservaEJB.getReservacionActivas());
        }

    }

    public void alquilarTodo() {

        for (Reservacion r : reservacionClienteActivas) {
            entregarReservacionRenTa(r);
        }

    }

    public void entregarReservacionVenta(Reservacion reservacion) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");

        if (reservaEJB.entregarReservacionVenta(reservacion, user, valor)) {
            reservacionClienteActivas.clear();
            setReservacionClienteActivas(reservaEJB.getReservacionClienteActivas(reservacion.getClienteId()));
        }

        if (getReservacionClienteActivas().isEmpty()) {
            reservacionActivas.clear();
            setReservacionActivas(reservaEJB.getReservacionActivas());
            RequestContext req = RequestContext.getCurrentInstance();
            req.update(":form");
        }

    }

    public void venderTodo() {

        for (Reservacion r : reservacionClienteActivas) {
            entregarReservacionVenta(r);

        }

    }

    public void onTransfer(TransferEvent event) {
        for (Object item : event.getItems()) {
            prendaUtil = new PrendaUtil();
            prendaUtil.setPrendaNombre(((PrendaUtil) item).getPrendaNombre());
            prendaUtil.setPrendaId(((PrendaUtil) item).getPrendaId());
            prendaUtil.setValor("0.0");
        }
        for (PrendaUtil listaRenta : prendasSelect.getTarget()) {
            if (listaRenta.getPrendaId() == null ? listaRenta.getPrendaId() == null : listaRenta.getPrendaId().equals(prendaUtil.getPrendaId())) {
                setValor(0);
                RequestContext req = RequestContext.getCurrentInstance();
                req.execute("PF('dlg3').show();");
                break;
            }
        }
    }

    public void valorTotal() {
        for (PrendaUtil listaPrenda : prendasSelect.getTarget()) {
            if (listaPrenda.getPrendaId() == null ? prendaUtil.getPrendaId() == null : listaPrenda.getPrendaId().equals(prendaUtil.getPrendaId())) {
                listaPrenda.setValor(String.valueOf(valor));
                break;
            }
        }
        setValor(0);
        RequestContext req = RequestContext.getCurrentInstance();
        req.execute("PF('dlg3').hide();");
    }

    public void crearRenta() {
        RequestContext req = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");

        if (rentaEJB.setCrearRenta(prendasSelect.getTarget(), alquilarUtil, user)) {
            Comunes.mensaje("Se ha creado el alquiler correctamente", "");
        } else {
            Comunes.mensaje("Error creando el alquiler", "");
        }
        prendasSelect.getTarget().clear();
        prendasSelect.getSource().clear();
        setValor(0);
        req.update(":form");
        req.execute("PF('dlg2').hide();");
    }

    public void crearVenta() {
        RequestContext req = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");

        if (rentaEJB.setCrearVenta(prendasSelect.getTarget(), alquilarUtil, user)) {
            Comunes.mensaje("Se ha creado el alquiler correctamente", "");
        } else {
            Comunes.mensaje("Error creando el alquiler", "");
        }
        prendasSelect.getTarget().clear();
        prendasSelect.getSource().clear();
        setValor(0);
        req.update(":form");
        req.execute("PF('dlg2').hide();");
    }

}
