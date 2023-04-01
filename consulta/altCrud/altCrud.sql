create database altCrud;
use altCrud;

#drop database altCrud;
#drop table usuario;

create table usuario (
idUsuario int primary key,
nome varchar(45),
email varchar(45),
senha varchar(16)
);

select * from usuario;