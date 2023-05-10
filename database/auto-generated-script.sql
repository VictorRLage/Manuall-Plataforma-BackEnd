DROP DATABASE mydb;

-- MySQL Script generated by MySQL Workbench
-- Wed May 10 09:50:17 2023
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
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NULL,
  `email` VARCHAR(256) NULL,
  `senha` CHAR(60) NULL,
  `cpf` CHAR(11) NULL,
  `orcamento_min` DECIMAL(8,2) NULL,
  `orcamento_max` DECIMAL(8,2) NULL,
  `descricao` VARCHAR(270) NULL,
  `presta_aula` TINYINT(1) NULL,
  `status` INT NULL,
  `acessos` INT NULL,
  `tipo_usuario` INT NULL,
  `canal` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`area` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
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
-- Table `mydb`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`chat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `mensagem` VARCHAR(150) NULL,
  `horario` DATETIME NULL,
  PRIMARY KEY (`id`, `contratante_usuario_id`, `prestador_usuario_id`),
  INDEX `fk_Chat_Contratante1_idx` (`contratante_usuario_id` ASC) VISIBLE,
  INDEX `fk_Chat_Usuario1_idx` (`prestador_usuario_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_Chat_Contratante1`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Chat_Usuario1`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`area_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`area_usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usuario_id` INT NOT NULL,
  `area_id` INT NOT NULL,
  PRIMARY KEY (`id`, `usuario_id`, `area_id`),
  INDEX `fk_Usuario_has_Area_Area1_idx` (`area_id` ASC) VISIBLE,
  INDEX `fk_Usuario_has_Area_Usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Usuario_has_Area_Usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Area_Area1`
    FOREIGN KEY (`area_id`)
    REFERENCES `mydb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`dados_bancarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`dados_bancarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plano` INT NULL,
  `nome` VARCHAR(60) NULL,
  `numero` CHAR(16) NULL,
  `validade` DATE NULL,
  `cvv` CHAR(3) NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_DadosBancarios_Usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_DadosBancarios_Usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tipo_servico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tipo_servico` (
  `id` INT NOT NULL,
  `Nome` VARCHAR(80) NULL,
  `area_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tipo_servico_area1_idx` (`area_id` ASC) VISIBLE,
  CONSTRAINT `fk_tipo_servico_area1`
    FOREIGN KEY (`area_id`)
    REFERENCES `mydb`.`area` (`id`)
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
  `tipo_servico` INT NOT NULL,
  `tamanho` INT NULL,
  `unidade_medida` VARCHAR(20) NULL,
  `descricao` VARCHAR(120) NULL,
  `status` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_solicitacao_usuario1_idx` (`contratante_usuario_id` ASC) VISIBLE,
  INDEX `fk_solicitacao_usuario2_idx` (`prestador_usuario_id` ASC) VISIBLE,
  INDEX `fk_solicitacao_tipo_servico1_idx` (`tipo_servico` ASC) VISIBLE,
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
    FOREIGN KEY (`tipo_servico`)
    REFERENCES `mydb`.`tipo_servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`avaliacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`avaliacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `solicitacao_id` INT NOT NULL,
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `nota` INT NULL,
  `descricao` VARCHAR(75) NULL,
  PRIMARY KEY (`id`, `solicitacao_id`),
  INDEX `fk_Avaliações_Usuario2_idx` (`prestador_usuario_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_avaliacao_solicitacao1_idx` (`solicitacao_id` ASC) VISIBLE,
  CONSTRAINT `fk_Avaliações_Usuario1`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Avaliações_Usuario2`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_avaliacao_solicitacao1`
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
  `nome` VARCHAR(60) NULL,
  `email` VARCHAR(256) NULL,
  `fone` CHAR(13) NULL,
  `faixa` VARCHAR(30) NULL,
  `como_cobra` VARCHAR(45) NULL,
  `dt_nascimento` DATE NULL,
  `opt_cidade` INT NULL,
  `opt_reside` INT NULL,
  `opt_tamanho` INT NULL,
  `opt_contratar` INT NULL,
  `opt_buscando` INT NULL,
  `opt_experiencia` INT NULL,
  `opt_canal` INT NULL,
  `opt_interesse_loja` INT NULL,
  `opt_interesse_plat` INT NULL,
  `bln_interesse_ensinar` TINYINT(1) NULL,
  `bln_ja_contratou` TINYINT(1) NULL,
  `bln_aprender` TINYINT(1) NULL,
  `bln_contratou` TINYINT(1) NULL,
  `bln_divulga` TINYINT(1) NULL,
  `bln_divulgara` TINYINT(1) NULL,
  `msg_desistencia` VARCHAR(90) NULL,
  `status` INT NULL,
  `tipo_usuario` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`desc_servicos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`desc_servicos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `topico` VARCHAR(30) NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_desc_servicos_usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_desc_servicos_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`contato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`contato` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NULL,
  `email` VARCHAR(256) NULL,
  `mensagem` VARCHAR(120) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`prospect_area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`prospect_area` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `prospect_id` INT NOT NULL,
  `area_id` INT NOT NULL,
  PRIMARY KEY (`id`, `prospect_id`, `area_id`),
  INDEX `fk_prospect_contratante_has_area_area1_idx` (`area_id` ASC) VISIBLE,
  INDEX `fk_prospect_contratante_has_area_prospect_contratante1_idx` (`prospect_id` ASC) VISIBLE,
  CONSTRAINT `fk_prospect_contratante_has_area_prospect_contratante1`
    FOREIGN KEY (`prospect_id`)
    REFERENCES `mydb`.`prospect` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prospect_contratante_has_area_area1`
    FOREIGN KEY (`area_id`)
    REFERENCES `mydb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



INSERT INTO area (id, nome) VALUES (1, "Jardineiro"),
								   (2, "Pintor"),
								   (3, "Eletricista"),
								   (4, "Encanador"),
								   (5, "Marceneiro"),
								   (6, "Montador"),
								   (7, "Gesseiro");

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
