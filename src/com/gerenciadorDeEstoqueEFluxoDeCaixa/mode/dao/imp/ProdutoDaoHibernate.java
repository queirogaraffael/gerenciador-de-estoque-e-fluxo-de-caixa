package com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.ProdutoDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.Produto;

public class ProdutoDaoHibernate implements ProdutoDao {

	private EntityManagerFactory entityManagerFactory;

	public ProdutoDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void adicionaProduto(Produto produto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(produto);
			entityManager.getTransaction().commit();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas em adicionar o produto" + erro);
		} finally {
			entityManager.close();
		}

	}

	public void atualizaProduto(Produto produto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.merge(produto);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			entityManager.getTransaction().rollback();
			JOptionPane.showMessageDialog(null, "Problema na atualizacao do produto." + erro);
		} finally {
			entityManager.close();
		}

	}

	public void removeProduto(String codigo) {
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

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas ao remover produto" + erro);
		} finally {
			entityManager.close();
		}

	}

	public Produto retornaProdutoPorCodigo(String codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			return entityManager.find(Produto.class, codigo);

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por produto" + erro);
			return null;

		} finally {
			entityManager.close();
		}
	}

	public String geraRelatotioProdutos(Integer categoria) {

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

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar gerar relatorio dos produtos: " + erro);
		} finally {
			entityManager.close();
		}
		return "";
	}

	public boolean tabelaProdutoEstaVazia() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		try {
			Long quantidade = entityManager.createQuery("SELECT COUNT(*) FROM Produto", Long.class).getSingleResult();

			if (quantidade == 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tabela de produtos esta vazia: " + erro);
			return false;
		} finally {
			entityManager.close();
		}

	}

	public String geraRelatorioProdutosEstoqueBaixo() {

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

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio de produtos com baixo estoque: " + erro);
			return "";
		} finally {
			entityManager.close();
		}

	}

}