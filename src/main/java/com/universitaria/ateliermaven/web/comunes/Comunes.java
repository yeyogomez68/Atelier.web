/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.universitaria.ateliermaven.web.comunes;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author SoulHunter
 */
public class Comunes {

    private static FacesMessage message;

    public static synchronized void mensaje(String titulo, String descripcion) {

        if (message == null) {
            message = new FacesMessage(titulo, descripcion);
        } else {
            message.setSummary(titulo);
            message.setDetail(descripcion);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static synchronized String getFormat(String text) {
        return text.substring(0, 1).toUpperCase()
                + text.substring(1).toLowerCase();
    }

}
