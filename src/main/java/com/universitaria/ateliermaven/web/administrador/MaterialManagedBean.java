/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.administrador;

import com.universitaria.atelier.web.jpa.Material;
import com.universitaria.ateliermaven.ejb.administrador.MaterialEJB;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author SoulHunter
 */
@Stateless
public class MaterialManagedBean {
    
    
    public MaterialManagedBean(){}
    
    private MaterialEJB materialEJB;
    private List<Material> material;
    private List<SelectItem> materialTipo;
    private List<SelectItem> marca;
    private String materiaNombre;
    private String materialReference;
    
    
    
    
    
}
