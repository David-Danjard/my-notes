INSERT INTO ROLE (ROLE) VALUES
('ROLE_USER');
INSERT INTO APP_USER (EMAIL, NAME, SURNAME,PASSWORD) VALUES
('david.danjard@gmail.com', 'David', 'DANJARD', '$2y$10$iK/QOse1ZH9vFayholEhcul0.N1bn/kAbYJyBX0LCYmnToF4pDIzm'),
('jane.doe@monmail.com', 'Jane', 'DOE', '$2y$10$iK/QOse1ZH9vFayholEhcul0.N1bn/kAbYJyBX0LCYmnToF4pDIzm'),
('john.doe@monmail.com', 'John', 'DOE', '$2y$10$iK/QOse1ZH9vFayholEhcul0.N1bn/kAbYJyBX0LCYmnToF4pDIzm');
INSERT INTO APP_USER_ROLE (APP_USER_ID, ROLE_ID) VALUES
(1,1),
(2,1),
(3,1);