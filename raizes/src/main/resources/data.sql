-- 1. Inserindo as Unidades
INSERT INTO unidades (nome, localizacao) VALUES ('Raízes Recife Centro', 'Recife-PE');
INSERT INTO unidades (nome, localizacao) VALUES ('Raízes Paulista', 'São Paulo-SP');

-- 2. Inserindo os Produtos no Cardápio
INSERT INTO produtos (nome, descricao, preco_base) VALUES ('Tapioca de Carne de Sol', 'Tapioca recheada com carne de sol e queijo coalho', 15.50);
INSERT INTO produtos (nome, descricao, preco_base) VALUES ('Cuscuz Completo', 'Cuscuz com ovo, charque e queijo', 12.00);

-- 3. Inserindo o Estoque (Relacionando a Unidade 1 aos Produtos 1 e 2)
INSERT INTO estoque (unidade_id, produto_id, quantidade) VALUES (1, 1, 50);
INSERT INTO estoque (unidade_id, produto_id, quantidade) VALUES (1, 2, 30);

-- 4. Inserindo o Usuário para Teste de Login (Com proteção contra quebra da aplicação)
-- O ON CONFLICT impede que o Spring trave caso você reinicie a API e o e-mail já exista.
INSERT INTO usuarios (nome, email, senha_hash, perfil, pontos_fidelidade) 
VALUES ('Cliente Teste', 'cliente@exemplo.com', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.', 'CLIENTE', 0)
ON CONFLICT (email) DO NOTHING;