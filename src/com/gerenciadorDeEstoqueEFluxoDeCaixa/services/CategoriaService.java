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

	public static void adicionarCategoriasSeNaoTiverAinda() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			List<Categoria> categorias = entityManager.createQuery("FROM Categoria", Categoria.class).getResultList();

			if (categorias.isEmpty()) {

				Categoria categoria1 = new Categoria(1, "Alimentos e Bebidas");
				Categoria categoria2 = new Categoria(2, "Produtos de Limpeza");
				Categoria categoria3 = new Categoria(3, "Higiene Pessoal");
				Categoria categoria4 = new Categoria(4, "Eletrônicos");
				Categoria categoria5 = new Categoria(5, "Roupas e Acessórios");
				Categoria categoria6 = new Categoria(6, "Móveis e Decoração");
				Categoria categoria7 = new Categoria(7, "Ferramentas e Equipamentos");
				Categoria categoria8 = new Categoria(8, "Livros e Materiais de Escritório");
				Categoria categoria9 = new Categoria(9, "Saúde e Bem-Estar");
				Categoria categoria10 = new Categoria(10, "Automotivo");
				Categoria categoria11 = new Categoria(11, "Outra");

				entityManager.persist(categoria1);
				entityManager.persist(categoria2);
				entityManager.persist(categoria3);
				entityManager.persist(categoria4);
				entityManager.persist(categoria5);
				entityManager.persist(categoria6);
				entityManager.persist(categoria7);
				entityManager.persist(categoria8);
				entityManager.persist(categoria9);
				entityManager.persist(categoria10);
				entityManager.persist(categoria11);

				entityManager.getTransaction().commit();

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
		} finally {
			entityManager.close();
		}

	}

}
