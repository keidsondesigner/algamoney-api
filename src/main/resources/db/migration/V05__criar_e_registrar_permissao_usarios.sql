-- Criação da tabela 'permissao'
CREATE TABLE permissao (
  codigo BIGINT PRIMARY KEY,
  descricao VARCHAR(50) NOT NULL
);

-- Criação da tabela 'usuario_permissao'
CREATE TABLE usuario_permissao (
  codigo_usuario BIGINT NOT NULL,
  codigo_permissao BIGINT NOT NULL,
  PRIMARY KEY (codigo_usuario, codigo_permissao),
  FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
  FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);

-- Inserções para tabela 'permissao'
INSERT INTO permissao (codigo, descricao) VALUES (1, 'ROLE_CATEGORIA_CADASTRAR');
INSERT INTO permissao (codigo, descricao) VALUES (2, 'ROLE_CATEGORIA_PESQUISAR');
INSERT INTO permissao (codigo, descricao) VALUES (3, 'ROLE_CATEGORIA_REMOVER');
INSERT INTO permissao (codigo, descricao) VALUES (4, 'ROLE_PESSOA_CADASTRAR');
INSERT INTO permissao (codigo, descricao) VALUES (5, 'ROLE_PESSOA_PESQUISAR');
INSERT INTO permissao (codigo, descricao) VALUES (6, 'ROLE_PESSOA_REMOVER');
INSERT INTO permissao (codigo, descricao) VALUES (7, 'ROLE_PESSOA_ATUALIZAR');
INSERT INTO permissao (codigo, descricao) VALUES (8, 'ROLE_LANCAMENTO_CADASTRAR');
INSERT INTO permissao (codigo, descricao) VALUES (9, 'ROLE_LANCAMENTO_PESQUISAR');
INSERT INTO permissao (codigo, descricao) VALUES (10, 'ROLE_LANCAMENTO_REMOVER');

-- Inserções para tabela 'usuario_permissao' (USUARIO-ADMIN-PERMISSAO)
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 3);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 4);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 6);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 7);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 8);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (1, 10);

-- Inserções para tabela 'usuario_permissao' (USUARIO-NORMAL-PERMISSAO)
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 5);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) VALUES (2, 9);