create table product (
                         id smallint primary key ,
                         description varchar(50) not null,
                         price numeric(5, 2) check ( price > 0 ),
                         available_number smallint check ( available_number >= 0 ),
                         is_special_offer boolean default false
);

create table discount_card(
card_name VARCHAR(10) PRIMARY KEY,
discount_percent SMALLINT CHECK ( discount_percent BETWEEN 1 AND 100 )
);

insert into product (id, description, price, available_number, is_special_offer)
values (1, 'Lollipop', 1.07, 20, false),
       (2, 'Popcorn', 2.10, 20, true),
       (3, 'Gingerbread', 2.50, 20, false),
       (4, 'Yogurt', 1.50, 20, true),
       (5, 'Biscuit', 1.48, 20, true),
       (6, 'Cake', 18.65, 20, false),
       (7, 'Chocolate', 3.10, 20, false),
       (8, 'Croissant', 1.20, 20, false),
       (9, 'Jam', 3.41, 20, false),
       (10, 'Marshmallow', 1.48, 20, false),
       (11, 'Waffle', 2.63, 20, true),
       (12, 'Brownie', 1.79, 20, false),
       (13, 'Bun', 0.62, 20, true),
       (14, 'Eclair', 1.90, 20, true),
       (15, 'Gum', 1.00, 20, false),
       (16, 'Macaroon', 5.52, 20, false),
       (17, 'Marmalade', 2.70, 20, false),
       (18, 'Lemonade', 1.62, 20, true),
       (19, 'Juice', 3.00, 20, false),
       (20, 'Coke', 2.50, 20, false),
       (21, 'Honey', 10.25, 8, false),
       (22, 'Candy', 1.46, 9, true),
       (23, 'Pie', 3.69, 15, false),
       (24, 'Cracker', 4.87, 14, false),
       (25, 'Cupcake', 2.40, 16, false),
       (26, 'Chocolate', 4.58, 16, true),
       (27, 'Cinnamon', 2.90, 12, false),
       (28, 'Donut', 2.14, 20, false),
       (29, 'Milkshake', 2.17, 19, false),
       (30, 'Muffin', 3.17, 17,  false),
       (31, 'Pudding', 1.87, 18, false),
       (32, 'Toffee', 1.80, 10, false),
       (33, 'Pretzel', 1.10, 12, false),
       (34, 'Caramel', 3.19, 19, true),
       (35, 'Cheesecake', 6.59, 19, true),
       (36, 'Jelly', 1.49, 17, true),
       (37, 'Oreo', 2.52, 19, false),
       (38, 'Tiramisu', 4.50, 19, false),
       (39, 'Cocoa', 1.50, 19, true),
       (40, 'Tea', 1.20, 20, false);

insert into discount_card (card_name, discount_percent)
values ('CARD1', 1),
       ('CARD2', 2),
       ('CARD3', 3),
       ('CARD4', 4),
       ('CARD5', 5),
       ('CARD6', 6),
       ('CARD7', 7),
       ('CARD8', 8),
       ('CARD9', 9);
