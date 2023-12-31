insert into sources values
    (default, 'Source 1', 180000, 150000, 50),
    (default, 'Source 2', 200000, 170000, 50),
    (default, 'Source 3', 120000, 100000, 50);

insert into salles values
    (default, 'Grande Salle', 1),
    (default, 'Salle 1', 2),
    (default, 'Salle 2', 2),
    (default, 'Amphi C', 3),
    (default, 'BICI', 3);

insert into pointages values
    (default, 1, 307, 295, '2023-11-30'),
    (default, 1, 317, 285, '2023-12-01'),
    (default, 1, 297, 291, '2023-12-04'),
    (default, 1, 294, 292, '2023-12-05'),
    (default, 1, 301, 294, '2023-12-06'),
    (default, 1, 312, 305, '2023-12-07'),
    (default, 1, 309, 290, '2023-12-08'),
    (default, 1, 312, 298, '2023-12-11'),
    (default, 1, 308, 289, '2023-12-12'),
    (default, 1, 302, 297, '2023-12-13'),

    (default, 2, 135, 123, '2023-11-30'),
    (default, 2, 143, 139, '2023-12-01'),
    (default, 2, 134, 132, '2023-12-04'),
    (default, 2, 132, 141, '2023-12-05'),
    (default, 2, 129, 140, '2023-12-06'),
    (default, 2, 145, 138, '2023-12-07'),
    (default, 2, 149, 129, '2023-12-08'),
    (default, 2, 142, 112, '2023-12-11'),
    (default, 2, 139, 124, '2023-12-12'),
    (default, 2, 124, 153, '2023-12-13'),

    (default, 3, 126, 114, '2023-11-30'),
    (default, 3, 121, 118, '2023-12-01'),
    (default, 3, 122, 120, '2023-12-04'),
    (default, 3, 123, 112, '2023-12-05'),
    (default, 3, 127, 108, '2023-12-06'),
    (default, 3, 108, 103, '2023-12-07'),
    (default, 3, 109, 101, '2023-12-08'),
    (default, 3, 112, 121, '2023-12-11'),
    (default, 3, 105, 102, '2023-12-12'),
    (default, 3, 107, 104, '2023-12-13'),

    (default, 4, 108, 115, '2023-11-30'),
    (default, 4, 103, 101, '2023-12-01'),
    (default, 4, 104, 114, '2023-12-04'),
    (default, 4, 111, 108, '2023-12-05'),
    (default, 4, 102, 152, '2023-12-06'),
    (default, 4, 104, 103, '2023-12-07'),
    (default, 4, 109, 109, '2023-12-08'),
    (default, 4, 103, 119, '2023-12-11'),
    (default, 4, 114, 114, '2023-12-12'),
    (default, 4, 118, 117, '2023-12-13'),

    (default, 5, 32, 33, '2023-11-30'),
    (default, 5, 31, 31, '2023-12-01'),
    (default, 5, 32, 32, '2023-12-04'),
    (default, 5, 32, 34, '2023-12-05'),
    (default, 5, 31, 32, '2023-12-06'),
    (default, 5, 34, 30, '2023-12-07'),
    (default, 5, 33, 29, '2023-12-08'),
    (default, 5, 31, 29, '2023-12-11'),
    (default, 5, 32, 32, '2023-12-12'),
    (default, 5, 30, 34, '2023-12-13');

