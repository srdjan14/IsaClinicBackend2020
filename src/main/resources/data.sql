insert into clinic_center (id) values (100);

insert into clinic (id, clinic_center_id, name, address, description, version, x , y) values
( 30 , null, 'Klinika 1', 'Adresa 1', 'Opis klinike 1', null, 45.29, 19.80),
( 31 , null, 'Klinika 2', 'Adresa 2', 'Opis klinike 2', null, 45.26, 19.80),
( 32 , null, 'Klinika 3', 'Adresa 3', 'Opis klinike 3', null, 45.25, 19.83);
--  ( 33 , null, 'Klinika 4', 'Adresa 4', 'Opis klinike 4', null, 45.28, 19.79),
--  ( 34 , null, 'Klinika 5', 'Adresa 5', 'Opis klinike 5', null, 45.31, 19.84),
--  ( 35 , null, 'Klinika 6', 'Adresa 6', 'Opis klinike 6', null, 45.28, 19.77);

insert into admin(id, admin_type, clinic_id) values
( 1 , 'CLINIC_CENTER_ADMIN', 30),
( 2 , 'CLINIC_ADMIN', 30),
( 3 , 'CLINIC_ADMIN', 30),
( 4 , 'CLINIC_ADMIN', 31),
( 5, 'CLINIC_ADMIN', 31),
( 6 , 'CLINIC_ADMIN', 32),
( 7 , 'CLINIC_ADMIN', 32);

insert into examination_type(id, name, price, deleted_status) values
(40, 'Ortopedski', 5000, 'NOT_DELETED'),
(41, 'Otorinolaringolog', 4000, 'NOT_DELETED'),
(42, 'Dermatolog', 2500, 'NOT_DELETED'),
(43, 'Kardiolog', 6100, 'NOT_DELETED'),
(44, 'Ginekolog', 3000, 'NOT_DELETED'),
(45, 'Urolog', 2500, 'NOT_DELETED');

insert into medical_staff(id, medical_type, clinic_id, examination_type_id, start_work_at, end_work_at) values
( 14 , 'DOCTOR', 30, 40, "08:00", "20:00"),
( 15 , 'DOCTOR', 30, 41,  "08:00", "20:00"),
( 16 , 'DOCTOR', 31, 42, "08:00", "20:00"),
( 17 , 'DOCTOR', 31, 43, "08:00", "20:00"),
( 18 , 'DOCTOR', 32, 44, "08:00", "20:00"),
( 19 , 'DOCTOR', 32, 45, "08:00", "20:00");

insert into patient(id, active) values
( 8 , true),
( 9 , true),
( 10 , true),
( 11, true),
( 12 , true),
( 13 , true);

insert into operation_room(id, number, name, clinic_id, deleted_status, version) values
( 50, '1', 'soba1', 30, 'NOT_DELETED', null),
( 51, '2', 'soba2', 30, 'NOT_DELETED', null),
( 52, '3', 'soba3', 31, 'NOT_DELETED', null),
( 53, '4', 'soba4', 31, 'NOT_DELETED', null),
( 54, '5', 'soba5', 32, 'NOT_DELETED', null),
( 55, '6', 'soba6', 32, 'NOT_DELETED', null);

--insert into registration_request (id, address, city, country, email, first_name, last_name, password, phone, ssn, status) values
--( 60, 'adresa', 'city', 'country', 'email1@test.com', 'firstName', 'lastName', 'password', '1241241', '2141241', 'PENDING'),
--( 61, 'adresa1', 'city1', 'country1', 'email2@test.com', 'firstName2', 'lastName2', 'password2', '1212441241', '2141241124', 'PENDING');