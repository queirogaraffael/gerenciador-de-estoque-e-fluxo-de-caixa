Gerenciador de Estoque e Fluxo de Caixa

Este repositório contém um sistema de gerenciamento de estoque e fluxo de caixa desenvolvido 
em Java. O sistema permite gerenciar o estoque de produtos, realizar vendas e controlar o 
fluxo de caixa.

Executando o Programa

Você pode executar o programa de duas maneiras:

    Arquivo "Gerenciador de Estoque e Fluxo de Caixa.jar": Você pode baixar o arquivo .jar 
    fornecido neste repositório e executá-lo em uma JVM (Java Virtual Machine) instalada em 
    seu sistema. Basta abrir um terminal, navegar até o diretório onde o arquivo .jar está 
    localizado e executar o comando java -jar "Gerenciador de Estoque e Fluxo de Caixa.jar".

    Código Fonte: Você também pode executar o programa diretamente a partir do código fonte em 
    uma IDE (Integrated Development Environment) Java, como Eclipse, IntelliJ IDEA ou NetBeans. 
    Para isso, execute a classe Main como ponto de entrada.

Funcionalidades Principais

O sistema inclui as seguintes funcionalidades:

    Cadastro, Edição, Listagem e Remoção de Produtos: Possibilidade de gerenciar o estoque de produtos, 
    realizando operações como cadastrar novos produtos, editar informações de produtos existentes, 
    listar todos os produtos cadastrados e remover produtos do estoque.

    Controle de Estoque: Gerenciamento de quantidades de produtos em estoque, garantindo uma visão 
    atualizada dos itens disponíveis.

    Fluxo de Caixa: Possibilidade de registrar vendas, adicionar produtos ao carrinho de compras, 
    finalizar compras e controlar o fluxo de caixa.

    Integração Opcional com Arquivo .txt: O sistema integra-se opcionalmente com um arquivo .txt 
    para armazenar os dados de produtos cadastrados e vendas realizadas. Qualquer operação de 
    cadastro de produto ou realização de venda é refletida neste arquivo, caso a integração esteja ativada.

    Geração Opcional de Nota Fiscal: O sistema oferece opcionalmente a opção de gerar uma nota 
    fiscal para cada compra realizada.

Utilização

Para utilizar o sistema, execute a classe Main como ponto de entrada do programa. Isso iniciará 
a aplicação e você poderá interagir com o sistema através das opções apresentadas no menu principal.

Senha de Acesso

A senha para sair do sistema é "senha123".

Arquitetura MVC

O projeto foi desenvolvido seguindo a arquitetura MVC (Model-View-Controller), que organiza a 
aplicação em três componentes principais:

    Model: Responsável pela lógica de negócios e manipulação de dados. Aqui estão as classes 
    que representam os produtos, vendas e operações relacionadas ao estoque e caixa.

    View: Responsável pela apresentação dos dados ao usuário. No caso deste projeto, as 
    visualizações são implementadas por meio de caixas de diálogo (JOptionPane) e outras 
    interfaces de interação com o usuário.

    Controller: Responsável por controlar o fluxo de dados entre o Model e a View. Aqui estão 
    os controladores que recebem as interações do usuário e coordenam as ações apropriadas no 
    Model e na View.

Observações

    O sistema requer Java para ser executado.

Formato do Arquivo de Produtos

O arquivo de produtos segue o seguinte formato:

ID,Nome,Preço,Quantidade
1,Macarrão,3.5,25
2,Arroz,5.25,15
3,Feijão,7.0,10
4,Leite,2.75,30
5,Ovos,4.0,25
6,Óleo de Soja,6.5,18
7,Farinha de Trigo,3.0,22
8,Açúcar,4.75,9
9,Sal,1.5,40
10,Café,8.0,8
11,Carne,12.5,12
12,Laranja,1.75,35
13,Banana,2.0,30
14,Cenoura,1.2,25
15,Tomate,1.5,20

Foco

O projeto é focado para pequenos comércios, oferecendo uma solução simples e eficaz para o gerenciamento de estoque e fluxo de caixa.

Licença

Este projeto não possui uma licença específica.
