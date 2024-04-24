package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Categoria;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class CategoriaService {

	private static EntityManagerFactory entityManagerFactory = EntityManagerFactoryService.entityManagerFactory();

	public static Object[] categorias() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			List<Categoria> categorias = entityManager.createQuery("FROM Categoria", Categoria.class).getResultList();

			Object[] ArrayCategorias = new Object[categorias.size()];

			for (int i = 0; i < categorias.size(); i++) {
				ArrayCategorias[i] = categorias.get(i).toString();
			}

			return ArrayCategorias;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
		} finally {
			entityManager.close();
		}
		return null;

	}

	public static Integer retornaIdCategoria() {

		Object[] categorias = CategoriaService.categorias();

		Object resultadoCategoria = JOptionPane.showInputDialog(null, "Escolha uma categoria", "Categorias",
				JOptionPane.INFORMATION_MESSAGE, null, categorias, categorias[0]);

		String[] categoriaDado = resultadoCategoria.toString().split(" - ");

		int categoria = Integer.parseInt(categoriaDado[0]);
		return categoria;
	}

}
