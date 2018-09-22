/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.converter;

import com.universitaria.atelier.web.utils.PrendaUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author SoulHunter
 */
@FacesConverter("prendasPickListConverter")
public class PrendasPickListConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            String[] item = value.split(";");
            PrendaUtil prenda = new PrendaUtil();
            prenda.setPrendaId(item[0]);
            prenda.setPrendaNombre(item[1]);
            prenda.setPrendaDescripcion(item[2]);
            prenda.setColorId(item[3]);
            prenda.setOcasionId(item[4]);
            prenda.setPrendaTipoId(item[5]);
            prenda.setEstadoId(item[6]);
            prenda.setCantidad(item[7]);
            prenda.setUsuarioId(item[8]);
            prenda.setValor(item[9]);
            return prenda;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            PrendaUtil prenda = (PrendaUtil) object;
            StringBuffer buf = new StringBuffer(prenda.getPrendaId())
                    .append(";")
                    .append(prenda.getPrendaNombre())
                    .append(";")
                    .append(prenda.getPrendaDescripcion())
                    .append(";")
                    .append(prenda.getColorId())
                    .append(";")
                    .append(prenda.getOcasionId())
                    .append(";")
                    .append(prenda.getPrendaTipoId())
                    .append(";")
                    .append(prenda.getEstadoId())
                    .append(";")
                    .append(prenda.getCantidad())
                    .append(";")
                    .append(prenda.getUsuarioId())
                    .append(";")
                    .append(prenda.getValor());
            String pre = buf.toString();
            return pre;
        } else {
            return null;
        }
    }

}
