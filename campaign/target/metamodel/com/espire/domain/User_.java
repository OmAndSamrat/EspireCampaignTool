package com.espire.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile ListAttribute<User, Role> userRoles;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, String> pwd;
	public static volatile SingularAttribute<User, Long> userID;

}

