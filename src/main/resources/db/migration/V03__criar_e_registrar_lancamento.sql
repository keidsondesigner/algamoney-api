CREATE TABLE lancamento (
  codigo BIGSERIAL PRIMARY KEY,
  descricao VARCHAR(50) NOT NULL,
  data_vencimento DATE NOT NULL,
  data_pagamento DATE,
  valor NUMERIC(10,2) NOT NULL,
  observacao VARCHAR(100),
  tipo VARCHAR(20) NOT NULL,
  codigo_categoria BIGINT NOT NULL,
  codigo_pessoa BIGINT NOT NULL,
  FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
  FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Compra de material', '2024-08-01', '2024-08-02', 150.75, 'Compra de papel e canetas', 'Despesa', 1, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Venda de produtos', '2024-08-05', '2024-08-05', 500.00, 'Venda de produtos diversos', 'Receita', 2, 2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Pagamento de contas', '2024-08-10', '2024-08-11', 200.00, 'Pagamento de conta de luz', 'Despesa', 3, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Recebimento de aluguel', '2024-08-15', '2024-08-15', 1000.00, 'Aluguel de imóvel', 'Receita', 4, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Compra de suprimentos', '2024-08-20', '2024-08-21', 300.50, 'Compra de suprimentos para escritório', 'Despesa', 5, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Recebimento de honorários', '2024-08-25', '2024-08-25', 750.00, 'Honorários advocatícios', 'Receita', 6, 6);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Pagamento de fornecedores', '2024-08-30', '2024-08-31', 400.00, 'Pagamento de fornecedores de TI', 'Despesa', 7, 7);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Venda de serviços', '2024-09-05', '2024-09-05', 600.00, 'Venda de serviços de consultoria', 'Receita', 8, 8);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Recebimento de dividendos', '2024-09-10', '2024-09-10', 1200.00, 'Dividendos de ações', 'Receita', 9, 9);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) VALUES ('Compra de equipamento', '2024-09-15', '2024-09-16', 2000.00, 'Compra de novo servidor', 'Despesa', 10, 10);
