-- Criação da tabela 'usuario'
CREATE TABLE usuario (
  codigo BIGINT PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  senha VARCHAR(150) NOT NULL
);

-- Inserções para tabela 'usuario'
INSERT INTO usuario (codigo, nome, email, senha) VALUES (1, 'Administrador', 'admin@algamoney.com', '$2a$10$1FFHyNTmifrNf7MoWjzww.ZGRYuhiNhwXo/uxD1DrDeZSbItd7iWy');
INSERT INTO usuario (codigo, nome, email, senha) VALUES (2, 'Usuario Normal', 'user@algamoney.com', '$2a$10$dgEw/3QSZnr5IMTneIg4UOEe5/IBsXY8R87xJW.xtYrW6THntLuvW');
