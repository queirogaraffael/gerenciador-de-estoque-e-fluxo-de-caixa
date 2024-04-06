package com.gerenciadorDeEstoqueEFluxoDeCaixa.entities;

import java.util.Objects;

public class Produto {
	private Integer codigo;
	private String nome;
	private Double valor;
	private Integer quantidade;

	public Produto(Integer codigo, String nome, Double valor, Integer quantidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public void setValor(Double valor) {
		this.valor = valor;
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
		Produto other = (Produto) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Produto: código = " + codigo + ", nome = " + nome + ", valor = " + String.format("%.2f", valor)
				+ ", quantidade = " + quantidade;
	}

}
