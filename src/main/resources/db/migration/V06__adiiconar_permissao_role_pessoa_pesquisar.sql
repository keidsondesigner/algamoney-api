-- Inserções para tabela 'permissao'
INSERT INTO permissao (codigo, descricao) VALUES (11, 'ROLE_PESSOA_PESQUISAR');

-- Inserções para tabela 'usuario_permissao' (USUARIO-ADMIN-PERMISSAO)
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 11);

-- Inserções para tabela 'usuario_permissao' (USUARIO-NORMAL-PERMISSAO)
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 11);