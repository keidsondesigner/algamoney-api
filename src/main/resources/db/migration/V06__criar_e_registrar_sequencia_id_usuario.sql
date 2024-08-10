-- Ajusta a sequência do campo 'codigo' para o próximo valor disponível
SELECT setval('usuario_codigo_seq', (SELECT MAX(codigo) FROM usuario));
