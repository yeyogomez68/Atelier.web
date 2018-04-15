/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;


import com.universitaria.atelier.web.jpa.Prenda;
import com.universitaria.ateliermaven.ejb.administrador.PrendaEJB;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Jeisson Gomez
 */
public class PrendaManagedBean {

    /**
     * Creates a new instance of PrendaManagedBean
     */
    public PrendaManagedBean() {
    }
    
    @EJB
    private PrendaEJB prendaEJB;
    
    private List<Prenda> prendas;
    
    
    
    
    public List<Prenda> getPrendas() {        
        if(prendas==null || prendas.isEmpty()){
            prendas = new ArrayList<>();           
        }else{
            prendas.clear();           
        }      
        setPrendas(prendaEJB.getPrendas());
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }  
}
