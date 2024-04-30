package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.util.HashSet;
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

	public static ItemVenda retornaItemVendaPeloCodigo(Set<ItemVenda> listaCompras, String codigo) {
		for (ItemVenda p : listaCompras) {
			if (p.getProduto().getcodigoDeBarra().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

	public static String geraRelatorioItemVenda(Set<ItemVenda> itens) {

		StringBuilder sb = new StringBuilder();
		for (ItemVenda p : itens) {
			sb.append("Codigo: ").append(p.getProduto().getcodigoDeBarra()).append(", nome = ")
					.append(p.getProduto().getNome()).append(", Preco: ").append(p.getProduto().getpreco())
					.append(" R$").append(", Quantidade: ").append(p.getQuantidade()).append(", Total: ")
					.append(p.subTotal()).append("\n");

		}

		return sb.toString();
	}

	public static void adicionaItemVenda(ItemVenda itemVenda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(itemVenda);
			entityManager.getTransaction().commit();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas em adicionar item." + erro);
		} finally {
			entityManager.close();
		}

	}

	public static Set<ItemVenda> retornaItensVenda(Venda venda) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<ItemVenda> itens = entityManager
					.createQuery("SELECT p FROM ItemVenda p WHERE p.id.venda = :venda", ItemVenda.class)
					.setParameter("venda", venda).getResultList();

			Set<ItemVenda> itensVenda = new HashSet<>(itens);
			return itensVenda;

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao retornar itens venda." + erro);
			return null;
		} finally {
			entityManager.close();
		}

	}

}
