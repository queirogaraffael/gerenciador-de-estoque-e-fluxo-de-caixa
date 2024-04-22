package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Categoria;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class CategoriaService {
	
	private static EntityManagerFactory entityManagerFactory = EntityManagerFactoryService.entityManagerFactory();

	public static List<Categoria> categorias() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			List<Categoria> categorias = entityManager.createQuery("FROM categorias", Categoria.class).getResultList();
			return categorias;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
		} finally {
			entityManager.close();
		}
		return null;

	}

	public static Categoria retornaCategoriaPeloId(Integer id) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			return entityManager.find(Categoria.class, id);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por categoria" + e.getMessage());
			return null;

		} finally {
			entityManager.close();
		}
	}

}
