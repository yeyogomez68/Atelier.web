/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.compras;

import com.universitaria.atelier.web.jpa.Ordencompra;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.ateliermaven.ejb.compras.OrdenCompraEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author jeisson.gomez
 */
public class OrdenCompraBean {

    @EJB
    private OrdenCompraEJB ordenCompraEJB;
    private List<Ordencompra> listOrdenesCompra;
    private Usuario user;
    /**
     * Creates a new instance of OrdenCompraBean
     */
    public OrdenCompraBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        user = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
    }
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Ordencompra> getListOrdenesCompra() {
        return listOrdenesCompra;
    }

    public void setListOrdenesCompra(List<Ordencompra> listOrdenesCompra) {
        if(listOrdenesCompra == null || listOrdenesCompra.isEmpty()){            
            listOrdenesCompra = new ArrayList<>();
            setListOrdenesCompra(ordenCompraEJB.getOrdenesComprasByUser());
        }
        this.listOrdenesCompra = listOrdenesCompra;
    }
    
    public void verOrdenCompra (Ordencompra orCompra){
        
    }
    
    public void crearOrdenCompra(){
    
    }
}
