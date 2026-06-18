-- 1. Inserindo as Unidades
INSERT INTO unidade (nome, localizacao) VALUES ('Raízes Recife Centro', 'Recife-PE');
INSERT INTO unidade (nome, localizacao) VALUES ('Raízes Paulista', 'São Paulo-SP');

-- 2. Inserindo os Produtos no Cardápio
INSERT INTO produto (nome, descricao, preco_base) VALUES ('Tapioca de Carne de Sol', 'Tapioca recheada com carne de sol e queijo coalho', 15.50);
INSERT INTO produto (nome, descricao, preco_base) VALUES ('Cuscuz Completo', 'Cuscuz com ovo, charque e queijo', 12.00);

-- 3. Inserindo o Estoque (Relacionando Unidade e Produto)
-- Ex: A unidade 1 (Recife) tem 50 Tapiocas e 30 Cuscuz disponíveis
INSERT INTO estoque (unidade_id, produto_id, quantidade) VALUES (1, 1, 50);
INSERT INTO estoque (unidade_id, produto_id, quantidade) VALUES (1, 2, 30);

-- 4. Inserindo o Usuário para Teste de Login
-- A senha abaixo é o Hash BCrypt correspondente a "Senha@123" para cumprir a regra da LGPD
INSERT INTO tb_usuario (nome, email, senha_hash, perfil, pontos_fidelidade) 
VALUES ('Cliente Teste', 'cliente@exemplo.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuAw5QOTP.1/2B.pBdbGfE//N7Vw3qDZm', 'CLIENTE', 0);