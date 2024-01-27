insert into roles
values
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

insert into users
values
    (true, nextval('user_id_sequence'), 'jan.kowalski@wp.pl', 'Jan', 'Kowalski', '$2a$10$r2tBnzrqH2idcnjP8fMmu.UqFn6l3.mWQ5CRwhn9JVfczcdUNU3oi', 'jan123'),
    (false, nextval('user_id_sequence'), 'zbigniew.nowak@wp.pl', 'Zbigniew', 'Nowak', '$2a$10$eYftuIDCDq23pQMc4hO.cuxDYChzlBtPX2G9UBqpyraRDLvu/s.4S', 'zbigniew123'),
    (true, nextval('user_id_sequence'), 'kasia.kowalska@wp.pl', 'Kasia', 'Kowalska', '$2a$10$iNMuAJ765aZRSIrwE9zfYuwP.R4.XTAD2sWHbnZiyKgGGhUExnSli', 'kasia123');

insert into user_role
values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1);

insert into devices
values
    (1, 'my_device_1', '00:2A:E6:3E:FD:E1'),
    (1, 'fridge_1', '00:0A:E6:54:FA:A1'),
    (1, 'fridge_2', '00:11:A6:5B:14:A2'),
    (3, 'freezer', 'BB:AA:A4:15:1F:FF');

insert into history
values
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:27:52.539065', '00:2A:E6:3E:FD:E1'),
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:37:52.539065', '00:2A:E6:3E:FD:E1'),
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:47:52.539065', '00:2A:E6:3E:FD:E1'),
    (false, nextval('history_id_sequence'), NULL, '2024-01-24 22:57:52.539065', '00:2A:E6:3E:FD:E1'),
    (NULL, nextval('history_id_sequence'), 6.04, '2024-01-24 22:27:22.539065', '00:2A:E6:3E:FD:E1'),
    (NULL, nextval('history_id_sequence'), 6.02, '2024-01-24 22:28:52.539065', '00:2A:E6:3E:FD:E1'),
    (NULL, nextval('history_id_sequence'), 5.03, '2024-01-24 22:29:52.539065', '00:2A:E6:3E:FD:E1'),

    (false, nextval('history_id_sequence'), NULL, '2024-01-24 22:27:52.539065', '00:0A:E6:54:FA:A1'),
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:37:52.539065', '00:0A:E6:54:FA:A1'),
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:47:52.539065', '00:0A:E6:54:FA:A1'),
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:57:52.539065', '00:0A:E6:54:FA:A1'),
    (NULL, nextval('history_id_sequence'), 6.04, '2024-01-24 22:27:22.539065', '00:0A:E6:54:FA:A1'),
    (NULL, nextval('history_id_sequence'), 6.02, '2024-01-24 22:28:52.539065', '00:0A:E6:54:FA:A1'),
    (NULL, nextval('history_id_sequence'), 5.03, '2024-01-24 22:29:52.539065', '00:0A:E6:54:FA:A1'),

    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:27:52.539065', 'BB:AA:A4:15:1F:FF'),
    (false, nextval('history_id_sequence'), NULL, '2024-01-24 22:37:52.539065', 'BB:AA:A4:15:1F:FF'),
    (false, nextval('history_id_sequence'), NULL, '2024-01-24 22:47:52.539065', 'BB:AA:A4:15:1F:FF'),
    (true, nextval('history_id_sequence'), NULL, '2024-01-24 22:57:52.539065', 'BB:AA:A4:15:1F:FF'),
    (NULL, nextval('history_id_sequence'), 6.04, '2024-01-24 22:27:22.539065', 'BB:AA:A4:15:1F:FF'),
    (NULL, nextval('history_id_sequence'), 6.02, '2024-01-24 22:28:52.539065', 'BB:AA:A4:15:1F:FF'),
    (NULL, nextval('history_id_sequence'), 5.03, '2024-01-24 22:29:52.539065', 'BB:AA:A4:15:1F:FF');
