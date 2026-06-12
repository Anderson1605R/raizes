-- 1. Inserindo as Filiais (Unidades)
INSERT INTO tb_unidade (nome, localizacao) VALUES ('Raízes do Nordeste - Matriz Centro', 'Recife - PE');
INSERT INTO tb_unidade (nome, localizacao) VALUES ('Raízes do Nordeste - Shopping', 'João Pessoa - PB');

-- 2. Inserindo o Cardápio (Produtos)
INSERT INTO tb_produto (nome, descricao, preco_base) VALUES ('Coxinha de Macaxeira', 'Deliciosa coxinha com massa de macaxeira e recheio de frango desfiado', 8.50);
INSERT INTO tb_produto (nome, descricao, preco_base) VALUES ('Cuscuz com Carne de Sol', 'Cuscuz tradicional nordestino acompanhado de carne de sol na manteiga de garrafa', 15.90);
INSERT INTO tb_produto (nome, descricao, preco_base) VALUES ('Suco de Cajá', 'Copo de 500ml de suco natural de cajá', 7.00);

-- 3. Inserindo o Estoque Descentralizado (A ponte entre Produto e Unidade)
-- A Matriz (ID 1) tem 50 coxinhas (ID 1) e 30 cuscuz (ID 2)
INSERT INTO tb_estoque (unidade_id, produto_id, quantidade) VALUES (1, 1, 50);
INSERT INTO tb_estoque (unidade_id, produto_id, quantidade) VALUES (1, 2, 30);
-- O Shopping (ID 2) tem apenas 20 coxinhas (ID 1) e 100 sucos (ID 3)
INSERT INTO tb_estoque (unidade_id, produto_id, quantidade) VALUES (2, 1, 20);
INSERT INTO tb_estoque (unidade_id, produto_id, quantidade) VALUES (2, 3, 100);

-- 4. Inserindo os Usuários (Simulando LGPD com uma senha que já sofreu Hash e Roles diferentes)
-- A senha simulada abaixo seria o hash bcrypt para a senha "123456"
INSERT INTO tb_usuario (nome, email, senha_hash, perfil, pontos_fidelidade) 
VALUES ('João Silva', 'joao.cliente@email.com', '$2a$10$Wp/2QJpX4QOqM.bLqV6x1.a6Xh7DXZO2bO2p.tH8U8QYgG5P9e8y', 'CLIENTE', 10);

INSERT INTO tb_usuario (nome, email, senha_hash, perfil, pontos_fidelidade) 
VALUES ('Maria Souza', 'maria.gerente@raizes.com', '$2a$10$Wp/2QJpX4QOqM.bLqV6x1.a6Xh7DXZO2bO2p.tH8U8QYgG5P9e8y', 'GERENTE', 0);