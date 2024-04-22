package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.ItemVenda;
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
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Problemas em adicionar o produto" + e.getMessage());
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

	public static String imprimeVendas() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Venda> vendas = entityManager.createQuery("FROM Venda", Venda.class).getResultList();

			StringBuilder sb = new StringBuilder();

			for (Venda elemento : vendas) {
				sb.append(elemento + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
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
			List<Venda> vendas = entityManager.createQuery("FROM Venda", Venda.class).getResultList();
			return vendas.isEmpty();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e);
		} finally {
			entityManager.close();
		}
		return false;

	}

	public static void geradorNotaFiscal(Venda venda, String caminho) {
		String codigo = String.valueOf(venda.getCodigo()) + ".txt";

		StringBuilder sb = new StringBuilder();

		sb.append("Codigo venda: ").append(venda.getCodigo()).append(" , Data: ").append(venda.getInstant())
				.append("\n");

		List<ItemVenda> itensVenda = ItemVendaService.retornaItensVenda(venda);

		for (ItemVenda p : itensVenda) {
			sb.append("Codigo: " + p.getProduto().getcodigoDeBarra() + ", nome = " + p.getProduto().getNome()
					+ ", Quantidade: " + String.format("%d, ", p.getQuantidade()) + "Total: "
					+ String.format("%.2f", p.subTotal()) + "\n");

		}

		double total = 0;
		for (ItemVenda p : itensVenda) {
			total += p.subTotal();
		}

		sb.append("Total: ").append(total);

		File diretorio = new File(caminho);

		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}

		File arquivo = new File(diretorio, codigo);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
			bw.write(sb.toString());

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Diretorio não encontrado: " + caminho);
		}

	}
}
