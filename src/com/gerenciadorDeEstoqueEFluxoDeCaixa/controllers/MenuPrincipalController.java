package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.commons.constantes.ConstantesMenuPrincipal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.commons.utils.AutenticadorDeSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.CategoriaDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.DaoFactory;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.ItemVendaDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.ProdutoDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.VendaDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.domain.NotaFiscal;

public class MenuPrincipalController {
	private EstoqueController estoqueController;
	private CaixaController caixaController;
	private NotaFiscal notaFiscal;
	private CategoriaDao categoriaDao;
	private ItemVendaDao itemVendaDao;
	private ProdutoDao produtoDao;
	private VendaDao vendaDao;

	public MenuPrincipalController() {
		this.notaFiscal = new NotaFiscal();

		this.categoriaDao = DaoFactory.createCategoriaDao();
		this.itemVendaDao = DaoFactory.createItemVendaDao();
		this.produtoDao = DaoFactory.createProdutoDao();
		this.vendaDao = DaoFactory.createVendaDao();

		this.estoqueController = new EstoqueController(notaFiscal, categoriaDao, itemVendaDao, produtoDao, vendaDao);
		this.caixaController = new CaixaController(notaFiscal, categoriaDao, itemVendaDao, produtoDao, vendaDao);

	}

	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;
		Object[] opcoes = { "Gerenciador de Estoque", "Fluxo de Caixa", "Encerrar programa" };

		do {

			opcaoMenuPrincipal = JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Menu Principal",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			switch (opcaoMenuPrincipal) {
			case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
				String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
				boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

				if (autenticacao) {

					estoqueController.gerenciadorEstoque();
				} else {
					JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
				}

				break;
			case (ConstantesMenuPrincipal.FLUXO_CAIXA):
				caixaController.fluxoDeCaixa();
				break;
			default:

				EntityManagerFactoryService.fechaEntityManagerFactory();
				System.exit(0);
			}
		} while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);
	}
}
