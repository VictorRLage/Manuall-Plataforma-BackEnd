-- MySQL Script generated by MySQL Workbench
-- Thu Apr 13 12:40:38 2023
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
-- Table `mydb`.`canal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`canal` (
  `id` INT NOT NULL,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuario` (
  `id` INT NOT NULL,
  `nome` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `senha` VARCHAR(45) NULL,
  `cpf` CHAR(11) NULL,
  `orcamento_min` DECIMAL(8,2) NULL,
  `orcamento_max` DECIMAL(8,2) NULL,
  `status` INT NULL,
  `acessos` INT NULL,
  `tipo_usuario` INT NULL,
  `canal_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Usuario_Canal1_idx` (`canal_id` ASC) VISIBLE,
  CONSTRAINT `fk_Usuario_Canal1`
    FOREIGN KEY (`canal_id`)
    REFERENCES `mydb`.`canal` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`area` (
  `id` INT NOT NULL,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`endereco` (
  `id` INT NOT NULL,
  `estado` VARCHAR(25) NULL,
  `cidade` VARCHAR(35) NULL,
  `cep` CHAR(8) NULL,
  `bairro` VARCHAR(35) NULL,
  `rua` VARCHAR(45) NULL,
  `numero` INT NULL,
  `complemento` VARCHAR(25) NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Endereço_Usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Endereço_Usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`chat` (
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `id` INT NOT NULL,
  `mensagem` VARCHAR(80) NULL,
  `horario` DATETIME NULL,
  PRIMARY KEY (`contratante_usuario_id`, `prestador_usuario_id`, `id`),
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
  `id` VARCHAR(45) NOT NULL,
  `fk_usuario_id` INT NOT NULL,
  `fk_area_id` INT NOT NULL,
  PRIMARY KEY (`id`, `fk_usuario_id`, `fk_area_id`),
  INDEX `fk_Usuario_has_Area_Area1_idx` (`fk_area_id` ASC) VISIBLE,
  INDEX `fk_Usuario_has_Area_Usuario1_idx` (`fk_usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Usuario_has_Area_Usuario1`
    FOREIGN KEY (`fk_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_has_Area_Area1`
    FOREIGN KEY (`fk_area_id`)
    REFERENCES `mydb`.`area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`imagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`imagem` (
  `id` INT NOT NULL,
  `perfil` BLOB NULL,
  `rg_frente` BLOB NULL,
  `rg_verso` BLOB NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Imagem_Usuario1_idx` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Imagem_Usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`dados_bancarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`dados_bancarios` (
  `id` INT NOT NULL,
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
-- Table `mydb`.`avaliacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`avaliacao` (
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `id` INT NOT NULL,
  `nota` INT NULL,
  `descricao` VARCHAR(45) NULL,
  PRIMARY KEY (`contratante_usuario_id`, `prestador_usuario_id`, `id`),
  INDEX `fk_Avaliações_Usuario2_idx` (`prestador_usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_Avaliações_Usuario1`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Avaliações_Usuario2`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`anexo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`anexo` (
  `id` INT NOT NULL,
  `arquivo` BLOB NULL,
  `chat_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Anexo_Chat1_idx` (`chat_id` ASC) VISIBLE,
  CONSTRAINT `fk_Anexo_Chat1`
    FOREIGN KEY (`chat_id`)
    REFERENCES `mydb`.`chat` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`prospect_contratante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`prospect_contratante` (
  `id` INT NOT NULL,
  `id_cliente` INT NOT NULL,
  `nome` VARCHAR(60) NULL,
  `email` VARCHAR(256) NULL,
  `fone` CHAR(13) NULL,
  `status` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`solicitacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`solicitacao` (
  `contratante_usuario_id` INT NOT NULL,
  `prestador_usuario_id` INT NOT NULL,
  `id` INT NOT NULL,
  `assunto` VARCHAR(30) NULL,
  `mensagem` VARCHAR(80) NULL,
  `status` INT NULL,
  `tipo_servico` INT NULL,
  PRIMARY KEY (`id`, `contratante_usuario_id`, `prestador_usuario_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_solicitacao_usuario1_idx` (`contratante_usuario_id` ASC) VISIBLE,
  INDEX `fk_solicitacao_usuario2_idx` (`prestador_usuario_id` ASC) VISIBLE,
  CONSTRAINT `fk_solicitacao_usuario1`
    FOREIGN KEY (`contratante_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_solicitacao_usuario2`
    FOREIGN KEY (`prestador_usuario_id`)
    REFERENCES `mydb`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
