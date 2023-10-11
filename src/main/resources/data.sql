INSERT INTO area
(nome)
VALUES
('Jardineiro'),
('Pintor'),
('Eletricista'),
('Encanador'),
('Marceneiro'),
('Montador'),
('Gesseiro');

INSERT INTO servico
(area_id, nome)
VALUES
(1, 'Poda'),
(1, 'Controle de Pragas'),
(1, 'Projeto e paisagismo de jardins'),
(2, 'Pintura de paredes internas e externas'),
(2, 'Pintura de portas, janelas e portoes'),
(2, 'Restauração e repintura de fachadas'),
(3, 'Instalação e manutenção elétrica residencial'),
(3, 'Troca de fiação elétrica'),
(3, 'Troca de lâmpada'),
(4, 'Reparos e manutenção de encanamentos'),
(4, 'Instalação de torneiras, chuveiros e pias'),
(4, 'Detecção e reparos de vazamentos'),
(5, 'Fabricação e montagem de móveis sob medida'),
(5, 'Restauração e reparo de móveis de madeira'),
(5, 'Criação de projetos personalizados em madeira'),
(6, 'Montagem de móveis pré-fabricados'),
(6, 'Montagem de estruturas metálicas'),
(6, 'Instalação de sistemas modulares'),
(7, 'Molduras e sancas de gesso'),
(7, 'Decoração em gesso 3D'),
(7, 'Reparos e manutenção em elementos de gesso');

