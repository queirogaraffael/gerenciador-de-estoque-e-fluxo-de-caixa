package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;

public class ProdutoService {
	// Adicionar produtos
	public static void adicionarProduto(Set<Produto> produtos, Produto produto) {
		produtos.add(produto);
	}

	// edição
	public static void editarProdutoCompleto(Set<Produto> produtos, Integer codigo, String novoNome, Double novoValor,
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

	public static void editarProdutoQuantidade(Set<Produto> produtos, Integer codigo, int novaQuantidade) {
		for (Produto p : produtos) {
			if (p.getCodigo().equals(codigo)) {
				p.setQuantidade(novaQuantidade);
				break;
			}
		}
	}

	// remoção por código
	public static void removerProduto(Set<Produto> produtos, Integer codigo) {
		produtos.removeIf(p -> p.getCodigo().equals(codigo));
	}

	public static void adicionaProdutoPorArquivo(String caminho, Set<Produto> listaProdutos) {

		try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
			String line = br.readLine();
			while (line != null) {

				String[] atributos = line.split(",");

				Integer codigo = Integer.parseInt(atributos[0]);
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