/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import dao.CityDAO; 
import entities.City;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author Daryna_Ragimova
 */
@ManagedBean(name = "cityConverterBean") 
@FacesConverter("cityConverter")
public class CityConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        try {
            Context ctx= new InitialContext();
            CityDAO cityDAO = (CityDAO)ctx.lookup("java:global/SearchJobWeb/CityDAO!dao.CityDAO");
            return cityDAO.findCity(value);
        } catch (NamingException ex) {
            Logger.getLogger(CityConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value instanceof City)
            return value.toString();
        else
            return null;
    }

}
