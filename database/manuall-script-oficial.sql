CREATE DATABASE TesteManuall;
USE TesteManuall;

#DROP DATABASE TesteManuall;
#DROP TABLE area;
#DROP TABLE usuario;
#DROP TABLE area_usuario;
#DROP TABLE dados_bancarios;
#DROP TABLE endereco;
#DROP TABLE imagem;
#DROP TABLE solicitacao;

#SELECT * FROM area;
#SELECT * FROM usuario;
#SELECT * FROM area_usuario;
#SELECT * FROM dados_bancarios;
#SELECT * FROM endereco;
#SELECT * FROM imagem;
#SELECT * FROM solicitacao;

CREATE TABLE IF NOT EXISTS area (
id_area INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS perfis (
id_perfis INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS usuario (
id_usuario INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(45),
email VARCHAR(45),
senha VARCHAR(45),
cpf VARCHAR(11),
aprovado BOOLEAN DEFAULT FALSE,
fk_perfis_id_perfis INT NOT NULL,
FOREIGN KEY (fk_perfis_id_perfis) REFERENCES perfis(id_perfis)
);

CREATE TABLE IF NOT EXISTS area_usuario (
id_area_usuario INT AUTO_INCREMENT PRIMARY KEY,
fk_usuario_id_usuario INT NOT NULL,
fk_area_id_area INT NOT NULL,
FOREIGN KEY (fk_usuario_id_usuario) REFERENCES usuario(id_usuario),
FOREIGN KEY (fk_area_id_area) REFERENCES area(id_area)
);

CREATE TABLE IF NOT EXISTS chat (
fk_remetente_id_usuario INT NOT NULL,
fk_destinatario_id_usuario INT NOT NULL,
id_mensagem INT UNIQUE AUTO_INCREMENT,
mensagem VARCHAR(80),
horario DATETIME,
PRIMARY KEY (fk_remetente_id_usuario, fk_destinatario_id_usuario, id_mensagem),
FOREIGN KEY (fk_remetente_id_usuario) REFERENCES usuario(id_usuario),
FOREIGN KEY (fk_destinatario_id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE IF NOT EXISTS dados_bancarios (
id_dados_bancarios INT AUTO_INCREMENT PRIMARY KEY,
plano INT,
nome VARCHAR(60),
numero INT,
validade DATE,
cvv VARCHAR(3),
fk_usuario_id_usuario INT NOT NULL,
FOREIGN KEY (fk_usuario_id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE IF NOT EXISTS endereco (
id_endereco INT AUTO_INCREMENT PRIMARY KEY,
estado VARCHAR(25),
cidade VARCHAR(35),
cep VARCHAR(8),
bairro VARCHAR(35),
rua VARCHAR(45),
numero INT,
complemento VARCHAR(25),
fk_usuario_id_usuario INT NOT NULL,
FOREIGN KEY (fk_usuario_id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE IF NOT EXISTS imagem (
id_imagem INT AUTO_INCREMENT PRIMARY KEY,
perfil LONGBLOB,
rg_frente LONGBLOB,
rg_verso LONGBLOB,
fk_usuario_id_usuario INT NOT NULL,
FOREIGN KEY (fk_usuario_id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE IF NOT EXISTS solicitacao (
fk_remetente_id_usuario INT NOT NULL,
fk_destinatario_id_usuario INT NOT NULL,
id_solicitacao INT UNIQUE AUTO_INCREMENT,
dt_solicitacao DATE,
assunto VARCHAR(15),
mensagem VARCHAR(45),
aprovado BOOLEAN DEFAULT NULL,
PRIMARY KEY (fk_remetente_id_usuario, fk_destinatario_id_usuario, id_solicitacao),
FOREIGN KEY (fk_remetente_id_usuario) REFERENCES usuario(id_usuario),
FOREIGN KEY (fk_destinatario_id_usuario) REFERENCES usuario(id_usuario)
);

INSERT INTO area (nome) VALUES (
"Marceneiro"
);