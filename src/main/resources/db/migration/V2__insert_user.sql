INSERT INTO public."user" (id, created_ts, email, password, phone, updated_ts, fcm_token)
VALUES ('U4Gnn6koanStHJUKhMmYJBh23Tq1', '2023-05-16 05:22:06.000000', 'thuyenvuanh2412@gmail.com', '123456789',
        '0795870939', '2023-05-16 05:22:55.000000',
        'daKpeSUDTeGVhowDzRJUTm:APA91bGP6OT8GVmLVzU0nSgEkQHBePDWHwU7ruZXW7-kgzEHzAEkjqilgCVF8MmRmdd0wybQC_9qns5K9HSIm3qZ69V663SOlJO1ATy_e_Q7ZWA9lvB8-4hISy8xXQfG1N2N62VR2ydZ');
-- INSERT INTO public."user" (id, created_ts, email, password, phone, updated_ts) VALUES ('48TBAFDw4zW6jKs5OBKD0wV5g7J3', '2023-05-16 05:22:06.000000', 'anhthuyn2412@gmail.com', '123456789', '0123456789', '2023-05-16 05:22:55.000000');
INSERT INTO public."species" (id, name)
values ('d5652886-f36f-11ed-a05b-0242ac120003', 'Chó');
INSERT INTO public."species" (id, name)
values ('d5652b1a-f36f-11ed-a05b-0242ac120003', 'Mèo');
INSERT INTO public."species" (id, name)
values ('d5652c64-f36f-11ed-a05b-0242ac120003', 'Cá');
INSERT INTO public."species" (id, name)
values ('d5652d9a-f36f-11ed-a05b-0242ac120003', 'Chim');
INSERT INTO public."breed" (id, name, species_id)
values ('f152bcf2-f36f-11ed-a05b-0242ac120003', 'Chó Husky', 'd5652886-f36f-11ed-a05b-0242ac120003');
INSERT INTO public."breed" (id, name, species_id)
values ('f152bfae-f36f-11ed-a05b-0242ac120003', 'Chó Alaska', 'd5652886-f36f-11ed-a05b-0242ac120003');
INSERT INTO public."breed" (id, name, species_id)
values ('f152c102-f36f-11ed-a05b-0242ac120003', 'Chó Shiba', 'd5652886-f36f-11ed-a05b-0242ac120003');
INSERT INTO public."breed" (id, name, species_id)
values ('f152c256-f36f-11ed-a05b-0242ac120003', 'Chó Poodle', 'd5652886-f36f-11ed-a05b-0242ac120003');
INSERT INTO public."breed" (id, name, species_id)
values ('b2a0db24-f577-11ed-a05b-0242ac120003', 'Mèo ta', 'd5652b1a-f36f-11ed-a05b-0242ac120003');
