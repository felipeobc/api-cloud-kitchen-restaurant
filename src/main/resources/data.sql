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

-- Insert menu items (restaurant_id references the 3 restaurants above: IDs 1, 2, 3)
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Spaghetti Carbonara', 'Massa italiana com ovos, queijo pecorino e pancetta', 45.90, FALSE, '/photos/spaghetti-carbonara.jpg', 1);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Pizza Margherita', 'Pizza clássica com molho de tomate, mussarela e manjericão', 52.00, TRUE, '/photos/pizza-margherita.jpg', 1);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Feijoada Completa', 'Feijoada tradicional com arroz, couve, farofa e laranja', 65.00, TRUE, '/photos/feijoada.jpg', 2);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Pão de Queijo', 'Pão de queijo mineiro artesanal', 8.50, FALSE, '/photos/pao-de-queijo.jpg', 2);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Sushi Combinado', 'Combinado com 20 peças variadas de sushi e sashimi', 89.90, TRUE, '/photos/sushi-combinado.jpg', 3);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Lasanha Bolonhesa', 'Lasanha com molho bolonhesa, bechamel e queijo gratinado', 58.00, FALSE, '/photos/lasanha-bolonhesa.jpg', 1);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Risoto de Funghi', 'Risoto cremoso com mix de cogumelos e parmesão', 62.50, TRUE, '/photos/risoto-funghi.jpg', 1);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Tiramisu', 'Sobremesa italiana clássica com mascarpone e café', 28.00, FALSE, '/photos/tiramisu.jpg', 1);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Picanha na Brasa', 'Picanha grelhada com arroz, farofa e vinagrete', 95.00, TRUE, '/photos/picanha-brasa.jpg', 2);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Coxinha de Frango', 'Coxinha artesanal recheada com frango e catupiry', 12.00, FALSE, '/photos/coxinha.jpg', 2);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Açaí na Tigela', 'Açaí batido com granola, banana e mel', 22.00, FALSE, '/photos/acai.jpg', 2);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Temaki de Salmão', 'Temaki recheado com salmão, cream cheese e pepino', 32.00, TRUE, '/photos/temaki-salmao.jpg', 3);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Yakisoba de Frango', 'Macarrão yakisoba com frango, legumes e molho shoyu', 42.00, FALSE, '/photos/yakisoba.jpg', 3);
INSERT INTO menu_items (name, description, price, only_in_restaurant, photo_path, restaurant_id) VALUES ('Gyoza', 'Pastel japonês recheado com carne suína e repolho, frito ou cozido', 35.00, TRUE, '/photos/gyoza.jpg', 3);