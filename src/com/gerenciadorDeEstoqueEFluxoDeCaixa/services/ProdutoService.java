package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;

public class ProdutoService {
	// Adicionar produtos
	public static void adicionarProduto(Set<Produto> produtos, Produto produto) {
		produtos.add(produto);
	}

	// edição
	public static void editarProdutoCompleto(Set<Produto> produtos, String codigo, String novoNome, Double novoValor,
			int novaQuantidade) {
		for (Produto p : produtos) {
			if (p.getCodigo().equals(codigo)) {
				p.setNome(novoNome);
				p.setValor(novoValor);
				p.setQuantidade(novaQuantidade);
				break;
			}
		}
	}

	public static void editarProdutoQuantidade(Set<Produto> produtos, String codigo, int novaQuantidade) {
		for (Produto p : produtos) {
			if (p.getCodigo().equals(codigo)) {
				p.setQuantidade(novaQuantidade);
				break;
			}
		}
	}

	// listagem de produtos
	public static void visualizarProduto(Set<Produto> produtos) {
		StringBuilder sb = new StringBuilder();
		sb.append("Listagem de produtos: \n");
		
		for (Produto p : produtos) {
			sb.append(p + "\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	// remoção por código
	public static void removerProduto(Set<Produto> tarefas, String codigo) {
		tarefas.removeIf(p -> p.getCodigo().equals(codigo));
	}

	public static boolean jaContemProduto(Set<Produto> produtos, String codigo) {
		boolean tarefaJaExiste = produtos.stream().anyMatch(p -> p.getCodigo().equals(codigo));
		return tarefaJaExiste;

	}

	public static void adicionaProdutoPorArquivo(String caminho, Set<Produto> listaProdutos) {

		try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
			String line = br.readLine();
			while (line != null) {

				String[] atributos = line.split(",");

				String codigo = atributos[0];
				String nome = atributos[1];
				Double valor = Double.parseDouble(atributos[2]);
				Integer quantidade = Integer.parseInt(atributos[3]);

				listaProdutos.add(new Produto(codigo, nome, valor, quantidade));

				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}