package entities;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Employer.class)
public abstract class Employer_ {

	public static volatile SingularAttribute<Employer, Integer> idemployer;
	public static volatile SingularAttribute<Employer, String> description;
	public static volatile SingularAttribute<Employer, String> name;
	public static volatile SetAttribute<Employer, Vacancy> vacancies;
	public static volatile SingularAttribute<Employer, User> user;

}