insert into meteos values
    (default, '2023-11-30 08:00:00', 4),
    (default, '2023-11-30 09:00:00', 5),
    (default, '2023-11-30 10:00:00', 6),
    (default, '2023-11-30 11:00:00', 7),
    (default, '2023-11-30 12:00:00', 8),
    (default, '2023-11-30 13:00:00', 8),
    (default, '2023-11-30 14:00:00', 6),
    (default, '2023-11-30 15:00:00', 5),
    (default, '2023-11-30 16:00:00', 4),

    (default, '2023-12-01 08:00:00', 4),
    (default, '2023-12-01 09:00:00', 5),
    (default, '2023-12-01 10:00:00', 7),
    (default, '2023-12-01 11:00:00', 7),
    (default, '2023-12-01 12:00:00', 8),
    (default, '2023-12-01 13:00:00', 6),
    (default, '2023-12-01 14:00:00', 5),
    (default, '2023-12-01 15:00:00', 5),
    (default, '2023-12-01 16:00:00', 4),

    (default, '2023-12-04 08:00:00', 4),
    (default, '2023-12-04 09:00:00', 5),
    (default, '2023-12-04 10:00:00', 6),
    (default, '2023-12-04 11:00:00', 7),
    (default, '2023-12-04 12:00:00', 8),
    (default, '2023-12-04 13:00:00', 8),
    (default, '2023-12-04 14:00:00', 5),
    (default, '2023-12-04 15:00:00', 5),
    (default, '2023-12-04 16:00:00', 4),

    (default, '2023-12-05 08:00:00', 5),
    (default, '2023-12-05 09:00:00', 5),
    (default, '2023-12-05 10:00:00', 5),
    (default, '2023-12-05 11:00:00', 5),
    (default, '2023-12-05 12:00:00', 6),
    (default, '2023-12-05 13:00:00', 7),
    (default, '2023-12-05 14:00:00', 6),
    (default, '2023-12-05 15:00:00', 5),
    (default, '2023-12-05 16:00:00', 4),

    (default, '2023-12-06 08:00:00', 4),
    (default, '2023-12-06 09:00:00', 5),
    (default, '2023-12-06 10:00:00', 5),
    (default, '2023-12-06 11:00:00', 7),
    (default, '2023-12-06 12:00:00', 8),
    (default, '2023-12-06 13:00:00', 8),
    (default, '2023-12-06 14:00:00', 6),
    (default, '2023-12-06 15:00:00', 5),
    (default, '2023-12-06 16:00:00', 4),

    (default, '2023-12-07 08:00:00', 5),
    (default, '2023-12-07 09:00:00', 5),
    (default, '2023-12-07 10:00:00', 6),
    (default, '2023-12-07 11:00:00', 6),
    (default, '2023-12-07 12:00:00', 7),
    (default, '2023-12-07 13:00:00', 8),
    (default, '2023-12-07 14:00:00', 8),
    (default, '2023-12-07 15:00:00', 7),
    (default, '2023-12-07 16:00:00', 6),

    (default, '2023-12-08 08:00:00', 4),
    (default, '2023-12-08 09:00:00', 5),
    (default, '2023-12-08 10:00:00', 5),
    (default, '2023-12-08 11:00:00', 6),
    (default, '2023-12-08 12:00:00', 5),
    (default, '2023-12-08 13:00:00', 5),
    (default, '2023-12-08 14:00:00', 6),
    (default, '2023-12-08 15:00:00', 5),
    (default, '2023-12-08 16:00:00', 4),

    (default, '2023-12-11 08:00:00', 4),
    (default, '2023-12-11 09:00:00', 5),
    (default, '2023-12-11 10:00:00', 6),
    (default, '2023-12-11 11:00:00', 7),
    (default, '2023-12-11 12:00:00', 8),
    (default, '2023-12-11 13:00:00', 9),
    (default, '2023-12-11 14:00:00', 7),
    (default, '2023-12-11 15:00:00', 6),
    (default, '2023-12-11 16:00:00', 5),

    (default, '2023-12-12 08:00:00', 4),
    (default, '2023-12-12 09:00:00', 6),
    (default, '2023-12-12 10:00:00', 6),
    (default, '2023-12-12 11:00:00', 5),
    (default, '2023-12-12 12:00:00', 6),
    (default, '2023-12-12 13:00:00', 7),
    (default, '2023-12-12 14:00:00', 6),
    (default, '2023-12-12 15:00:00', 5),
    (default, '2023-12-12 16:00:00', 4),

    (default, '2023-12-13 08:00:00', 4),
    (default, '2023-12-13 09:00:00', 5),
    (default, '2023-12-13 10:00:00', 6),
    (default, '2023-12-13 11:00:00', 8),
    (default, '2023-12-13 12:00:00', 7),
    (default, '2023-12-13 13:00:00', 6),
    (default, '2023-12-13 14:00:00', 5),
    (default, '2023-12-13 15:00:00', 5),
    (default, '2023-12-13 16:00:00', 4),

    (default, '2023-12-14 08:00:00', 4),
    (default, '2023-12-14 09:00:00', 6),
    (default, '2023-12-14 10:00:00', 7),
    (default, '2023-12-14 11:00:00', 6),
    (default, '2023-12-14 12:00:00', 5),
    (default, '2023-12-14 13:00:00', 6),
    (default, '2023-12-14 14:00:00', 5),
    (default, '2023-12-14 15:00:00', 5),
    (default, '2023-12-14 16:00:00', 5);

insert into coupures values
    (default, 1, '2023-11-30 14:45:00'),
    (default, 1, '2023-12-01 15:57:00'),
    (default, 1, '2023-12-04 11:05:00'),
    (default, 1, '2023-12-05 13:08:00'),
    (default, 1, '2023-12-06 11:23:00'),
    (default, 1, '2023-12-07 15:47:00'),
    (default, 1, '2023-12-08 13:52:00'),
    (default, 1, '2023-12-11 12:39:00'),
    (default, 1, '2023-12-12 16:19:00'),
    (default, 1, '2023-12-13 14:08:00'),

    (default, 2, '2023-11-30 16:04:00'),
    (default, 2, '2023-12-01 11:10:00'),
    (default, 2, '2023-12-04 12:43:00'),
    (default, 2, '2023-12-05 14:28:00'),
    (default, 2, '2023-12-06 15:59:00'),
    (default, 2, '2023-12-07 12:10:00'),
    (default, 2, '2023-12-08 11:48:00'),
    (default, 2, '2023-12-11 14:51:00'),
    (default, 2, '2023-12-12 12:36:00'),
    (default, 2, '2023-12-13 16:12:00'),

    (default, 3, '2023-11-30 12:19:00'),
    (default, 3, '2023-12-01 15:56:00'),
    (default, 3, '2023-12-04 11:00:00'),
    (default, 3, '2023-12-05 10:17:00'),
    (default, 3, '2023-12-06 11:31:00'),
    (default, 3, '2023-12-07 12:06:00'),
    (default, 3, '2023-12-08 14:01:00'),
    (default, 3, '2023-12-11 16:37:00'),
    (default, 3, '2023-12-12 14:31:00'),
    (default, 3, '2023-12-13 12:17:00');