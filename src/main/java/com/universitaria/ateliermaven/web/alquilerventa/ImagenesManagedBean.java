/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.alquilerventa;

import com.universitaria.atelier.web.jpa.Renta;
import com.universitaria.atelier.web.jpa.Rentadeta;
import com.universitaria.ateliermaven.ejb.alquilerventas.DetalleRentaEJB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author SoulHunter
 */
public class ImagenesManagedBean {

    @EJB
    private DetalleRentaEJB detalleRentaEJB;

    private Rentadeta rentadeta;

    public DetalleRentaEJB getDetalleRentaEJB() {
        return detalleRentaEJB;
    }

    public void verImagenDetalleRenta(Rentadeta renta) {
        RequestContext req = RequestContext.getCurrentInstance();
        this.rentadeta = renta;
        req.execute("PF('dlg2').show();");
    }

    public void setDetalleRentaEJB(DetalleRentaEJB detalleRentaEJB) {
        this.detalleRentaEJB = detalleRentaEJB;
    }

    public StreamedContent dynamicImageController() {
        StreamedContent sc = null;
        try {
            RequestContext req = RequestContext.getCurrentInstance();
            File f = detalleRentaEJB.traerImagenesPorRenta(rentadeta);
            sc = new DefaultStreamedContent(new FileInputStream(f), "image/jpg", f.getName());
            req.update(":imagesDialog");
        } catch (FileNotFoundException ex) {
            System.out.println("com.universitaria.ateliermaven.web.alquilerventa.ImagenesManagedBean.dynamicImageController()");
            ex.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sc;

    }

    private StreamedContent image;

    public StreamedContent getImage() {
        image = dynamicImageController();
        return image;
    }

}
