package entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Vacancy.class)
public abstract class Vacancy_ {

	public static volatile SingularAttribute<Vacancy, String> skills;
	public static volatile SingularAttribute<Vacancy, Boolean> status;
	public static volatile SingularAttribute<Vacancy, String> name;
	public static volatile SingularAttribute<Vacancy, Employer> employer;
	public static volatile SingularAttribute<Vacancy, Integer> experience;
	public static volatile SingularAttribute<Vacancy, Double> salary;
	public static volatile SingularAttribute<Vacancy, String> education;
	public static volatile SingularAttribute<Vacancy, Integer> idvacancy;
	public static volatile SingularAttribute<Vacancy, City> city;

}

