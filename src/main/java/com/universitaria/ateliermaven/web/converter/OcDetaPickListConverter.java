package com.universitaria.ateliermaven.web.converter;

import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.jpa.Ordencompradeta;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import com.universitaria.atelier.web.utils.OrdenCompraDetaUtil;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("ocDetaPickListConverter")
public class OcDetaPickListConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            String[] item = value.split(";");
            OrdenCompraDetaUtil ocDeta = new OrdenCompraDetaUtil();
            ocDeta.setEncabezado(item[0]);
            ocDeta.setMaterial(item[1]);
            ocDeta.setMaterialId(item[2]);
            ocDeta.setCatidad(item[3]);
            ocDeta.setValorUnitario(item[4]);
            ocDeta.setValorBruto(item[5]);
            ocDeta.setValorIva(item[6]);
            ocDeta.setValorTotal(item[7]);
            return ocDeta;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            OrdenCompraDetaUtil ocDeta = (OrdenCompraDetaUtil) object;
            StringBuffer buf = new StringBuffer(String.valueOf(ocDeta.getEncabezado()))
                    .append(";")
                    .append(ocDeta.getMaterial())
                    .append(";")
                    .append(ocDeta.getMaterialId())
                    .append(";")
                    .append(ocDeta.getCatidad())
                    .append(";")
                    .append(ocDeta.getValorUnitario())
                    .append(";")
                    .append(ocDeta.getValorBruto())
                    .append(";")
                    .append(ocDeta.getValorIva())
                    .append(";")
                    .append(ocDeta.getValorTotal());
            String detalle = buf.toString();
            return detalle;
        } else {
            return null;
        }
    }
}
