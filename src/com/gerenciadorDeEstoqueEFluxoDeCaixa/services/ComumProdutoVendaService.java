package com.gerenciadorDeEstoqueEFluxoDeCaixa.services;

import java.util.Set;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.interfaces.InterfaceSuporteMetodosEmComum;

public class ComumProdutoVendaService {

	// tipo generico. Aqui eu posso verificar se ja contém um produto ou uma venda
	// pelo codigo sem precisar escrever dois metodos com tipos de argumentos
	// diferentes(Venda e Produto)
	public static <T extends InterfaceSuporteMetodosEmComum> boolean jaContem(Set<T> colecao, Integer codigo) {
		boolean JaExiste = colecao.stream().anyMatch(p -> p.getCodigo().equals(codigo));
		return JaExiste;
	}

	// tipo generico que obriga qualquer argumento da função ter um metodo getCodigo
	// para poder funcionar corretamente
	public static <T extends InterfaceSuporteMetodosEmComum> T retornaPeloCodigo(Set<T> colecao, Integer codigo) {
		for (T p : colecao) {
			if (p.getCodigo().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

	// Metodo em comum para ver tanto as vendas quanto os produtos
	public static <T> String imprimir(Set<T> colecao) {
		StringBuilder sb = new StringBuilder();

		for (T elemento : colecao) {
			sb.append(elemento + "\n");
		}
		return sb.toString();
	}

}
