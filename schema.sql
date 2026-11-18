BEGIN;

CREATE TYPE cargo AS ENUM (
    'ATENDENTE',
    'GARCOM',
    'GERENTE'
);

CREATE TYPE status_comanda AS ENUM (
    'ABERTA',
    'FECHADA',
    'CANCELADA'
);

CREATE TYPE status_mesa_sinuca AS ENUM (
    'LIVRE',
    'OCUPADA',
    'MANUTENCAO'
);

CREATE TABLE produto (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(150) NOT NULL,
    preco NUMERIC(12, 2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT pk_produto PRIMARY KEY (id),
    CONSTRAINT ck_produto_nome_nao_vazio
        CHECK (BTRIM(nome) <> ''),
    CONSTRAINT ck_produto_preco_positivo
        CHECK (preco > 0),
    CONSTRAINT ck_produto_estoque_nao_negativo
        CHECK (quantidade_estoque >= 0)
);

CREATE TABLE funcionario (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    telefone VARCHAR(20),
    cargo cargo NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT pk_funcionario PRIMARY KEY (id),
    CONSTRAINT uq_funcionario_cpf UNIQUE (cpf),
    CONSTRAINT ck_funcionario_nome_nao_vazio
        CHECK (BTRIM(nome) <> ''),
    CONSTRAINT ck_funcionario_cpf_nao_vazio
        CHECK (BTRIM(cpf) <> ''),
    CONSTRAINT ck_funcionario_senha_hash_nao_vazia
        CHECK (BTRIM(senha_hash) <> '')
);

CREATE TABLE mesa_sinuca (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    numero INTEGER NOT NULL,
    valor_hora NUMERIC(12, 2) NOT NULL,
    status status_mesa_sinuca NOT NULL DEFAULT 'LIVRE',

    CONSTRAINT pk_mesa_sinuca PRIMARY KEY (id),
    CONSTRAINT uq_mesa_sinuca_numero UNIQUE (numero),
    CONSTRAINT ck_mesa_sinuca_numero_positivo
        CHECK (numero > 0),
    CONSTRAINT ck_mesa_sinuca_valor_hora_positivo
        CHECK (valor_hora > 0)
);

CREATE TABLE comanda (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    data_hora_abertura TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_hora_fechamento TIMESTAMP WITHOUT TIME ZONE,
    status status_comanda NOT NULL DEFAULT 'ABERTA',
    nome_cliente VARCHAR(150) NOT NULL,
    funcionario_responsavel_id BIGINT,

    CONSTRAINT pk_comanda PRIMARY KEY (id),
    CONSTRAINT fk_comanda_funcionario_responsavel
        FOREIGN KEY (funcionario_responsavel_id)
        REFERENCES funcionario (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT ck_comanda_nome_cliente_nao_vazio
        CHECK (BTRIM(nome_cliente) <> ''),
    CONSTRAINT ck_comanda_datas
        CHECK (
            data_hora_fechamento IS NULL
            OR data_hora_fechamento >= data_hora_abertura
        )
);

CREATE TABLE item_comanda (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario NUMERIC(12, 2) NOT NULL,
    subtotal NUMERIC(14, 2) NOT NULL,
    comanda_id BIGINT NOT NULL,

    CONSTRAINT pk_item_comanda PRIMARY KEY (id),
    CONSTRAINT uq_item_comanda_produto
        UNIQUE (comanda_id, produto_id),
    CONSTRAINT fk_item_comanda_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_item_comanda_comanda
        FOREIGN KEY (comanda_id)
        REFERENCES comanda (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ck_item_comanda_quantidade_positiva
        CHECK (quantidade > 0),
    CONSTRAINT ck_item_comanda_preco_unitario_positivo
        CHECK (preco_unitario > 0),
    CONSTRAINT ck_item_comanda_subtotal
        CHECK (subtotal = ROUND(preco_unitario * quantidade, 2))
);

CREATE TABLE uso_mesa_sinuca (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    hora_inicio TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    hora_fim TIMESTAMP WITHOUT TIME ZONE,
    valor_hora_aplicado NUMERIC(12, 2) NOT NULL,
    valor_total NUMERIC(14, 2),
    mesa_sinuca_id BIGINT NOT NULL,
    comanda_id BIGINT NOT NULL,

    CONSTRAINT pk_uso_mesa_sinuca PRIMARY KEY (id),
    CONSTRAINT fk_uso_mesa_sinuca_mesa
        FOREIGN KEY (mesa_sinuca_id)
        REFERENCES mesa_sinuca (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_uso_mesa_sinuca_comanda
        FOREIGN KEY (comanda_id)
        REFERENCES comanda (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ck_uso_mesa_valor_hora_positivo
        CHECK (valor_hora_aplicado > 0),
    CONSTRAINT ck_uso_mesa_datas
        CHECK (hora_fim IS NULL OR hora_fim >= hora_inicio),
    CONSTRAINT ck_uso_mesa_encerramento
        CHECK (
            (hora_fim IS NULL AND valor_total IS NULL)
            OR (hora_fim IS NOT NULL AND valor_total IS NOT NULL)
        ),
    CONSTRAINT ck_uso_mesa_valor_total_nao_negativo
        CHECK (valor_total IS NULL OR valor_total >= 0)
);

CREATE INDEX idx_comanda_funcionario_responsavel
    ON comanda (funcionario_responsavel_id);

CREATE INDEX idx_item_comanda_produto
    ON item_comanda (produto_id);

CREATE INDEX idx_uso_mesa_sinuca_mesa
    ON uso_mesa_sinuca (mesa_sinuca_id);

CREATE INDEX idx_uso_mesa_sinuca_comanda
    ON uso_mesa_sinuca (comanda_id);

COMMIT;
