package entities;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Applicant.class)
public abstract class Applicant_ {

	public static volatile SingularAttribute<Applicant, Integer> idapplicant;
	public static volatile SingularAttribute<Applicant, Integer> age;
	public static volatile SingularAttribute<Applicant, String> name;
	public static volatile SingularAttribute<Applicant, String> surname;
	public static volatile SetAttribute<Applicant, Cv> cvs;
	public static volatile SingularAttribute<Applicant, String> education;
	public static volatile SingularAttribute<Applicant, User> user;
	public static volatile SingularAttribute<Applicant, City> city;

}

