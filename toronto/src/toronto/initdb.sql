SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS Cliente (
cpf varchar(24) PRIMARY KEY AUTO_INCREMENT,
nome varchar(120),
email varchar(120),
telefone varchar(24),
logradouro varchar(120),
numero varchar(24),
bairro varchar(80),
cep varchar(24),
cidade varchar(64),
uf varchar(24),
criado_em date DEFAULT CURRENT_DATE(),
ultima_compra date,
id_usuario bigint
);

CREATE TABLE IF NOT EXISTS Usuario (
id_usuario bigint PRIMARY KEY AUTO_INCREMENT,
nome varchar(120),
login varchar(80) UNIQUE,
senha varchar(64),
salario real,
admin boolean
);

CREATE TABLE IF NOT EXISTS Pedido (
cod_pedido bigint PRIMARY KEY AUTO_INCREMENT,
status varchar(64),
data date DEFAULT CURRENT_DATE(),
forma_pag varchar(64),
valor real,
num_nf bigint
);

CREATE TABLE IF NOT EXISTS Produto (
cod_produto bigint PRIMARY KEY AUTO_INCREMENT,
nome varchar(120),
descricao varchar(240),
preco real
);

CREATE TABLE IF NOT EXISTS Produtos (
id bigint PRIMARY KEY AUTO_INCREMENT,
quantidade int,
cod_produto bigint,
cod_pedido bigint,
FOREIGN KEY(cod_produto) REFERENCES Produto (cod_produto),
FOREIGN KEY(cod_pedido) REFERENCES Pedido (cod_pedido)
);

CREATE TABLE IF NOT EXISTS NotaFiscal (
num_nf bigint PRIMARY KEY AUTO_INCREMENT,
impostos real,
valor real,
cod_pedido bigint,
FOREIGN KEY(cod_pedido) REFERENCES Pedido (cod_pedido)
);

ALTER TABLE Cliente ADD FOREIGN KEY(id_usuario) REFERENCES Usuario (id_usuario);
ALTER TABLE Pedido ADD FOREIGN KEY(num_nf) REFERENCES NotaFiscal (num_nf);

SET FOREIGN_KEY_CHECKS=1;