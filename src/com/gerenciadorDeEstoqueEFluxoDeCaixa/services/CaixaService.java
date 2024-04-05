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

	public static void adicionarProduto(Set<Produto> produtos, Set<Produto> listaCompras, String codigo) {
		for (Produto p : produtos) {
			if (p.getCodigo().equals(codigo)) {
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

	public static boolean jaContemProduto(Set<Produto> listaCompras, String codigo) {
		boolean tarefaJaExiste = listaCompras.stream().anyMatch(p -> p.getCodigo().equals(codigo));
		return tarefaJaExiste;

	}

	public static Produto retornaProdutoPeloCodigo(Set<Produto> produtos, String codigo) {
		for (Produto p : produtos) {
			if (p.getCodigo().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

	public static Venda retornaVendaPeloCodigo(Set<Venda> vendas, String codigo) {
		for (Venda p : vendas) {
			if (p.getCodigo().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

	public static int geradorDeCodigoVenda(Set<Integer> codigosVendas) {

		int numero = random.nextInt(9000) + 1000; // um numero de 4 digitos

		while (!codigosVendas.contains(numero) && !codigosVendas.isEmpty()) {
			numero = random.nextInt(9000) + 1000;
		}

		return numero;
	}

	public static void geradorNotaFiscal(Venda venda) {

		StringBuilder sb = new StringBuilder();
		sb.append("Código venda: " + venda.getCodigo() + "\n");
		for (Produto p : venda.getProdutos()) {
			sb.append(p + "\n");
		}
		sb.append(venda);

		String diretorioPrograma = System.getProperty("user.dir");
		String nomeNovaPasta = "notasFiscais";
		String caminhoNovaPasta = diretorioPrograma + File.separator + nomeNovaPasta;
		File novaPasta = new File(caminhoNovaPasta);
		
		
		if (!novaPasta.exists()) {
			novaPasta.mkdir();
		}
		
		novaPasta.setWritable(true);
		

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(novaPasta))) {
			bw.write(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
