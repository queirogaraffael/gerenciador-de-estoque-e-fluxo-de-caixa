package com.gerenciadorDeEstoqueEFluxoDeCaixa.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendas")
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	private Instant instant;

	@OneToMany(mappedBy = "id.venda")
	private Set<ItemVenda> itens = new HashSet<>();

	public Venda() {
	}

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

	public Venda(Instant instant) {
		this.instant = instant;
	}

	public String getInstant() {
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
		return formatoDataHora.format(zonedDateTime);
	}

	public Set<ItemVenda> getProdutosVendidos() {
		return itens;
	}

	public Double total() {
		double total = 0;
		for (ItemVenda p : itens) {
			total += p.subTotal();
		}
		return total;
	}

//melhorar isso aqui
	public void listarProdutos() {

		StringBuilder sb = new StringBuilder();

		sb.append("Codigo venda: " + String.format("%d,", codigo) + "Data: " + getInstant() + "\n");

		for (ItemVenda p : itens) {
			sb.append("Codigo: " + p.getProduto().getcodigoDeBarra() + ", nome = " + p.getProduto().getNome()
					+ "Quantidade: " + String.format("%d, ", p.getQuantidade()) + "Total: "
					+ String.format("%d", p.subTotal()) + "\n");

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
		return "Venda [codigo=" + codigo + ", instant=" + instant + "]";
	}

}
