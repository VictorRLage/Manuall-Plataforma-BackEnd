INSERT INTO area (nome) VALUES
("Jardineiro"),
("Pintor"),
("Eletricista"),
("Encanador"),
("Marceneiro"),
("Montador"),
("Gesseiro");
insert into servico (area_id, nome) values
(1, "Poda"),
(1, "Controle de Pragas"),
(1, "Adubagem"),
(3, "Conserto de resistência de chuveiro"),
(3, "Troca de fiação"),
(3, "Troca de lâmpada");

select * from usuario;