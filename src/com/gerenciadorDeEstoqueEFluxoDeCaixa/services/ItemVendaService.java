package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.ItemVenda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class ItemVendaService {

	private static EntityManagerFactory entityManagerFactory = EntityManagerFactoryService.entityManagerFactory();

	public static boolean contemProduto(Set<ItemVenda> listaCompras, String codigo) {
		boolean JaExiste = listaCompras.stream().anyMatch(p -> p.getProduto().getcodigoDeBarra().equals(codigo));
		return JaExiste;
	}

	public static Double somaPrecos(Set<ItemVenda> listaCompras) {
		double total = 0;

		for (ItemVenda p : listaCompras) {
			total += p.getProduto().getpreco() * p.getQuantidade();
		}
		return total;
	}

	public static void removeProduto(Set<ItemVenda> listaCompras, String codigo) {

		listaCompras.removeIf(p -> p.getProduto().getcodigoDeBarra().equals(codigo));
	}

	public static boolean jaContem(Set<ItemVenda> listaCompras, String codigo) {
		boolean JaExiste = listaCompras.stream().anyMatch(p -> p.getProduto().getcodigoDeBarra().equals(codigo));
		return JaExiste;
	}

	public static ItemVenda retornaItemVendaPeloCodigo(Set<ItemVenda> listaCompras, String codigo) {
		for (ItemVenda p : listaCompras) {
			if (p.getProduto().getcodigoDeBarra().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

	public static String imprime(Set<ItemVenda> listaCompras) {

		StringBuilder sb = new StringBuilder();

		for (ItemVenda p : listaCompras) {
			sb.append("Codigo: " + p.getProduto().getcodigoDeBarra() + ", nome = " + p.getProduto().getNome()
					+ ", Quantidade: " + String.format("%d, ", p.getQuantidade()) + "Total: "
					+ String.format("%.2f", p.subTotal()) + "\n");

		}

		return sb.toString();
	}

	public static void adicionaItemVenda(ItemVenda itemVenda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(itemVenda);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas em adicionar item" + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public static List<ItemVenda> retornaItensVenda(Venda venda) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<ItemVenda> itens = entityManager
					.createQuery("SELECT p FROM ItemVenda p WHERE p.id.venda = :venda", ItemVenda.class)
					.setParameter("venda", venda).getResultList();

			return itens;

		} catch (Exception e) {
			System.out.println("Erro: " + e);
			JOptionPane.showMessageDialog(null, "Erro: " + e);
			return null;
		} finally {
			entityManager.close();
		}

	}

}
