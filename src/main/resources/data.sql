-- Insert 3 Restaurant Owners
INSERT INTO user_types (name, phone, email, owner) VALUES ('Fernando Almeida', '11987654321', 'fernando.almeida@example.com', TRUE);
INSERT INTO user_types (name, phone, email, owner) VALUES ('Gabriela Lima', '11987654322', 'gabriela.lima@example.com', TRUE);
INSERT INTO user_types (name, phone, email, owner) VALUES ('Henrique Santos', '11987654323', 'henrique.santos@example.com', TRUE);

-- Insert 5 random Clients
INSERT INTO user_types (name, phone, email, owner) VALUES ('Ana Silva', '11912345671', 'ana.silva@example.com', FALSE);
INSERT INTO user_types (name, phone, email, owner) VALUES ('Bruno Costa', '11912345672', 'bruno.costa@example.com', FALSE);
INSERT INTO user_types (name, phone, email, owner) VALUES ('Carla Dias', '11912345673', 'carla.dias@example.com', FALSE);
INSERT INTO user_types (name, phone, email, owner) VALUES ('Daniel Rocha', '11912345674', 'daniel.rocha@example.com', FALSE);
INSERT INTO user_types (name, phone, email, owner) VALUES ('Elisa Souza', '11912345675', 'elisa.souza@example.com', FALSE);

-- Insert 3 Restaurants (owner_id references the 3 owners above: IDs 1, 2, 3)
INSERT INTO restaurants (name, address, cuisine_type, opening_hours, owner_id) VALUES ('Cantina do Fernando', 'Rua das Flores, 123 - São Paulo, SP', 'Italiana', 'Seg-Sex 11:00-23:00, Sab-Dom 11:00-00:00', 1);
INSERT INTO restaurants (name, address, cuisine_type, opening_hours, owner_id) VALUES ('Sabores da Gabriela', 'Av. Paulista, 500 - São Paulo, SP', 'Brasileira', 'Seg-Dom 10:00-22:00', 2);
INSERT INTO restaurants (name, address, cuisine_type, opening_hours, owner_id) VALUES ('Tempero do Henrique', 'Rua Augusta, 200 - São Paulo, SP', 'Japonesa', 'Ter-Dom 12:00-23:00', 3);