/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import dao.VacancyDAO;
import entities.Vacancy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author Daryna_Ragimova
 */
@Named
@SessionScoped
public class LazyVacancyDataModel extends LazyDataModel<Vacancy> {

    private List<Vacancy> data = new ArrayList<Vacancy>();
    @Inject
    private VacancyDAO vacancyDAO;

    @Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    @Override
    public Vacancy getRowData(String rowKey) {
        for (Vacancy vacancy : data) {
            if (String.valueOf(vacancy.getIdvacancy()).equals(rowKey)) {
                return vacancy;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Vacancy vacancy) {
        return vacancy.getIdvacancy();
    }

    @Override
    public List<Vacancy> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            String filterValue = filters.get(filterProperty);

            if (filterValue != null && filterProperty != null) {
                if (filterProperty.equals("name")) {
                    vacancyDAO.addFilterVacancy(filterValue);
                } else if (filterProperty.equals("salary")) {
                    vacancyDAO.addFilterSalary(Double.valueOf(filterValue));
                } else {
                    vacancyDAO.addFilterCity(filterValue);
                }
            }
        }
        int dataSize = vacancyDAO.findByFilters();

        //sort
        if (sortField != null) {
           vacancyDAO.addSorter(sortField, sortOrder);
        }

        //paginate
        data = vacancyDAO.find(pageSize, first);
        this.setRowCount(dataSize);
        return data;

    }
}
