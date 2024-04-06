package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;

public class CaixaService {

	public static Random random = new Random();

	public static void adicionarProduto(Set<Produto> produtos, Set<Produto> listaCompras, Integer codigo) {
		for (Produto p : produtos) {
			if (p.getCodigo() == codigo) {
				listaCompras.add(p);
			}
		}
	}

	public static void listarCompras(Set<Produto> listaCompras) {

		StringBuilder sb = new StringBuilder();
		sb.append("Todos os produtos da sua sacola de compras:\n");

		double subtotal = 0;

		for (Produto p : listaCompras) {
			sb.append(p + "\n");
			subtotal += p.getValor() * p.getQuantidade();
		}
		sb.append(String.format("Subtotal: %.2f", subtotal));

		JOptionPane.showMessageDialog(null, sb);
	}

	public static boolean jaContemProduto(Set<Produto> listaCompras, Integer codigo) {
		boolean tarefaJaExiste = listaCompras.stream().anyMatch(p -> p.getCodigo() == codigo);
		return tarefaJaExiste;

	}

	public static Produto retornaProdutoPeloCodigo(Set<Produto> produtos, Integer codigo) {
		for (Produto p : produtos) {
			if (p.getCodigo() == codigo) {
				return p;
			}
		}
		return null;
	}

	public static Venda retornaVendaPeloCodigo(Set<Venda> vendas, Integer codigo) {
		for (Venda p : vendas) {
			if (p.getCodigo() == codigo) {
				return p;
			}
		}
		return null;
	}

	public static int geradorDeCodigoVenda(Set<Integer> codigosVendas) {

		int numero = random.nextInt(9000) + 1000;

		while (!codigosVendas.contains(numero) && !codigosVendas.isEmpty()) {
			numero = random.nextInt(9000) + 1000;
		}

		return numero;
	}

	public static void geradorNotaFiscal(Venda venda, String caminho) {
		String codigo = String.valueOf(venda.getCodigo()) + ".txt";

		StringBuilder sb = new StringBuilder();
		sb.append("Código venda: " + venda.getCodigo() + "\n");
		for (Produto p : venda.getProdutos()) {
			sb.append(p + "\n");
		}
		sb.append(venda);

		File diretorio = new File(caminho);

		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}

		File arquivo = new File(diretorio, codigo);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
			bw.write(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
