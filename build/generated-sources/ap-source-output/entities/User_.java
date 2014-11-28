package entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Integer> iduser;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Employer> employer;
	public static volatile SingularAttribute<User, Applicant> applicant;
	public static volatile SingularAttribute<User, Role> role;
	public static volatile SingularAttribute<User, String> password;

}

