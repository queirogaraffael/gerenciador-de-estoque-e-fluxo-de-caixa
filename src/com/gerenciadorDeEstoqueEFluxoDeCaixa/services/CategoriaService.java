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

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro em recuperar categorias: " + erro);
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

	public static void adicionarCategoriasSeNaoTiverAinda() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			List<Categoria> categorias = entityManager.createQuery("FROM Categoria", Categoria.class).getResultList();

			if (categorias.isEmpty()) {

				String[] nomesCategorias = { "Alimentos e Bebidas", "Produtos de Limpeza", "Higiene Pessoal",
						"Eletrônicos", "Roupas e Acessórios", "Móveis e Decoração", "Ferramentas e Equipamentos",
						"Livros e Materiais de Escritório", "Saúde e Bem-Estar", "Automotivo", "Outra" };

				for (int i = 1; i <= nomesCategorias.length; i++) {
					Categoria categoria = new Categoria(i, nomesCategorias[i - 1]);
					entityManager.persist(categoria);
				}
				entityManager.getTransaction().commit();

			}

		} catch (Exception erro) {
			entityManager.getTransaction().rollback();
			JOptionPane.showMessageDialog(null, "Erro ao adicionar categorias" + erro);
		} finally {
			entityManager.close();
		}

	}

}
