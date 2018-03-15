/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;



import com.universitaria.atelier.web.jpa.Estado;
import com.universitaria.ateliermaven.ejb.administrador.EstadoEJB;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author jeisson.gomez
 */
public class EstadoManagedBean {
    
    @EJB
    private EstadoEJB estadoEJB;
    
    private List<Estado> estados;
    
    
    private String nomEstado;
    /**
     * Creates a new instance of EstadoManagedBean
     */
    public EstadoManagedBean() {
    }

    public String getNomEstado() {
        return nomEstado;
    }

    public void setNomEstado(String NomEstado) {
        this.nomEstado = nomEstado;
    }
    
    public List<Estado> getEstados() {        
        if(estados==null || estados.isEmpty()){
            estados = new ArrayList<>();
        }else{
            estados.clear();
            setEstados(estadoEJB.getEstados());
        }        
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }  
}
