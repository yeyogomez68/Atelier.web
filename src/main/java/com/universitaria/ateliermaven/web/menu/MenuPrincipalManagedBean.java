/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.menu;

import com.universitaria.atelier.web.jpa.Opcion;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.ateliermaven.ejb.menu.OpcionEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author jeisson.gomez
 */
public class MenuPrincipalManagedBean implements Serializable{
    
    private MenuModel model;
    private Usuario user;
    @EJB
    private OpcionEJB opcionEJB;
    
    private List<Opcion> listOpcionModulos;
    private List<Opcion> listOpcionItems;

    public MenuPrincipalManagedBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
    }
    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        listOpcionModulos = new ArrayList<>();
        listOpcionItems = new ArrayList<>();
        
        listOpcionModulos = opcionEJB.getOpcionesByPerm(1,user.getRollId().getRollId());
        listOpcionItems = opcionEJB.getOpcionesByPerm(2,user.getRollId().getRollId());
        
        for (Opcion opciModulos : listOpcionModulos) {
            DefaultSubMenu mod = new DefaultSubMenu(opciModulos.getFormulario());            
            for (Opcion opciItems : listOpcionItems) {
                if(opciItems.getOpcionPadre()==opciModulos.getOpcionId()){
                    DefaultMenuItem item = new DefaultMenuItem();
                    item = new DefaultMenuItem(opciItems.getFormulario());
                    item.setOutcome(opciItems.getUrl());
                    mod.addElement(item);
                }
                
            }
            model.addElement(mod);
        }
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    
    
    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    
}
