package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;

public class ProdutoService {
	// Adicionar produtos
	public static void adicionarProduto(Set<Produto> produtos, Produto produto) {
		produtos.add(produto);
	}

	// remoção por código
	public static void removeProduto(Set<Produto> produtos, Integer codigo) {
		produtos.removeIf(p -> p.getCodigo().equals(codigo));
	}

	// edição
	public static void editaProduto(Produto produtoModificar, String novoNome, Double novoValor, int novaQuantidade) {
		produtoModificar.setNome(novoNome);
		produtoModificar.setValor(novoValor);
		produtoModificar.setQuantidade(null);
	}

	// edição. sobrecarga de metodo
	public static void editaProduto(Produto produto, int novaQuantidade) {
		produto.setQuantidade(novaQuantidade);
	}

	public static int quantidadeRealProduto(Set<Produto> listaCompras, Set<Produto> produtos, int codigo) {
		Produto prod = ComumProdutoVendaService.retornaPeloCodigo(listaCompras, codigo);
		Produto produtoEstoque = ComumProdutoVendaService.retornaPeloCodigo(produtos, codigo);

		return prod.getQuantidade() + produtoEstoque.getQuantidade();

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
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	public static void atualizaArquivoProdutos(String caminho, Set<Produto> produtos) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
			for (Produto p : produtos) {

				String resultadoFormatado = String.valueOf(p.getCodigo()) + "," + p.getNome() + ","
						+ String.valueOf(p.getValor()) + "," + String.valueOf(p.getQuantidade());

				bw.write(resultadoFormatado);
				bw.newLine();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	public static Double somaValores(Set<Produto> listaCompras) {
		double total = 0;

		for (Produto p : listaCompras) {
			total += p.getValor() * p.getQuantidade();
		}
		return total;
	}

}