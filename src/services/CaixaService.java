package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import entities.Produto;
import entities.Venda;

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
			subtotal += p.getValor();
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

	public static String geradorDeCodigoVenda(Set<String> listaCodigos) {

		String numero = String.valueOf(random.nextInt(9000) + 1000); // um numero de 4 digitos

		while (!listaCodigos.contains(numero) && !listaCodigos.isEmpty()) {
			numero = String.valueOf(random.nextInt(9000) + 1000);
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

		String diretorioAtual = System.getProperty("user.dir");
		boolean ok = new File(diretorioAtual + "\\notasfiscais").mkdir();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(diretorioAtual + "\\notasfiscais"))) {
			bw.write(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
