package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class VendaService {
	private static EntityManagerFactory entityManagerFactory = EntityManagerFactoryService.entityManagerFactory();

	public static void adicionaVenda(Venda venda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(venda);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas em adicionar a venda" + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public static void atualizaVenda(Venda venda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.merge(venda);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas em atualizar venda" + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public static Venda retornaVendaPorCodigo(Integer codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			return entityManager.find(Venda.class, codigo);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por venda" + e.getMessage());
			return null;

		} finally {
			entityManager.close();
		}
	}

	public static String geraRelatioVendas() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Venda> vendas = entityManager.createQuery("FROM Venda", Venda.class).getResultList();

			StringBuilder sb = new StringBuilder();

			for (Venda venda : vendas) {
				sb.append(venda + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
			return "";
		} finally {
			entityManager.close();
		}

	}

	public static boolean tabelaVendaEstaVazia() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			Long quantidade = entityManager.createQuery("SELECT COUNT(*) FROM Venda", Long.class).getSingleResult();
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

	public static String geraRelatiorioVendasPorData(LocalDate data) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Venda> vendas = entityManager
					.createQuery("SELECT p FROM Venda p WHERE CAST(p.dataHora AS date) = :data", Venda.class)
					.setParameter("data", Date.valueOf(data)).getResultList();

			Double total = 0.0;

			StringBuilder sb = new StringBuilder();
			for (Venda venda : vendas) {
				sb.append(venda + "\n");
				total += venda.getTotal();
			}

			sb.append("Total: ").append(total);
			return sb.toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro em buscar vendas por data: " + e);
			return "";
		} finally {
			entityManager.close();
		}

	}

}
