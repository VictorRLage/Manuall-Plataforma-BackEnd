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
(1, "Projeto e paisagismo de jardins"), 
(2, "Pintura de paredes internas e externas"), 
(2, "Pintura de portas, janelas e portões"), 
(2, "Restauração e repintura de fachadas"), 
(3, "Instalação e manutenção elétrica residencial"), 
(3, "Troca de fiação elétrica"), 
(3, "Troca de lâmpada"), 
(4, "Reparos e manutenção de encanamentos"), 
(4, "Instalação de torneiras, chuveiros e pias"),    
(4, "Detecção e reparos de vazamentos"), 
(5, "Fabricação e montagem de móveis sob medida"), 
(5, "Restauração e reparo de móveis de madeira"), 
(5, "Criação de projetos personalizados em madeira"), 
(6, "Montagem de móveis pré-fabricados"), 
(6, "Montagem de estruturas metálicas"), 
(6, "Instalação de sistemas modulares"), 
(7, "Molduras e sancas de gesso"), 
(7, "Decoração em gesso 3D"), 
(7, "Reparos e manutenção em elementos de gesso"); 

INSERT INTO usuario
(`nome`, `email`, `senha`, `cpf`, `telefone`, `orcamento_min`, `orcamento_max`, `descricao`, `presta_aula`, `plano`, `status`, `anexo_pfp`, `acessos`, `tipo_usuario`, `canal`, `area_id`)
VALUES
('Joaquim Gimenes Pires', 'joaquim.pires@sptech.school', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '12345678901', '11987654321', 500.00, 1000.00, 'Sou um professor experiente', 1, 1, 2, 'https://static-cse.canva.com/blob/759723/DrobotDeanCanva.jpg', 0, 1, 0, 1),
('Maria Santos', 'maria.santos@example.com', '$2a$10$cBT.xH1jVvoCDdc/SEwaOutmAUlZoTVBsDxawF42yAW136grzNMpa', '98765432109', '11976543210', 1000.00, 2000.00, 'Ofereço aulas particulares', 0, 2, 2, 'https://imagens.usp.br/wp-content/uploads/Campus-15-Foto-Marcos-Santos20101220_066.jpg', 0, 2, 1, 3),
('Ana Oliveira', 'ana.oliveira@example.com', '$2a$10$5ph0RY7U8DKcKkHKJgYjPOToAtW6y0ZfQygJSiR4j2DYeFSKytJni', '76543210987', '11965432109', 1500.00, 3000.00, 'Posso ajudar em diversas disciplinas', 1, 3, 2, 'https://i.pinimg.com/originals/f5/67/bf/f567bfd530f04a3aff832f38844fe5be.jpg', 0, 1, 4, 5),
('Fernanda Souza', 'fernanda.souza@example.com', '$2a$10$IMisr8TdWydcgH22StC/lutUcdtDmqYVDWxyJmZmQX8Tp.nraC5dq', '23456789012', '11912345678', 800.00, 1500.00, 'Professora de Matemática', 1, 3, 2, 'perfil.jpg', 0, 1, 3, 2),
('Rafaela Lima', 'rafaela.lima@example.com', '$2a$10$NFvy/hCRZkaEMiE468y1quooIfRTVWPJEAAE2772.iry.TCCaYO3a', '34567890123', '11923456789', 1200.00, 2000.00, 'Especialista em Ciências', 0, 2, 2, 'foto.png', 0, 2, 1, 6),
('Carlos Mendes', 'carlos.mendes@example.com', '$2a$10$UT.ocrWrVawMkbqVc/6RN.NALwJOcuKIXupneVOZ.nlnWX3KBEKQy', '45678901234', '11934567890', 1500.00, 3000.00, 'Aulas de Inglês para todos os níveis', 1, 1, 2, 'avatar.jpg', 0, 1, 4, 7),
('Pedro Santos', 'pedro.santos@example.com', '$2a$10$DdOzkLpv3MWFxIijfxBNGONl3kHALH//z4G27bTGd92mq9dZRdJiS', '56789012345', '11945678901', 800.00, 1500.00, 'Professor de História', 1, 2, 2, 'perfil.jpg', 0, 1, 1, 3),
('Mariana Oliveira', 'mariana.oliveira@example.com', '$2a$10$GLZUkiDGCqZG9LX7YC/tRuPVBCNtaEhDlf.KvYtiSI7TkQ41.0zNu', '67890123456', '11956789012', 1200.00, 2000.00, 'Especialista em Biologia', 0, 1, 2, 'foto.png', 0, 2, 1, 5),
('Pedro Oliveira', 'pedro.oliveira@example.com', '$2a$10$u8ZWxnPbnTQfU1lWRNBq1Ouc1TFdBoz6GGJfhmMd1ULWliTirNH4O', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 3', 1, 3, 2, 'anexo3.jpg', 0, 2, 2, 3),
('Ana Souza', 'ana.souza@example.com', '$2a$10$AOnNkhW49gT6wW6t46Hbuenh7MEJqRAtTiIocj7chIGIq6rn2g87S', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 4', 0, 1, 2, 'anexo4.jpg', 0, 2, 3, 4),
('Lucas Ferreira', 'lucas.ferreira@example.com', '$2a$10$Nx2rDH7u93q2bM3XFA4bTeUusk3AKN18yqE2hddIhNRXPAEFHd2zm', '56789012345', '11943210987', 3000.00, 4000.00, 'Descrição do usuário 5', 1, 2, 2, 'anexo5.jpg', 0, 1, 4, 5),
('Fernanda Rodrigues', 'fernanda.rodrigues@example.com', '$2a$10$cNXBWXutEmZLpCFZV/X8duyms0Nfkq6E4/yhrSdsGCjkK90qS8zC.', '67890123456', '11932109876', 3500.00, 4500.00, 'Descrição do usuário 6', 1, 3, 2, 'anexo6.jpg', 0, 1, 3, 6),
('Gustavo Lima', 'gustavo.lima@example.com', '$2a$10$of6uGGU9YmW4t29qTsgnaedhdGvT7J03JoC5E5mAwdnqdHuVIb7tG', '78901234567', '11921098765', 4000.00, 5000.00, 'Descrição do usuário 7', 0, 1, 2, 'anexo7.jpg', 0, 2, 4, 7),
('Juliana Almeida', 'juliana.almeida@example.com', '$2a$10$cischjBywyKBTR5QEVuOBOtnEF5AMesyj0YIOlxsLghwalKHzwvWa', '89012345678', '11910987654', 4500.00, 5500.00, 'Descrição do usuário 8', 1, 2, 2, 'anexo8.jpg', 0, 1, 0, 1),
('Rafael Martins', 'rafael.martins@example.com', '$2a$10$Cx06v8rau8pXFYOGTe28leArpQq1gzmsxc.2elyxthfEVaNQl6HUK', '90123456789', '11909876543', 5000.00, 6000.00, 'Descrição do usuário 9', 0, 3, 2, 'anexo9.jpg', 0, 2, 1, 2),
('Camila Gomes', 'camila.gomes@example.com', '$2a$10$5nP0d/R1NRI02FygaUviFOBojVFljzJyScPlGhOVxjG3fTPQ9IAC.', '01234567890', '11998765432', 5500.00, 6500.00, 'Descrição do usuário 10', 1, 2, 2, 'anexo10.jpg', 0, 1, 2, 3),
('Ricardo Santos', 'ricardo.santos@example.com', '$2a$10$W3EcOn.A/DFF/eCyQXgHSOBnzFMYsuVL8QFx.NMSnbqswH/SthAHe', '12345678901', '11987654321', 1000.00, 2000.00, 'Descrição do usuário 11', 1, 1, 2, 'anexo11.jpg', 0, 1, 0, 1),
('Amanda Oliveira', 'amanda.oliveira@example.com', '$2a$10$DiZnbfv.2Zz/YkwAmfnvtOnF8TMTIKiD29gyEAhySsHPGN7UAGwIW', '23456789012', '11976543210', 1500.00, 2500.00, 'Descrição do usuário 12', 0, 2, 2, 'anexo12.jpg', 0, 1, 1, 2),
('Gabriel Rodrigues', 'gabriel.rodrigues@example.com', '$2a$10$UdS5.K.yHjQjbeBv0RsVvel3bo.KFdaV0KOZWPa/R60T8ZjLqXkDm', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 13', 1, 3, 2, 'anexo13.jpg', 0, 2, 2, 3),
('Carolina Souza', 'carolina.souza@example.com', '$2a$10$aXk4jmNvpTMhWpksBpqqrusqeX7ENeBvgWpnBSl3ZFzypzcB3ApoG', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 14', 0, 1, 2, 'anexo14.jpg', 0, 2, 3, 4),
('Matheus Ferreira', 'matheus.ferreira@example.com', '$2a$10$KRIagtT591nRBgkGCXJoFOiTIaztE/GhqlNkTyVETiYvWfn3VeqDm', '56789012345', '11943210987', 3000.00, 4000.00, 'Descrição do usuário 15', 1, 2, 2, 'anexo15.jpg', 0, 1, 4, 5);

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
(1, 2, 4, "Muito bom o serviço, mas demorou mais que o esperado"),
(2, 1, 5, "Sensacional! Serviço impecável");

INSERT INTO solicitacao (contratante_usuario_id, prestador_usuario_id, tamanho, medida, descricao, status, servico_id, avaliacao_id) VALUES
(1, 2, 50.000, "m2", "Quebar meu banheiro inteiro", 1, 1, 1),
(1, 2, 50.000, "m2", "Quebar meu banheiro inteiro", 2, 1, 1),
(1, 2, 50.000, "m2", "Quebar meu banheiro inteiro", 3, 1, 1);

INSERT INTO chat (solicitacao_id, id_remetente, mensagem, horario) values
(1, 1, "opa", '2023-05-21 14:30:00'),
(1, 2, "eai, td bem??", '2023-05-21 14:30:00');

INSERT INTO usuario_img (usuario_id, anexo) VALUES
(1, "https://www.youtube.com"),
(1, "https://www.google.com"),
(1, "https://www.facebook.com");

INSERT INTO usuario_servico (usuario_id, servico_id) VALUES
(1, 1),
(1, 2),
(1, 3);