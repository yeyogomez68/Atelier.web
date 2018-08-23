package com.universitaria.ateliermaven.web.converter;

import com.universitaria.atelier.web.jpa.Material;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("materialPickListConverter")
public class MaterialPickListConverter implements Converter {

	

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component,value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object object) {
            if (object!= null) {
                return object.toString();
            }else {
                return null;
            }
	}

	@SuppressWarnings("unchecked")
	private Material getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<Material> dualList;
		try{
			dualList = (DualListModel<Material>) ((PickList)component).getValue();
			Material mat = getObjectFromList(dualList.getSource(),Integer.valueOf(value));
			if(mat==null){
				mat = getObjectFromList(dualList.getTarget(),Integer.valueOf(value));
			}
			
			return mat;
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}
	}

	private Material getObjectFromList(final List<?> list, final Integer identifier) {
		for(final Object object:list){
			final Material mat = (Material) object;
			if(mat.getMaterialId().equals(identifier)){
				return mat;
			}
		}
		return null;
	}
}