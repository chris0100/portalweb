/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portalweb.controllers;

import com.portalweb.entities.T002Rol;
import com.portalweb.sessions.T002RolFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Lccabalr
 */
@Named(value = "rolController")
@SessionScoped
public class RolController implements Serializable {

    @EJB
    private T002RolFacade rolFacade;
    
    public RolController() {
    }

    public T002RolFacade getRolFacade() {
        return rolFacade;
    }

    public void setRolFacade(T002RolFacade rolFacade) {
        this.rolFacade = rolFacade;
    }
     
    public List<T002Rol> getItemsAvailableSelectOne() {
        return getRolFacade().consultaRol();
    }
    public List<T002Rol> getListaRolCalidad() {
        return getRolFacade().consultaRolCalidad();
    }
     public T002Rol getRoles (java.lang.String id) {
        return getRolFacade().find(id);
    }
     @FacesConverter(forClass = T002Rol.class, value="rolConverter")
    public static class RolesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolController controller = (RolController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolController");
            return controller.getRoles(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof T002Rol) {
                T002Rol o = (T002Rol) object;
                return getStringKey(o.getIdRol());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + T002Rol.class.getName());
            }
        }

    }
    
}
