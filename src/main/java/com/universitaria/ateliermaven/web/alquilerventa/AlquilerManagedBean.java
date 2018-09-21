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
import com.universitaria.ateliermaven.ejb.alquilerventas.DetalleRentaEJB;
import com.universitaria.ateliermaven.ejb.alquilerventas.RentaEJB;
import com.universitaria.ateliermaven.ejb.alquilerventas.ReservaEJB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author SoulHunter
 */
public class AlquilerManagedBean implements Serializable {

    @EJB
    private RentaEJB rentaEJB;
    @EJB
    private DetalleRentaEJB detalleRentaEJB;
    @EJB
    private ReservaEJB reservaEJB;

    private List<Renta> rentasActivas;
    private List<Rentadeta> detalleRenta;
    private List<Reservacion> reservacionActivas;
    private List<Reservacion> reservacionClienteActivas;

    private int valor;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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
            detalleRenta.clear();
            setDetalleRenta(detalleRentaEJB.getRentaDetalleRenta(rentadetalle.getRentaId()));

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

    }

    public void liberarReservacion(Reservacion reservacion) {
        if (reservaEJB.liberarReservacion(reservacion)) {
            reservacionClienteActivas.clear();
            setReservacionClienteActivas(reservaEJB.getReservacionClienteActivas(reservacion.getClienteId()));
        }

    }

}
