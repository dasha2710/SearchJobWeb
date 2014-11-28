/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazyDataModel;

import dao.CvDAO;
import entities.Cv;
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
public class LazyCVDataModel extends LazyDataModel<Cv> {

    private List<Cv> data = new ArrayList<Cv>();
    @Inject
    private CvDAO cvDAO;

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
    public Cv getRowData(String rowKey) {
        for (Cv cv : data) {
            if (String.valueOf(cv.getIdcv()).equals(rowKey)) {
                return cv;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Cv cv) {
        return cv.getIdcv();
    }

    @Override
    public List<Cv> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {

        for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            String filterValue = filters.get(filterProperty);

            if (filterValue != null && filterProperty != null) {
                if (filterProperty.equals("vacancy")) {
                    cvDAO.addFilterVacancy(filterValue);
                } else if (filterProperty.equals("salary")) {
                    cvDAO.addFilterSalary(Double.valueOf(filterValue));
                } else {
                    cvDAO.addFilterCity(filterValue);
                }
            }
        }
        int dataSize = cvDAO.findByFilters();
        //sort
        if (sortField != null) {
            cvDAO.addSorter(sortField, sortOrder);
        }
        data = cvDAO.find(pageSize, first);
        this.setRowCount(dataSize);
        return data;
    }
}
