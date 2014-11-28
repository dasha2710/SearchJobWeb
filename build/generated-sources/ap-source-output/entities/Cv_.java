package entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Cv.class)
public abstract class Cv_ {

	public static volatile SingularAttribute<Cv, String> skills;
	public static volatile SingularAttribute<Cv, String> vacancy;
	public static volatile SingularAttribute<Cv, Boolean> status;
	public static volatile SingularAttribute<Cv, Applicant> applicant;
	public static volatile SingularAttribute<Cv, Integer> idcv;
	public static volatile SingularAttribute<Cv, Integer> experience;
	public static volatile SingularAttribute<Cv, Double> salary;

}

