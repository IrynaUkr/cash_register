


INSERT INTO user (login, password, role, surname) VALUES ('alex', '123', 'CASHIER','Alekseenko');
INSERT INTO user (login, password, role, surname) VALUES ('max', '456', 'CHIEF_CASHIER','Maximov');
INSERT INTO user (login, password, role, surname) VALUES ('fred', '789','MERCHANDISER','Fredovskiy');
INSERT INTO user (login, password, role, surname) VALUES ('fix', '179','ADMIN','Petrov');

INSERT INTO user_details (surname, adress, phone_number, email, id_user) values ('Alekseenko','Odesa,Glushko 5', 'alex@gmail.com',123456, 1);
INSERT INTO user_details (surname, adress, phone_number, email, id_user) values ('Maximov','Kiev, Peremogu 45/2', 'max@gmail.com',456789, 2);
INSERT INTO user_details (surname, adress, phone_number, email, id_user) values ('Fredovskiy','Odesa,Livitana 8 ', 'fred@gmail.com',987456, 3);
 insert INTO product_category (name, description) values ('casual', 'it use in....');
 insert INTO product_category (name, description) values ('sport', 'it use in....');
 insert INTO product_category (name, description) values ('clasic', 'it use in....');
 
INSERT INTO product (articul, name, description, price, amount, weight, category_id )
 values ('142631N01N', 'jacket', 'black color, middle length, for winter', 123, 200, 1,1); 
 INSERT INTO product (articul, name, description, price, amount, weight,category_id )
 values ('855950N10C', 'coat', 'pink color, short, for spring', 45, 300, 0.8,2); 
 INSERT INTO product (articul, name, description, price, amount, weight, category_id )
 values ('111111N10C', 'dress', 'white color, long, for spring', 55, 400, 0.3, 2); 
 INSERT INTO product (articul, name, description, price, amount, weight, category_id  )
 values ('11tt456789', 'sweater', 'grey color, long, for winter', 38, 200, 0.4, 3);
 
 insert INTO  status_receipt (name, description) values ('new', 'it saved,  needs to be closed');
 insert INTO  status_receipt (name, description) values ('closed', 'coud not be chanched');


