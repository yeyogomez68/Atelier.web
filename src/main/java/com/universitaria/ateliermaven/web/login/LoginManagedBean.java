/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.login;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import com.universitaria.atelier.web.jpa.Usuario;
import com.universitaria.ateliermaven.ejb.UsuarioEJB;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author jeisson.gomez
 */
public class LoginManagedBean implements Serializable {

    /**
     * Creates a new instance of LoginManagedBean
     */
    @EJB
    private UsuarioEJB usuarioEJB;
    
    private String userName;
    private String password;
    public LoginManagedBean() {        
    }
    
    public String iniciar() {
      Usuario usuario = null;
      if(getUserName() != null && getPassword() != null){  
          usuario = usuarioEJB.getAccess(getUserName(),getPassword());
            if(usuario!=null){   
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("user", usuario);                
                return "exito";
            }else{
                addMessage("Fallo de credenciales");
                return null;
            }                
        }else {
          addMessage("Fallo de credeciales");
          return null;
      }      
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword(){
    return password;
    }
    
    public void setPassword(String password){
    this.password = password;
    }
    
}
