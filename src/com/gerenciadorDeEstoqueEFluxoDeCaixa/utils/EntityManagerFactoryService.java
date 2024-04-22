package com.gerenciadorDeEstoqueEFluxoDeCaixa.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryService {

	private static EntityManagerFactory entityManagerFactory;

	public static EntityManagerFactory entityManagerFactory() {

		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		}
		return entityManagerFactory;
	}

	public static void inicializarEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		}
	}

	// fechar o entityManager
	public static void fecharEntityManagerFactory() {

		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

}
