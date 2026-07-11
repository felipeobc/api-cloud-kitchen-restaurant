-- 3 donos de restaurante (owner = TRUE)
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Fernando Almeida', '11987654321', 'fernando.almeida@example.com', TRUE);
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Carla Mendonça', '21976543210', 'carla.mendonca@example.com', TRUE);
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Roberto Souza', '31965432109', 'roberto.souza@example.com', TRUE);

-- 5 clientes (owner = FALSE)
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Ana Paula Lima', '11912345678', 'ana.lima@example.com', FALSE);
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Bruno Costa', '21923456789', 'bruno.costa@example.com', FALSE);
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Juliana Ferreira', '31934567890', 'juliana.ferreira@example.com', FALSE);
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Marcos Oliveira', '41945678901', 'marcos.oliveira@example.com', FALSE);
INSERT INTO user_types (id, name, phone, email, owner) VALUES (RANDOM_UUID(), 'Patrícia Santos', '51956789012', 'patricia.santos@example.com', FALSE);
