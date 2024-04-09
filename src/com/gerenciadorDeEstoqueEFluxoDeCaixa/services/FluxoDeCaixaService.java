package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;

public class FluxoDeCaixaService {

	public static Random random = new Random();

	// gera um codigo inteiro de 4 digitos
	public static int geradorDeCodigo(Set<Integer> colecaoCodigos) {

		int numero = random.nextInt(9000) + 1000;

		while (colecaoCodigos.contains(numero)) {
			numero = random.nextInt(9000) + 1000;
		}

		colecaoCodigos.add(numero);
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
