package entities;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SetAttribute<City, Applicant> applicants;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, Integer> idcity;
	public static volatile SetAttribute<City, Vacancy> vacancies;

}

