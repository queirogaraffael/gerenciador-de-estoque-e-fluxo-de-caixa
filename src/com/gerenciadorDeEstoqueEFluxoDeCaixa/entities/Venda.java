package com.gerenciadorDeEstoqueEFluxoDeCaixa.entities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Venda {
	private Integer codigo;
	private Instant instant;
	private Set<Produto> produtos = new HashSet<>();

	public Venda(Integer codigo, Instant instant) {
		super();
		this.codigo = codigo;
		this.instant = instant;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getInstant() {
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
		return formatoDataHora.format(zonedDateTime);
	}

	public Set<Produto> getProdutos() {
		return produtos;
	}

	public Double valorTotal() {
		double total = 0;
		for (Produto p : produtos) {
			total += p.getValor() * p.getQuantidade();
		}
		return total;
	}

	public int quantidadeTotalItens() {
		int quantidadeTotal = 0;
		for (Produto p : produtos) {
			quantidadeTotal += p.getQuantidade();
		}
		return quantidadeTotal;
	}

	public void listarProdutos() {

		StringBuilder sb = new StringBuilder();
		for (Produto p : produtos) {
			sb.append("Codigo: " + p.getCodigo() + ", nome = " + p.getNome() + ", valor = " + String
					.format(", valor total %.2f", valorTotal() + ", quantidade total: " + quantidadeTotalItens()));
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Venda: codigo = " + codigo
				+ String.format(", valor total %.2f, Data: %s\n", valorTotal(), getInstant());
	}

}
