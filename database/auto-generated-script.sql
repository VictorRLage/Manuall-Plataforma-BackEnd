DROP DATABASE mydb;
-- MySQL Script generated by MySQL Workbench
-- Sun May 28 11:33:08 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`area` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NULL,
  `email` VARCHAR(256) NULL,
  `senha` CHAR(60) NULL,
  `cpf` CHAR(11) NULL,
  `telefone` CHAR(11) NULL,
  `orcamento_min` DECIMAL(8,2) NULL,
  `orcamento_max` DECIMAL(8,2) NULL,
  `descricao` VARCHAR(270) NULL,
  `presta_aula` TINYINT(1) NULL,
  `plano` INT NULL,
  `status` INT NULL,
  `anexo_pfp` VARCHAR(150) NULL,
  `acessos` INT NULL,
  `tipo_usuario` INT NULL,
  `canal` INT NULL,
  `area_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_usuario_area1_idx` (`area_id` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_area1`
    FOREIGN KEY (`area_id`)
    REFERENCES `mydb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`dados_endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`dados_endereco` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `estado` VARCHAR(25) NULL,
  `cidade` VARCHAR(35) NULL,
  `cep` CHAR(8) NULL,
  `bairro` VARCHAR(35) NULL,
  `rua` VARCHAR(45) NULL,
  `numero` INT NULL,
  `complemento` VARCHAR(25) NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_dados_endereco_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_dados_endereco_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`servico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`servico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `area_id` INT NOT NULL,
  `nome` VARCHAR(90) NULL,
  PRIMARY KEY (`id`, `area_id`),
  INDEX `fk_tipo_servico_area1_idx` (`area_id` ASC) VISIBLE,
  CONSTRAINT `fk_tipo_servico_area1`
    FOREIGN KEY (`area_id`)
    REFERENCES `mydb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`avaliacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`avaliacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `nota` INT NULL,
  `descricao` VARCHAR(75) NULL,
  PRIMARY KEY (`id`, `contratante_usuario_id`, `prestador_usuario_id`),
  INDEX `fk_avaliacao_usuario1_idx` (`contratante_usuario_id` ASC) VISIBLE,
  INDEX `fk_avaliacao_usuario2_idx` (`prestador_usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_avaliacao_usuario1`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_avaliacao_usuario2`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`solicitacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `tamanho` DECIMAL(10,3) NULL,
  `medida` VARCHAR(10) NULL,
  `descricao` VARCHAR(120) NULL,
  `status` INT NULL,
  `servico_id` INT NOT NULL,
  `avaliacao_id` INT NULL,
  PRIMARY KEY (`id`, `contratante_usuario_id`, `prestador_usuario_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_solicitacao_usuario1_idx` (`contratante_usuario_id` ASC) VISIBLE,
  INDEX `fk_solicitacao_usuario2_idx` (`prestador_usuario_id` ASC) VISIBLE,
  INDEX `fk_solicitacao_tipo_servico1_idx` (`servico_id` ASC) VISIBLE,
  INDEX `fk_solicitacao_avaliacao1_idx` (`avaliacao_id` ASC) VISIBLE,
  CONSTRAINT `fk_solicitacao_usuario1`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_solicitacao_usuario2`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_solicitacao_tipo_servico1`
    FOREIGN KEY (`servico_id`)
    REFERENCES `mydb`.`servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_solicitacao_avaliacao1`
    FOREIGN KEY (`avaliacao_id`)
    REFERENCES `mydb`.`avaliacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`chat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `solicitacao_id` INT NOT NULL,
  `id_remetente` INT NOT NULL,
  `mensagem` VARCHAR(150) NULL,
  `horario` DATETIME NULL,
  `anexo` VARCHAR(150) NULL,
  PRIMARY KEY (`id`, `solicitacao_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_chat_solicitacao1_idx` (`solicitacao_id` ASC) VISIBLE,
  CONSTRAINT `fk_chat_solicitacao1`
    FOREIGN KEY (`solicitacao_id`)
    REFERENCES `mydb`.`solicitacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`prospect`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`prospect` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_cliente` INT NOT NULL,
  `opt_canal` INT NULL,
  `nome` VARCHAR(60) NULL,
  `email` VARCHAR(256) NULL,
  `fone` CHAR(13) NULL,
  `opt_cidade` INT NULL,
  `bln_conhece_manuall` TINYINT(1) NULL,
  `bln_aprender` TINYINT(1) NULL,
  `bln_interesse_manuall` TINYINT(1) NULL,
  `bln_interesse_ensinar` TINYINT(1) NULL,
  `bln_conhece_semelhante` TINYINT(1) NULL,
  `bln_cupom` TINYINT(1) NULL,
  `msg_desistencia` VARCHAR(90) NULL,
  `status` INT NULL,
  `tipo_usuario` INT NULL,
  `area_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_prospect_area1_idx` (`area_id` ASC) VISIBLE,
  CONSTRAINT `fk_prospect_area1`
    FOREIGN KEY (`area_id`)
    REFERENCES `mydb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`token_blacklist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`token_blacklist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(512) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuario_servico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuario_servico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usuario_id` INT NOT NULL,
  `servico_id` INT NOT NULL,
  PRIMARY KEY (`id`, `usuario_id`, `servico_id`),
  INDEX `fk_usuario_has_servico_servico1_idx` (`servico_id` ASC) VISIBLE,
  INDEX `fk_usuario_has_servico_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_has_servico_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_has_servico_servico1`
    FOREIGN KEY (`servico_id`)
    REFERENCES `mydb`.`servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`solicitacao_img`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitacao_img` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `solicitacao_id` INT NOT NULL,
  `anexo` VARCHAR(150) NULL,
  PRIMARY KEY (`id`, `solicitacao_id`),
  INDEX `fk_solicitacao_img_solicitacao1_idx` (`solicitacao_id` ASC) VISIBLE,
  CONSTRAINT `fk_solicitacao_img_solicitacao1`
    FOREIGN KEY (`solicitacao_id`)
    REFERENCES `mydb`.`solicitacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`solicitacao_img`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitacao_img` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `solicitacao_id` INT NOT NULL,
  `anexo` VARCHAR(150) NULL,
  PRIMARY KEY (`id`, `solicitacao_id`),
  INDEX `fk_solicitacao_img_solicitacao1_idx` (`solicitacao_id` ASC) VISIBLE,
  CONSTRAINT `fk_solicitacao_img_solicitacao1`
    FOREIGN KEY (`solicitacao_id`)
    REFERENCES `mydb`.`solicitacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuario_img`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuario_img` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usuario_id` INT NOT NULL,
  `anexo` VARCHAR(150) NULL,
  PRIMARY KEY (`id`, `usuario_id`),
  INDEX `fk_usuario_img_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_img_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`form_orcamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`form_orcamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `mensagem` VARCHAR(135) NULL,
  `orcamento` DECIMAL(9,2) NULL,
  PRIMARY KEY (`id`, `contratante_usuario_id`, `prestador_usuario_id`),
  INDEX `fk_form_orcamento_usuario1_idx` (`prestador_usuario_id` ASC) VISIBLE,
  INDEX `fk_form_orcamento_usuario2_idx` (`contratante_usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_form_orcamento_usuario1`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_form_orcamento_usuario2`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`desc_servicos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`desc_servicos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usuario_id` INT NOT NULL,
  `topico` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `usuario_id`),
  INDEX `fk_desc_servicos_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_desc_servicos_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO area (nome) VALUES
("Jardineiro"),
("Pintor"),
("Eletricista"),
("Encanador"),
("Marceneiro"),
("Montador"),
("Gesseiro");

INSERT INTO servico (area_id, nome) VALUES
(1, "Poda"),
(1, "Controle de Pragas"),
(1, "Adubagem"),
(2, "Pintura de paredes"),
(2, "Pintura de portas e janelas"),
(2, "Pintura de móveis"),
(3, "Conserto de resistência de chuveiro"),
(3, "Troca de fiação"),
(3, "Troca de lâmpada"),
(4, "Desentupimento de pia"),
(4, "Reparo de vazamentos"),
(4, "Instalação de tubulações"),
(5, "Fabricação de móveis sob medida"),
(5, "Reforma de móveis"),
(5, "Instalação de armários"),
(6, "Montagem de móveis"),
(6, "Montagem de estantes"),
(6, "Montagem de mesas e cadeiras"),
(7, "Instalação de gesso no teto"),
(7, "Reparo de acabamento em gesso"),
(7, "Sanca de gesso");

INSERT INTO usuario
(`nome`, `email`, `senha`, `cpf`, `telefone`, `orcamento_min`, `orcamento_max`, `descricao`, `presta_aula`, `plano`, `status`, `anexo_pfp`, `acessos`, `tipo_usuario`, `canal`, `area_id`)
VALUES
('Joaquim Gimenes Pires', 'joaquim.pires@sptech.school', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '12345678901', '11987654321', 500.00, 1000.00, 'Sou um professor experiente', 1, 1, 2, 'https://picsum.photos/200/300', 0, 1, 0, 1),
('Maria Santos', 'maria.santos@example.com', '$2a$10$cBT.xH1jVvoCDdc/SEwaOutmAUlZoTVBsDxawF42yAW136grzNMpa', '98765432109', '11976543210', 1000.00, 2000.00, 'Ofereço aulas particulares', 0, 2, 2, 'https://placeimg.com/640/480/any', 0, 2, 1, 3),
('Ana Oliveira', 'ana.oliveira@example.com', '$2a$10$5ph0RY7U8DKcKkHKJgYjPOToAtW6y0ZfQygJSiR4j2DYeFSKytJni', '76543210987', '11965432109', 1500.00, 3000.00, 'Posso ajudar em diversas disciplinas', 1, 3, 2, 'https://source.unsplash.com/random/800x600', 0, 1, 4, 5),
('Fernanda Souza', 'fernanda.souza@example.com', '$2a$10$IMisr8TdWydcgH22StC/lutUcdtDmqYVDWxyJmZmQX8Tp.nraC5dq', '23456789012', '11912345678', 800.00, 1500.00, 'Professora de Matemática', 1, 3, 2, 'https://picsum.photos/seed/picsum/200/300', 0, 1, 3, 2),
('Rafaela Lima', 'rafaela.lima@example.com', '$2a$10$NFvy/hCRZkaEMiE468y1quooIfRTVWPJEAAE2772.iry.TCCaYO3a', '34567890123', '11923456789', 1200.00, 2000.00, 'Especialista em Ciências', 0, 2, 2, 'https://placekitten.com/300/300', 0, 2, 1, 6),
('Carlos Mendes', 'carlos.mendes@example.com', '$2a$10$UT.ocrWrVawMkbqVc/6RN.NALwJOcuKIXupneVOZ.nlnWX3KBEKQy', '45678901234', '11934567890', 1500.00, 3000.00, 'Aulas de Inglês para todos os níveis', 1, 1, 2, 'https://source.unsplash.com/featured/800x600', 0, 1, 4, 7),
('Pedro Santos', 'pedro.santos@example.com', '$2a$10$DdOzkLpv3MWFxIijfxBNGONl3kHALH//z4G27bTGd92mq9dZRdJiS', '56789012345', '11945678901', 800.00, 1500.00, 'Professor de História', 1, 2, 2, 'https://picsum.photos/id/237/200/300', 0, 1, 1, 3),
('Mariana Oliveira', 'mariana.oliveira@example.com', '$2a$10$GLZUkiDGCqZG9LX7YC/tRuPVBCNtaEhDlf.KvYtiSI7TkQ41.0zNu', '67890123456', '11956789012', 1200.00, 2000.00, 'Especialista em Biologia', 0, 1, 2, 'https://placebeard.it/400/400', 0, 2, 1, 5),
('Pedro Oliveira', 'pedro.oliveira@example.com', '$2a$10$u8ZWxnPbnTQfU1lWRNBq1Ouc1TFdBoz6GGJfhmMd1ULWliTirNH4O', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 3', 1, 3, 2, 'https://source.unsplash.com/collection/190727/800x600', 0, 2, 2, 3),
('Ana Souza', 'ana.souza@example.com', '$2a$10$AOnNkhW49gT6wW6t46Hbuenh7MEJqRAtTiIocj7chIGIq6rn2g87S', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 4', 0, 1, 2, 'https://loremflickr.com/500/500', 0, 2, 3, 4),
('Carlos Mendes', 'carlos.mendes@example.com', '$2a$10$UT.ocrWrVawMkbqVc/6RN.NALwJOcuKIXupneVOZ.nlnWX3KBEKQy', '45678901234', '11934567890', 1500.00, 3000.00, 'Aulas de Inglês para todos os níveis', 1, 1, 2, 'https://picsum.photos/seed/picsum/200/300', 0, 1, 4, 7),
('Pedro Santos', 'pedro.santos@example.com', '$2a$10$DdOzkLpv3MWFxIijfxBNGONl3kHALH//z4G27bTGd92mq9dZRdJiS', '56789012345', '11945678901', 800.00, 1500.00, 'Professor de História', 1, 2, 2, 'https://placeimg.com/640/480/any', 0, 1, 1, 3),
('Pedro Oliveira', 'pedro.oliveira@example.com', '$2a$10$u8ZWxnPbnTQfU1lWRNBq1Ouc1TFdBoz6GGJfhmMd1ULWliTirNH4O', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 3', 1, 3, 2, 'https://loremflickr.com/320/240', 0, 2, 2, 3),
('Lucas Ferreira', 'lucas.ferreira@example.com', '$2a$10$Nx2rDH7u93q2bM3XFA4bTeUusk3AKN18yqE2hddIhNRXPAEFHd2zm', '56789012345', '11943210987', 3000.00, 4000.00, 'Descrição do usuário 5', 1, 2, 2, 'https://placekitten.com/300/300', 0, 1, 4, 5),
('Fernanda Rodrigues', 'fernanda.rodrigues@example.com', '$2a$10$cNXBWXutEmZLpCFZV/X8duyms0Nfkq6E4/yhrSdsGCjkK90qS8zC.', '67890123456', '11932109876', 3500.00, 4500.00, 'Descrição do usuário 6', 1, 3, 2, 'https://source.unsplash.com/featured/800x600', 0, 1, 3, 6),
('Gustavo Lima', 'gustavo.lima@example.com', '$2a$10$of6uGGU9YmW4t29qTsgnaedhdGvT7J03JoC5E5mAwdnqdHuVIb7tG', '78901234567', '11921098765', 4000.00, 5000.00, 'Descrição do usuário 7', 0, 1, 2, 'https://picsum.photos/id/237/200/300', 0, 2, 4, 7),
('Juliana Almeida', 'juliana.almeida@example.com', '$2a$10$cischjBywyKBTR5QEVuOBOtnEF5AMesyj0YIOlxsLghwalKHzwvWa', '89012345678', '11910987654', 4500.00, 5500.00, 'Descrição do usuário 8', 1, 2, 2, 'https://placebeard.it/400/400', 0, 1, 0, 1),
('Rafael Martins', 'rafael.martins@example.com', '$2a$10$Cx06v8rau8pXFYOGTe28leArpQq1gzmsxc.2elyxthfEVaNQl6HUK', '90123456789', '11909876543', 5000.00, 6000.00, 'Descrição do usuário 9', 0, 3, 2, 'https://picsum.photos/seed/picsum/200/300', 0, 2, 1, 2),
('Ricardo Santos', 'ricardo.santos@example.com', '$2a$10$W3EcOn.A/DFF/eCyQXgHSOBnzFMYsuVL8QFx.NMSnbqswH/SthAHe', '12345678901', '11987654321', 1000.00, 2000.00, 'Descrição do usuário 11', 1, 1, 2, 'anexo11.jpg', 0, 1, 0, 1),
('Amanda Oliveira', 'amanda.oliveira@example.com', '$2a$10$DiZnbfv.2Zz/YkwAmfnvtOnF8TMTIKiD29gyEAhySsHPGN7UAGwIW', '23456789012', '11976543210', 1500.00, 2500.00, 'Descrição do usuário 12', 0, 2, 2, 'anexo12.jpg', 0, 1, 1, 2),
('Gabriel Rodrigues', 'gabriel.rodrigues@example.com', '$2a$10$UdS5.K.yHjQjbeBv0RsVvel3bo.KFdaV0KOZWPa/R60T8ZjLqXkDm', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 13', 1, 3, 2, 'anexo13.jpg', 0, 2, 2, 3),
('Carolina Souza', 'carolina.souza@example.com', '$2a$10$aXk4jmNvpTMhWpksBpqqrusqeX7ENeBvgWpnBSl3ZFzypzcB3ApoG', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 14', 0, 1, 2, 'anexo14.jpg', 0, 2, 3, 4),
('Matheus Ferreira', 'matheus.ferreira@example.com', '$2a$10$KRIagtT591nRBgkGCXJoFOiTIaztE/GhqlNkTyVETiYvWfn3VeqDm', '56789012345', '11943210987', 3000.00, 4000.00, 'Descrição do usuário 15', 1, 2, 2, 'anexo15.jpg', 0, 1, 4, 5),
('Camila Gomes', 'camila.gomes@example.com', '$2a$10$5nP0d/R1NRI02FygaUviFOBojVFljzJyScPlGhOVxjG3fTPQ9IAC.', '01234567890', '11998765432', 5500.00, 6500.00, 'Descrição do usuário 10', 1, 2, 2, 'https://picsum.photos/seed/picsum/200/300', 0, 1, 2, 3),
('Ricardo Santos', 'ricardo.santos@example.com', '$2a$10$W3EcOn.A/DFF/eCyQXgHSOBnzFMYsuVL8QFx.NMSnbqswH/SthAHe', '12345678901', '11987654321', 1000.00, 2000.00, 'Descrição do usuário 11', 1, 1, 2, 'https://placeimg.com/640/480/any', 0, 1, 0, 1),
('Amanda Oliveira', 'amanda.oliveira@example.com', '$2a$10$DiZnbfv.2Zz/YkwAmfnvtOnF8TMTIKiD29gyEAhySsHPGN7UAGwIW', '23456789012', '11976543210', 1500.00, 2500.00, 'Descrição do usuário 12', 0, 2, 2, 'https://source.unsplash.com/random/800x600', 0, 1, 1, 2),
('Gabriel Rodrigues', 'gabriel.rodrigues@example.com', '$2a$10$UdS5.K.yHjQjbeBv0RsVvel3bo.KFdaV0KOZWPa/R60T8ZjLqXkDm', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 13', 1, 3, 2, 'https://loremflickr.com/320/240', 0, 2, 2, 3),
('Carolina Souza', 'carolina.souza@example.com', '$2a$10$aXk4jmNvpTMhWpksBpqqrusqeX7ENeBvgWpnBSl3ZFzypzcB3ApoG', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 14', 0, 1, 2, 'https://picsum.photos/200/300', 0, 2, 3, 4),
('Matheus Ferreira', 'matheus.ferreira@example.com', '$2a$10$KRIagtT591nRBgkGCXJoFOiTIaztE/GhqlNkTyVETiYvWfn3VeqDm', '56789012345', '11943210987', 3000.00, 4000.00, 'Descrição do usuário 15', 1, 2, 2, 'https://placekitten.com/300/300', 0, 1, 4, 5);

#LOGIN ADMINISTRATIVO
# Email:
# manuall.services@outlook.com
# Senha:
# #GfManuall
insert into usuario (nome, email, senha, tipo_usuario) values
("ManuallAdm", "manuall.services@outlook.com", "$2a$10$Q3ZNczM5qGAGf.qWtPHVnuVlwhlNjWBSQR9aiyunaQ6rty2bYtayK", 3);

INSERT INTO dados_endereco (estado, cidade, cep, bairro, rua, numero, complemento, usuario_id) VALUES
("Minas Gerais", "Belo Horizonte", "31015170", "São Pedro", "Avenida do Contorno", "789", "Sala 502", 1),
("Rio de Janeiro", "Rio de Janeiro", "22041001", "Copacabana", "Avenida Atlântica", "1500", "Apto 501", 2),
("Minas Gerais", "Belo Horizonte", "30110070", "Savassi", "Rua Pernambuco", "1000", "Sala 202", 3),
("Bahia", "Salvador", "40140110", "Barra", "Avenida Oceânica", "500", null, 4),
("Santa Catarina", "Florianópolis", "88036250", "Centro", "Rua Felipe Schmidt", "800", "Sala 301", 5),
("Rio Grande do Sul", "Porto Alegre", "90010140", "Centro Histórico", "Rua dos Andradas", "1234", "Apto 1001", 6),
("São Paulo", "São Paulo", "04545000", "Itaim Bibi", "Rua João Cachoeira", "789", "Conjunto 502", 7),
("Rio de Janeiro", "Rio de Janeiro", "22231100", "Botafogo", "Rua Voluntários da Pátria", "123", "Apto 301", 8),
("Minas Gerais", "Belo Horizonte", "30240180", "Santa Efigênia", "Rua dos Timbiras", "456", "Sala 101", 9),
("Bahia", "Salvador", "41740330", "Stiep", "Avenida Tancredo Neves", "789", null, 10),
("Santa Catarina", "Florianópolis", "88036110", "Trindade", "Rua Lauro Linhares", "987", "Apto 601", 11),
("Rio Grande do Sul", "Porto Alegre", "90430060", "Moinhos de Vento", "Rua Padre Chagas", "321", null, 12),
("São Paulo", "São Paulo", "04004003", "Vila Mariana", "Avenida Paulista", "999", "Conjunto 1503", 13),
("Rio de Janeiro", "Rio de Janeiro", "22260101", "Leblon", "Avenida Ataulfo de Paiva", "456", "Sala 201", 14),
("Minas Gerais", "Belo Horizonte", "30330040", "Santo Antônio", "Rua Sergipe", "789", null, 15),
("Bahia", "Salvador", "40290440", "Rio Vermelho", "Avenida Oceânica", "123", "Apto 401", 16),
("Santa Catarina", "Florianópolis", "88034400", "Lagoa da Conceição", "Rua das Rendeiras", "456", "Loja 10", 17),
("Rio Grande do Sul", "Porto Alegre", "90430130", "Auxiliadora", "Rua Marquês do Pombal", "321", "Sala 501", 18),
("São Paulo", "São Paulo", "05676000", "Morumbi", "Avenida Giovanni Gronchi", "9999", "Conjunto 2004", 19),
("Rio de Janeiro", "Rio de Janeiro", "22640100", "Ipanema", "Rua Visconde de Pirajá", "456", "Apto 901", 20),
("Bahia", "Salvador", "41770180", "Pituba", "Rua das Rosas", "123", null, 21);

INSERT INTO avaliacao (contratante_usuario_id, prestador_usuario_id, nota, descricao) VALUES
(1, 22, 3, "Serviço dentro das expectativas"),
(1, 13, 4, "Ótimo atendimento, recomendo"),
(1, 5, 2, "Insatisfatório, poderia ter sido melhor"),
(1, 10, 1, "Decepcionante, não recomendo"),
(1, 2, 5, "Maravilhoso! Superou todas as expectativas"),
(1, 19, 4, "Bom serviço, com pequenos problemas"),
(1, 9, 0, "Horrível! Nunca mais contratarei"),
(1, 23, 3, "Médio, nem bom nem ruim"),
(1, 14, 2, "Serviço ruim, poderia ser melhor"),
(1, 28, 5, "Serviço excepcional, superou minhas expectativas"),
(1, 17, 2, "Apenas ok, esperava mais"),
(1, 8, 4, "Serviço acima da média, mas com pequenos problemas"),
(1, 10, 5, "Excelente serviço, recomendo"),
(1, 5, 1, "Péssimo atendimento, não recomendo"),
(1, 6, 3, "Serviço aceitável, mas nada demais"),
(1, 1, 0, "Péssima experiência, não recomendo de jeito nenhum"),
(1, 23, 3, "Atendeu às expectativas mínimas"),
(1, 8, 5, "Ótimo atendimento, recomendo"),
(1, 10, 4, "Bom atendimento, com algumas ressalvas"),
(1, 2, 1, "Fiquei muito desapontado, esperava mais"),
(1, 19, 3, "Aceitável, dentro do esperado"),
(1, 22, 4, "Bom serviço, dentro das expectativas"),
(1, 14, 2, "Insatisfatório, poderia ter sido melhor"),
(1, 5, 1, "Decepcionante, não voltarei a contratar"),
(1, 28, 5, "Serviço excelente, superou minhas expectativas"),
(1, 9, 1, "Serviço abaixo das expectativas"),
(1, 2, 5, "Sensacional! Serviço impecável"),
(1, 13, 4, "Bom serviço, com pequenos problemas"),
(1, 5, 0, "Muito insatisfeito, não cumpriu o combinado"),
(1, 10, 3, "Médio, nem bom nem ruim"),
(1, 13, 5, "Excelente serviço, recomendo"),
(1, 2, 0, "Péssimo serviço, não recomendo"),
(1, 13, 3, "Serviço satisfatório, mas com algumas falhas"),
(1, 14, 2, "Simplesmente ok, nada demais"),
(1, 5, 1, "Péssimo atendimento, não recomendo"),
(1, 28, 4, "Bom serviço, dentro das expectativas"),
(1, 2, 4, "Muito bom o serviço, mas demorou mais que o esperado"),
(1, 23, 2, "Serviço ruim, poderia ser melhor"),
(1, 10, 1, "Decepcionante, não voltarei a contratar"),
(1, 5, 5, "Ótimo atendimento, recomendo"),
(1, 5, 3, "Serviço aceitável, mas nada demais"),
(1, 2, 0, "Péssimo serviço, não recomendo"),
(1, 2, 3, "Atendeu às expectativas mínimas"),
(1, 8, 5, "Ótimo atendimento, recomendo"),
(1, 10, 4, "Bom atendimento, com algumas ressalvas"),
(1, 5, 1, "Decepcionante, não voltarei a contratar"),
(1, 28, 5, "Serviço excepcional, superou minhas expectativas");


INSERT INTO solicitacao (contratante_usuario_id, prestador_usuario_id, tamanho, medida, descricao, status, servico_id, avaliacao_id) VALUES
(1, 2, 50.000, "m2", "Quebar meu banheiro inteiro", 1, 1, 1),
(1, 2, 50.000, "m2", "Quebar meu banheiro inteiro", 2, 1, 1),
(1, 2, 50.000, "m2", "Quebar meu banheiro inteiro", 3, 1, 1);

INSERT INTO chat (solicitacao_id, id_remetente, mensagem, horario) values
(2, 2, "opa", '2023-05-21 14:30:00'),
(2, 2, "eai, td bem??", '2023-05-21 14:30:00');

INSERT INTO usuario_img (usuario_id, anexo) VALUES
(1, "https://www.youtube.com"),
(1, "https://www.google.com"),
(1, "https://www.facebook.com");

INSERT INTO usuario_servico (usuario_id, servico_id) VALUES
(1, 1),
(1, 2),
(1, 3);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

select * from usuario;
SELECT id FROM usuario WHERE tipo_usuario = 2;
