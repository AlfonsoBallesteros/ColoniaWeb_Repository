INSERT INTO permissions (name,description,create_at,update_at) VALUES ('LEER','PERMISO DE LEER', NOW(),NOW())
INSERT INTO permissions (name,description,create_at,update_at) VALUES ('ESCRIBIR','PERMISO DE ESCRIBIR', NOW(),NOW())
INSERT INTO permissions (name,description,create_at,update_at) VALUES ('MODIFICAR','PERMISO DE MODIFICAR', NOW(),NOW())
INSERT INTO permissions (name,description,create_at,update_at) VALUES ('BORRAR','PERMISO DE BORRAR', NOW(),NOW())

INSERT INTO roles (name,description,create_at,update_at) VALUES ('ADMIN','ROL ADMIN', NOW(),NOW())
INSERT INTO roles (name,description,create_at,update_at) VALUES ('USER','ROL USER', NOW(),NOW())

INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(1, 1)
INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(1, 2)
INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(1, 3)
INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(1, 4)
INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(2, 1)
INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(2, 2)
INSERT INTO roles_permissions(rol_id, permissions_id) VALUES(2, 3)


INSERT INTO countries (name,prefix,create_at,update_at) VALUES ('COLOMBIA','+57',NOW(),NOW())
INSERT INTO countries (name,prefix,create_at,update_at) VALUES ('USA','+1',NOW(),NOW())
INSERT INTO doctypes (name, create_at, update_at) VALUES ('CÉDULA DE CIUDADANÍA', NOW(), NOW())
INSERT INTO doctypes (name, create_at, update_at) VALUES ('CÉDULA DE EXTRANJERÍA', NOW(), NOW())
INSERT INTO doctypes (name, create_at, update_at) VALUES ('TARJETA DE IDENTIDAD', NOW(), NOW())

INSERT INTO genders (name, create_at, update_at) VALUES ('FEMENINO', NOW(), NOW())
INSERT INTO genders (name, create_at, update_at) VALUES ('MASCULINO', NOW(), NOW())

INSERT INTO usuarios(username,password,email,name,lastname,doc_id,birthdate,cellphone,ocupation, avatar, country_id, doc_type_id, gender_id,enable,verified,create_at,update_at) VALUES ('PedritoAlimaña','pedrito123','pedrito@correo.com', 'PEDRITO', 'ALIMAÑA','123','2007-04-29','1232','INDEPENDIENTE', 'avatar1',1,1,2,'true','false',NOW(),NOW())
INSERT INTO usuarios(username,password,email,name,lastname,doc_id,birthdate,cellphone,ocupation, avatar, country_id, doc_type_id, gender_id,enable,verified,create_at,update_at) VALUES ('JuanitaNavaja','juanita123','juanita@correo.com', 'JUANITA', 'NAVAJA','321','2007-04-29','2321','TRABAJADOR', 'avatar2',1,2,2,'true','false',NOW(),NOW())

INSERT INTO networks(name, create_at, update_at) VALUES ('FACEBOOK',NOW(), NOW())
INSERT INTO networks(name, create_at, update_at) VALUES ('INSTAGRAM',NOW(), NOW())

INSERT INTO usuarios_roles (usuario_id, roles_id) VALUES (1,1)
INSERT INTO usuarios_roles (usuario_id, roles_id) VALUES (1,2)
INSERT INTO usuarios_roles (usuario_id, roles_id) VALUES (2,1)
INSERT INTO usuarios_networks(usuario_id, network_id, url, create_at, update_at) VALUES (1,1, 'https://facebook.com/pedrito', NOW(), NOW())
INSERT INTO usuarios_networks(usuario_id, network_id, url, create_at, update_at) VALUES (1,2, 'https://instagram.com/pedrito', NOW(), NOW())
INSERT INTO usuarios_networks(usuario_id, network_id, url, create_at, update_at) VALUES (2,1, 'https://facebook.com/juanita', NOW(), NOW())
INSERT INTO expacademics (title,institute,entry,exit,description,create_at,update_at,usuario_id) VALUES ('INGENIERÍA','HARVARD', '2019-04-29','2020-04-29' , 'Estudiante en el área de ingeniería', NOW(),NOW(), 1)
INSERT INTO expacademics (title,institute,entry,exit,description,create_at,update_at,usuario_id) VALUES ('DOCTOR','SURCOLOMBIANA', '2019-04-29', '2020-04-29', 'Titulo de doctor en la universidad', NOW(),NOW(), 1)
INSERT INTO expacademics (title,institute,entry,exit,description,create_at,update_at,usuario_id) VALUES ('INGENIERÍA','HARVARD', '2019-04-29','2020-04-29' , 'Estudiante en el área de ingeniería', NOW(),NOW(), 2)
INSERT INTO expacademics (title,institute,entry,exit,description,create_at,update_at,usuario_id) VALUES ('DOCTOR','SURCOLOMBIANA', '2019-04-29', '2020-04-29', 'Titulo de doctor en la universidad', NOW(),NOW(), 2)

INSERT INTO expjobs (employment,company,entry,exit,description,create_at,update_at, usuario_id) VALUES ('DESARROLLADOR','GOOGLE', NOW(), NOW(), 'Desarrollador para Google', NOW(),NOW(), 1)
INSERT INTO expjobs (employment,company,entry,exit,description,create_at,update_at, usuario_id) VALUES ('PROGRAMADOR','IBM', NOW(), NOW(), 'Programador para IBM', NOW(),NOW(), 1)
INSERT INTO expjobs (employment,company,entry,exit,description,create_at,update_at, usuario_id) VALUES ('ENFERMERA','HOSPITAL', NOW(), NOW(), 'Enfermera del hospital', NOW(),NOW(), 2)
INSERT INTO expjobs (employment,company,entry,exit,description,create_at,update_at, usuario_id) VALUES ('DOCENTE','SURCOLOMBIANA', NOW(), NOW(), 'Docente de la surcolombiana', NOW(),NOW(), 2)





