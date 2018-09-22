package com.universitaria.ateliermaven.web.converter;

import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.atelier.web.utils.MaterialRequerimientoUtil;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("materialPickListConverter")
public class MaterialPickListConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            String[] item = value.split(";");
            MaterialRequerimientoUtil mat = new MaterialRequerimientoUtil();
            mat.setMaterialId(item[0]);
            mat.setNombre(item[1]);
            mat.setMarcaId(item[2]);
            mat.setReferencia(item[3]);
            mat.setTipoId(item[4]);
            mat.setCantidad(item[5]);
            mat.setUsuarioId(item[6]);
            return mat;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            MaterialRequerimientoUtil mat = (MaterialRequerimientoUtil) object;
            StringBuffer buf = new StringBuffer(mat.getMaterialId())
                    .append(";")
                    .append(mat.getNombre())
                    .append(";")
                    .append(mat.getMarcaId())
                    .append(";")
                    .append(mat.getReferencia())
                    .append(";")
                    .append(mat.getTipoId())
                    .append(";")
                    .append(mat.getCantidad())
                    .append(";")
                    .append(mat.getUsuarioId());
            String materia = buf.toString();
            return materia;
        } else {
            return null;
        }
    }
}
