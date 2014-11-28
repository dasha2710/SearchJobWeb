/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.CityDAO;
import entities.City;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.SortOrder;
import sorter.LazySorter;


/**
 *
 * @author Daryna_Ragimova
 */
@ManagedBean
@RequestScoped
public class CityBean { 
    @EJB
    private CityDAO cityDAO;
    
        
    /**
     * Creates a new instance of CityBean
     */
    public CityBean() {
    }
    
    public List<City> findCities(){
        List<City> cities = cityDAO.findCityEntities();
        Collections.sort(cities, new LazySorter<City>("name",SortOrder.ASCENDING));
        return cities;
    }
}
