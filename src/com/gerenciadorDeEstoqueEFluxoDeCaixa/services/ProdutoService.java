package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class ProdutoService {

	private static EntityManagerFactory entityManagerFactory = EntityManagerFactoryService.entityManagerFactory();

	public static void adicionaProduto(Produto produto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(produto);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas em adicionar o produto" + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public static void atualizaProduto(Produto produto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.merge(produto);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();

			JOptionPane.showMessageDialog(null, "Problema na atuzalizaca do produto." + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public static void removeProduto(String codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			Produto produto = entityManager.find(Produto.class, codigo);

			if (produto != null) {
				entityManager.remove(produto);
				entityManager.getTransaction().commit();
			} else {
				JOptionPane.showMessageDialog(null, "Produto nao existe");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao remover produto" + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public static Produto retornaProdutoPorCodigo(String codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			return entityManager.find(Produto.class, codigo);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por produto" + e.getMessage());
			return null;

		} finally {
			entityManager.close();
		}
	}

	public static String geraRelatotioProdutos(Integer categoria) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Produto> produtos = entityManager
					.createQuery("SELECT p FROM Produto p WHERE p.categoria = :categoria", Produto.class)
					.setParameter("categoria", categoria).getResultList();

			StringBuilder sb = new StringBuilder();

			for (Produto elemento : produtos) {
				sb.append(elemento + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
		} finally {
			entityManager.close();
		}
		return "";
	}

	public static boolean tabelaProdutoEstaVazia() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		try {
			Long quantidade = entityManager.createQuery("SELECT COUNT(*) FROM Produto", Long.class).getSingleResult();

			if (quantidade == 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
			return false;
		} finally {
			entityManager.close();
		}

	}

	public static String geraRelatorioProdutosEstoqueBaixo() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Produto> produtos = entityManager
					.createQuery("SELECT p FROM Produto p WHERE p.quantidade <= 10", Produto.class).getResultList();

			StringBuilder sb = new StringBuilder();

			for (Produto produto : produtos) {
				sb.append(produto + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio de produtos com baixo estoque: " + e);
			return "";
		} finally {
			entityManager.close();
		}

	}

}