INSERT INTO usuario
(nome, email, senha, cpf, telefone, orcamento_min, orcamento_max, descricao, presta_aula, plano, status, anexo_pfp, acessos, tipo_usuario, canal, area_id)
VALUES
('Joaquim Gimenes Pires', 'joaquim.pires@sptech.school', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '12345678901', '11987654321', null, null, null, null, null, 2, null, null, 1, 0, null),
('Maria Santos', 'maria.santos@example.com', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '98765432109', '11976543210', 1000.00, 2000.00, 'Ofereço aulas particulares', 0, 2, 2, 'https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80', 0, 2, 4, 3),
('Maria Santos', 'maria.santos@example.com', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '98765432109', '11976543210', null, null, null, null, null, 2, null, null, 1, 1, null),
('Ana Oliveira', 'ana.oliveira@example.com', '$2a$10$5ph0RY7U8DKcKkHKJgYjPOToAtW6y0ZfQygJSiR4j2DYeFSKytJni', '76543210987', '11965432109', null, null, null, null, null, 2, null, null, 1, 4, null),
('Fernanda Souza', 'fernanda.souza@example.com', '$2a$10$IMisr8TdWydcgH22StC/lutUcdtDmqYVDWxyJmZmQX8Tp.nraC5dq', '23456789012', '11912345678', null, null, null, null, null, 2, null, null, 1, 3, null),
('Rafaela Lima', 'rafaela.lima@example.com', '$2a$10$NFvy/hCRZkaEMiE468y1quooIfRTVWPJEAAE2772.iry.TCCaYO3a', '34567890123', '11923456789', 1200.00, 2000.00, 'Especialista em Ciências', 0, 2, 2, 'https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2071&q=80', 0, 2, 1, 6),
('Carlos Mendes', 'carlos.mendes@example.com', '$2a$10$UT.ocrWrVawMkbqVc/6RN.NALwJOcuKIXupneVOZ.nlnWX3KBEKQy', '45678901234', '11934567890', null, null, null, null, null, 2, null, null, 1, 4, null),
('Pedro Santos', 'pedro.santos@example.com', '$2a$10$DdOzkLpv3MWFxIijfxBNGONl3kHALH//z4G27bTGd92mq9dZRdJiS', '56789012345', '11945678901', null, null, null, null, null, 2, null, null, 1, 1, null),
('Lucas Monte', 'lucas.monte@example.com', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '67890123456', '11956789012', 700.00, 1400.00, 'Olá! Meu nome é Lucas Monte e sou um marceneiro apaixonado com mais de 10 de experiência no ramo. Cada pedaço de madeira é uma oportunidade para mim de transformar um simples material em uma obra de arte funcional e durável. Me especializo em projetos residenciais.', 0, 1, 2, 'https://images.unsplash.com/photo-1599566150163-29194dcaad36?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTd8fHBlcnNvbnxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=600&q=60', 0, 2, 1, 2),
('Pedro Oliveira', 'pedro.oliveira@example.com', '$2a$10$u8ZWxnPbnTQfU1lWRNBq1Ouc1TFdBoz6GGJfhmMd1ULWliTirNH4O', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 3', 1, 3, 2, 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80', 0, 2, 2, 3),
('Ana Souza', 'ana.souza@example.com', '$2a$10$AOnNkhW49gT6wW6t46Hbuenh7MEJqRAtTiIocj7chIGIq6rn2g87S', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 4', 0, 1, 2, 'https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80', 0, 2, 3, 4),
('Lucas Ferreira', 'lucas.ferreira@example.com', '$2a$10$Nx2rDH7u93q2bM3XFA4bTeUusk3AKN18yqE2hddIhNRXPAEFHd2zm', '56789012345', '11943210987', null, null, null, null, null, 2, null, null, 1, 4, null),
('Fernanda Rodrigues', 'fernanda.rodrigues@example.com', '$2a$10$cNXBWXutEmZLpCFZV/X8duyms0Nfkq6E4/yhrSdsGCjkK90qS8zC.', '67890123456', '11932109876', null, null, null, null, null, 2, null, null, 1, 3, null),
('Gustavo Lima', 'gustavo.lima@example.com', '$2a$10$of6uGGU9YmW4t29qTsgnaedhdGvT7J03JoC5E5mAwdnqdHuVIb7tG', '78901234567', '11921098765', 4000.00, 5000.00, 'Descrição do usuário 7', 0, 1, 2, 'https://images.unsplash.com/photo-1544723795-3fb6469f5b39?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1889&q=80', 0, 2, 4, 7),
('Juliana Almeida', 'juliana.almeida@example.com', '$2a$10$cischjBywyKBTR5QEVuOBOtnEF5AMesyj0YIOlxsLghwalKHzwvWa', '89012345678', '11910987654', null, null, null, null, null, 2, null, null, 1, 0, null),
('Rafael Martins', 'rafael.martins@example.com', '$2a$10$Cx06v8rau8pXFYOGTe28leArpQq1gzmsxc.2elyxthfEVaNQl6HUK', '90123456789', '11909876543', 4000.00, 5000.00, 'Descrição do usuário 9', 0, 3, 2, 'https://images.unsplash.com/photo-1503443207922-dff7d543fd0e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1854&q=80', 0, 2, 1, 2),
('Camila Gomes', 'camila.gomes@example.com', '$2a$10$5nP0d/R1NRI02FygaUviFOBojVFljzJyScPlGhOVxjG3fTPQ9IAC.', '01234567890', '11998765432', null, null, null, null, null, 2, null, null, 1, 2, null),
('Ricardo Santos', 'ricardo.santos@example.com', '$2a$10$W3EcOn.A/DFF/eCyQXgHSOBnzFMYsuVL8QFx.NMSnbqswH/SthAHe', '12345678901', '11987654321', null, null, null, null, null, 2, null, null, 1, 0, null),
('Amanda Oliveira', 'amanda.oliveira@example.com', '$2a$10$DiZnbfv.2Zz/YkwAmfnvtOnF8TMTIKiD29gyEAhySsHPGN7UAGwIW', '23456789012', '11976543210', null, null, null, null, null, 2, null, null, 1, 1, null),
('José Faria', 'jose.faria@example.com', '$2a$10$UdS5.K.yHjQjbeBv0RsVvel3bo.KFdaV0KOZWPa/R60T8ZjLqXkDm', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 13', 1, 3, 2, 'https://img.freepik.com/fotos-gratis/close-up-foto-de-um-homem-bonito-branco-com-barba-vestido-com-uma-camiseta-olhando-e-sorrindo-com-uma-expressao-alegre-e-feliz-sentado-em-um-restaurante-na-calcada-em-um-dia-ensolarado-esperando-por-amigos_273609-6600.jpg', 0, 2, 2, 3),
('Carolina Souza', 'carolina.souza@example.com', '$2a$10$aXk4jmNvpTMhWpksBpqqrusqeX7ENeBvgWpnBSl3ZFzypzcB3ApoG', '45678901234', '11954321098', 2500.00, 3500.00, 'Descrição do usuário 14', 0, 1, 2, 'https://images.unsplash.com/photo-1618085221129-cb9f9ac1e651?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80', 0, 2, 3, 4),
('Matheus Ferreira', 'matheus.ferreira@example.com', '$2a$10$KRIagtT591nRBgkGCXJoFOiTIaztE/GhqlNkTyVETiYvWfn3VeqDm', '56789012345', '11943210987', null, null, null, null, null, 2, null, null, 1, 4, null),
('Camila Gomes', 'camila.gomes@example.com', '$2a$10$5nP0d/R1NRI02FygaUviFOBojVFljzJyScPlGhOVxjG3fTPQ9IAC.', '01234567890', '11998765432', null, null, null, null, null, 2, null, null, 1, 2, null),
('Ricardo Santos', 'ricardo.santos@example.com', '$2a$10$W3EcOn.A/DFF/eCyQXgHSOBnzFMYsuVL8QFx.NMSnbqswH/SthAHe', '12345678901', '11987654321', null, null, null, null, null, 2, null, null, 1, 0, null),
('Amanda Oliveira', 'amanda.oliveira@example.com', '$2a$10$DiZnbfv.2Zz/YkwAmfnvtOnF8TMTIKiD29gyEAhySsHPGN7UAGwIW', '23456789012', '11976543210', null, null, null, null, null, 2, null, null, 1, 1, null),
('Gabriel Rodrigues', 'gabriel.rodrigues@example.com', '$2a$10$UdS5.K.yHjQjbeBv0RsVvel3bo.KFdaV0KOZWPa/R60T8ZjLqXkDm', '34567890123', '11965432107', 2000.00, 3000.00, 'Descrição do usuário 13', 1, 3, 2, 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80', 0, 2, 2, 3),
('ManuallAdm', 'manuall.services@outlook.com', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '12345678901', '11987654321', null, null, null, null, null, 2, null, null, 1, 0, null),
('ManuallAdm', 'manuall.services@outlook.com', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', '98765432109', '11976543210', 1000.00, 2000.00, 'Ofereço aulas particulares', 0, 2, 2, 'https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80', 0, 2, 1, 3),
('ManuallAdm', 'manuall.services@outlook.com', '$2a$10$Pfz3ATcOC0jdOt4F36IxGuGBrfRtxovR1LAZkWoolJRdd7xVF.4sS', null, null, null, null, null, null, null, 2, null, null, 3, null, null);

INSERT INTO dados_endereco
(estado, cidade, cep, bairro, rua, numero, complemento, usuario_id)
VALUES
('SP', 'São Bernardo', '31015170', 'São Pedro', 'Avenida do Contorno', '789', 'Sala 502', 1),
('SP', 'Guarulhos', '22041001', 'Copacabana', 'Avenida Atlântica', '1500', 'Apto 501', 2),
('SP', 'Guarulhos', '22041001', 'Copacabana', 'Avenida Atlântica', '1500', 'Apto 501', 3),
('SP', 'São Bernardo do Campo', '40140110', 'Barra', 'Avenida Oceânica', '500', null, 4),
('SP', 'Santo André', '88036250', 'Centro', 'Rua Felipe Schmidt', '800', 'Sala 301', 5),
('SP', 'São José dos Campos', '90010140', 'Centro Histórico', 'Rua dos Andradas', '1234', 'Apto 1001', 6),
('SP', 'São Paulo', '04545000', 'Itaim Bibi', 'Rua João Cachoeira', '789', 'Conjunto 502', 7),
('SP', 'Ribeirão Preto', '22231100', 'Botafogo', 'Rua Voluntários da Pátria', '123', 'Apto 301', 8),
('SP', 'Osasco', '30240180', 'Santa Efigênia', 'Rua dos Timbiras', '456', 'Sala 101', 9),
('SP', 'Sorocaba', '41740330', 'Stiep', 'Avenida Tancredo Neves', '789', null, 10),
('SP', 'Mauá', '88036110', 'Trindade', 'Rua Lauro Linhares', '987', 'Apto 601', 11),
('SP', 'São José do Rio Preto', '90430060', 'Moinhos de Vento', 'Rua Padre Chagas', '321', null, 12),
('SP', 'Mogi das Cruzes', '04004003', 'Vila Mariana', 'Avenida Paulista', '999', 'Conjunto 1503', 13),
('SP', 'Santos', '22260101', 'Leblon', 'Avenida Ataulfo de Paiva', '456', 'Sala 201', 14),
('SP', 'Diadema', '30330040', 'Santo Antônio', 'Rua Sergipe', '789', null, 15),
('SP', 'Bauru', '40290440', 'Rio Vermelho', 'Avenida Oceânica', '123', 'Apto 401', 16),
('SP', 'Taubaté', '88034400', 'Lagoa da Conceição', 'Rua das Rendeiras', '456', 'Loja 10', 17),
('SP', 'Guarujá', '90430130', 'Auxiliadora', 'Rua Marquês do Pombal', '321', 'Sala 501', 18),
('SP', 'Suzano', '05676000', 'Morumbi', 'Avenida Giovanni Gronchi', '9999', 'Conjunto 2004', 19),
('SP', 'Rio de Janeiro', '22640100', 'Ipanema', 'Rua Visconde de Pirajá', '456', 'Apto 901', 20),
('SP', 'Limeira', '41770180', 'Pituba', 'Rua das Rosas', '123', null, 21),
('SP', 'Campinas', '30110070', 'Savassi', 'Rua Pernambuco', '1000', 'Sala 202', 22),
('SP', 'Barueri', '06454000', 'Alphaville', 'Alameda Rio Negro', '1030', 'Sala 1002', 23),
('SP', 'Jundiaí', '13201054', 'Centro', 'Rua Barão de Jundiaí', '1520', null, 24),
('SP', 'Itapevi', '06690000', 'Centro', 'Rua Presidente Vargas', '587', 'Apto 103', 25),
('SP', 'Itaquaquecetuba', '08570000', 'Estação', 'Rua das Orquídeas', '987', null, 26),
('SP', 'Piracicaba', '13400240', 'Centro', 'Rua Governador', '321', 'Sala 1201', 27),
('SP', 'Piracicaba', '13400240', 'Centro', 'Rua Governador', '321', 'Sala 1201', 28),
('SP', 'Piracicaba', '13400240', 'Centro', 'Rua Governador', '321', 'Sala 1201', 29);

INSERT INTO avaliacao
(nota, descricao)
VALUES
(4, 'Muito bom o serviço, mas demorou mais que o esperado'),
(5, 'Sensacional! Serviço impecável'),
(3, 'Serviço dentro das expectativas'),
(4, 'Ótimo atendimento, recomendo'),
(2, 'Insatisfatório, poderia ter sido melhor'),
(1, 'Decepcionante, não recomendo'),
(5, 'Maravilhoso! Superou todas as expectativas'),
(4, 'Bom serviço, com pequenos problemas'),
(0, 'Horrível! Nunca mais contratarei'),
(3, 'Médio, nem bom nem ruim'),
(2, 'Serviço ruim, poderia ser melhor'),
(5, 'Serviço excepcional, superou minhas expectativas'),
(2, 'Apenas ok, esperava mais'),
(4, 'Serviço acima da média, mas com pequenos problemas'),
(5, 'Excelente serviço, recomendo'),
(1, 'Péssimo atendimento, não recomendo'),
(3, 'Serviço aceitável, mas nada demais'),
(0, 'Péssima experiência, não recomendo de jeito nenhum'),
(3, 'Atendeu às expectativas mínimas'),
(5, 'Ótimo atendimento, recomendo'),
(4, 'Bom atendimento, com algumas ressalvas'),
(1, 'Fiquei muito desapontado, esperava mais'),
(3, 'Aceitável, dentro do esperado'),
(4, 'Bom serviço, dentro das expectativas'),
(2, 'Insatisfatório, poderia ter sido melhor'),
(1, 'Decepcionante, não voltarei a contratar'),
(5, 'Serviço excelente, superou minhas expectativas'),
(1, 'Serviço abaixo das expectativas'),
(5, 'Sensacional! Serviço impecável'),
(4, 'Bom serviço, com pequenos problemas'),
(0, 'Muito insatisfeito, não cumpriu o combinado'),
(3, 'Médio, nem bom nem ruim'),
(5, 'Excelente serviço, recomendo'),
(0, 'Péssimo serviço, não recomendo'),
(3, 'Serviço satisfatório, mas com algumas falhas'),
(2, 'Simplesmente ok, nada demais'),
(1, 'Péssimo atendimento, não recomendo'),
(4, 'Bom serviço, dentro das expectativas'),
(4, 'Muito bom o serviço, mas demorou mais que o esperado'),
(2, 'Serviço ruim, poderia ser melhor'),
(1, 'Decepcionante, não voltarei a contratar'),
(5, 'Ótimo atendimento, recomendo'),
(3, 'Serviço aceitável, mas nada demais'),
(0, 'Péssimo serviço, não recomendo'),
(3, 'Atendeu às expectativas mínimas'),
(5, 'Ótimo atendimento, recomendo'),
(4, 'Bom atendimento, com algumas ressalvas'),
(1, 'Decepcionante, não voltarei a contratar'),
(5, 'Serviço excepcional, superou minhas expectativas');

INSERT INTO solicitacao
(contratante_id, prestador_id, tamanho, medida, descricao, status, servico_id, avaliacao_id)
VALUES
(1, 2, 40.0, 'm²', 'Poda de árvores no quintal', 1, 1, 1),
(3, 6, 60.0, 'm²', 'Pintura das paredes da sala', 2, 4, 4),
(4, 9, 30.0, 'm', 'Instalação de chuveiro elétrico', 4, 11, 9),
(7, 9, 10.0, 'cm', 'Reparos no encanamento do banheiro', 2, 12, 16),
(7, 10, 15.0, 'Unidade', 'Criação de projeto de móveis para cozinha', 1, 15, 20),
(11, 13, 100.0, 'm²', 'Montagem de móveis pré-fabricados', 4, 16, 25),
(12, 15, 25.0, 'm', 'Decoração em gesso 3D para sala', 2, 20, 30),
(14, 19, 50.0, 'cm', 'Restauração e repintura de fachadas', 1, 6, 35),
(16, 20, 70.0, 'Unidade', 'Fabricação e montagem de móveis sob medida', 4, 13, 40),
(17, 25, 45.0, 'm²', 'Instalação de sistemas modulares', 2, 18, 45),
(18, 26, 35.0, 'm²', 'Reparos e manutenção em elementos de gesso', 1, 21, 49),
(1, 26, 20.0, 'Unidade', 'Poda de árvores no quintal', 1, 1, 2),
(4, 2, 100.0, 'm', 'Pintura das paredes da sala', 2, 4, 7),
(6, 10, 55.0, 'm', 'Instalação de chuveiro elétrico', 4, 11, 12),
(12, 8, 30.0, 'm', 'Reparos no encanamento do banheiro', 1, 12, 15),
(14, 9, 45.0, 'm', 'Criação de projeto de móveis para cozinha', 2, 15, 19),
(18, 20, 60.0, 'cm', 'Montagem de móveis pré-fabricados', 4, 16, 22),
(23, 13, 70.0, 'cm', 'Decoração em gesso 3D para sala', 1, 20, 27),
(17, 19, 35.0, 'cm', 'Restauração e repintura de fachadas', 2, 6, 29),
(21, 25, 85.0, 'cm', 'Fabricação e montagem de móveis sob medida', 4, 13, 34),
(7, 15, 40.0, 'Unidade', 'Instalação de sistemas modulares', 1, 18, 38),
(24, 5, 50.0, 'm²', 'Reparos e manutenção em elementos de gesso', 2, 21, 43),
(1, 26, 20.0, 'm²', 'Preciso de poda para árvores altas no quintal', 1, 1, 2),
(4, 2, 100.0, 'm²', 'Pintura total em sala com paredes desgastadas', 2, 4, 7),
(6, 10, 55.0, 'm²', 'Instalação de chuveiro elétrico com disjuntor', 4, 11, 12),
(12, 8, 30.0, 'Unidade', 'Reparos em encanamento com vazamento no banheiro', 1, 12, 15),
(14, 9, 45.0, 'Unidade', 'Projeto de móveis planejados para cozinha pequena', 2, 15, 19),
(18, 20, 60.0, 'm²', 'Montagem de móveis pré-fabricados para escritório', 4, 16, 22),
(23, 13, 70.0, 'm²', 'Decoração em gesso 3D para teto da sala de estar', 1, 20, 27),
(17, 19, 35.0, 'm²', 'Restauração e repintura de fachada de prédio antigo', 2, 6, 29),
(21, 25, 85.0, 'm', 'Móveis sob medida para quarto de criança', 4, 13, 34),
(7, 15, 40.0, 'cm', 'Instalação de prateleiras modulares para livros', 1, 18, 38),
(24, 5, 50.0, 'cm', 'Reparo em sancas de gesso danificadas por umidade', 2, 21, 43);

INSERT INTO chat
(solicitacao_id, id_remetente, mensagem, horario)
VALUES
(1, 1, 'Oi, tudo bem?', '2023-08-10 10:30:00'),
(1, 2, 'Olá! Estou bem, e você?', '2023-08-10 10:31:00'),
(2, 4, 'Bom dia, já pode começar o serviço?', '2023-08-11 09:00:00'),
(2, 5, 'Bom dia! Sim, já estou a caminho.', '2023-08-11 09:02:00'),
(3, 6, 'Oi, preciso alterar o horário do serviço para amanhã.', '2023-08-12 14:15:00'),
(3, 8, 'Sem problemas, que horas seria melhor para você?', '2023-08-12 14:17:00'),
(4, 7, 'Boa tarde, gostaria de adicionar um serviço extra.', '2023-08-13 16:20:00'),
(4, 9, 'Boa tarde! Claro, qual seria o serviço adicional?', '2023-08-13 16:22:00'),
(5, 11, 'Olá, vocês fazem instalação de torneiras também?', '2023-08-14 11:45:00'),
(5, 15, 'Olá! Sim, fazemos. Gostaria de adicionar esse serviço?', '2023-08-14 11:47:00'),
(6, 12, 'Oi, os móveis chegaram. Podemos agendar a montagem?', '2023-08-15 13:30:00'),
(6, 19, 'Oi! Claro, posso ir amanhã às 14h. Está bom para você?', '2023-08-15 13:32:00');

INSERT INTO usuario_img
(prestador_id, anexo)
VALUES
(2, 'https://static.vecteezy.com/ti/fotos-gratis/t2/9759099-bela-cachoeira-natureza-cenario-da-floresta-profunda-colorida-em-dia-de-verao-gratis-foto.jpg'),
(2, 'https://i.pinimg.com/236x/a4/bb/31/a4bb31b5f54ef7ad2581de5230b045b4.jpg'),
(2, 'https://img.elo7.com.br/product/original/338BB23/painel-de-festa-paisagem-natureza-3x2-festa-infantil.jpg');

INSERT INTO usuario_servico
(prestador_id, servico_id)
VALUES
(2, 1),
(2, 2),
(5, 3),
(5, 4),
(8, 5),
(8, 6),
(9, 7),
(9, 8),
(10, 9),
(10, 10),
(13, 11),
(13, 12),
(15, 13),
(15, 14),
(19, 15),
(19, 16),
(20, 17),
(20, 18),
(25, 19),
(25, 20),
(26, 21);

INSERT INTO crm_log
(acessou_url, inicio_contato, processo_finalizado, usuario_id)
VALUES
(0, '2023-08-12 14:17:00', 0, 1),
(0, '2023-08-15 11:23:45', 0, 2),
(0, '2023-09-01 10:15:30', 0, 3),
(0, '2023-09-02 17:47:20', 0, 4),
(0, '2023-08-19 09:34:50', 0, 5);

INSERT INTO crm_log_mensagem
(mensagem, crm_log_id)
VALUES
(20458, 1),
(78503, 1);