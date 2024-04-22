USE stockcash;

CREATE TABLE categorias (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

INSERT INTO categorias (id, nome) VALUES 
    (1, 'Alimentos e Bebidas'),
    (2, 'Produtos de Limpeza'),
    (3, 'Higiene Pessoal'),
    (4, 'Eletrônicos'),
    (5, 'Roupas e Acessórios'),
    (6, 'Móveis e Decoração'),
    (7, 'Ferramentas e Equipamentos'),
    (8, 'Livros e Materiais de Escritório'),
    (9, 'Saúde e Bem-Estar'),
    (10, 'Automotivo'),
    (11, 'Outra');