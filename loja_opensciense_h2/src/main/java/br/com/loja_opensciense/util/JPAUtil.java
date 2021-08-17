package br.com.loja_opensciense.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("loja_opensciense_h2");

	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();

	}

}
