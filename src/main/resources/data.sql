insert into roles
values
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

insert into users
values
    ('1999-04-02', true, true, nextval('user_id_sequence'), 'jan.kowalski@wp.pl', 'Jan', 'Kowalski', '$2a$10$r2tBnzrqH2idcnjP8fMmu.UqFn6l3.mWQ5CRwhn9JVfczcdUNU3oi', 'jan123'),
    ('1984-01-15', false, NULL, nextval('user_id_sequence'), 'zbigniew.nowak@wp.pl', 'Zbigniew', 'Nowak', '$2a$10$eYftuIDCDq23pQMc4hO.cuxDYChzlBtPX2G9UBqpyraRDLvu/s.4S', 'zbigniew123'),
    ('1980-04-21', true, false, nextval('user_id_sequence'), 'kasia.kowalska@wp.pl', 'Kasia', 'Kowalska', '$2a$10$iNMuAJ765aZRSIrwE9zfYuwP.R4.XTAD2sWHbnZiyKgGGhUExnSli', 'kasia123');

insert into user_role
values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1);