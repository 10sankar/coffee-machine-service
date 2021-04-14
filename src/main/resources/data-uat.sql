insert into ingredient (available_quantity, description, capacity, name, id, version) values (10000, 'ml', 50000, 'milk',1,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (1000, 'ml', 10000, 'water',2,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (1000, 'gm', 2000, 'coffee', 3,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (1000, 'gm', 2000, 'espresso', 4,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (1000, 'gm', 2000, 'sugar', 5,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (500, 'gm', 2008, 'cream', 6,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (2, 'gm', 20, 'low-ingredient1', 7,0);
insert into ingredient (available_quantity, description, capacity, name, id, version) values (4, 'ml', 20, 'low-ingredient2', 8,0);

insert into beverage (name, id, version) values ('low-beverage', 10,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (10,1,200,50,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (10,7,10,51,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (10,8,20,52,0);

insert into beverage (name, id, version) values ('coffee',1,0);
insert into beverage (name, id, version) values ('latte',2,0);
insert into beverage (name, id, version) values ('cappuccino',3,0);
insert into beverage (name, id, version) values ('cream-coffee',4,0);

insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (1,1,250,1,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (1,2,100,2,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (1,3,30,3,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (1,5,30,4,0);

insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (2,1,200,10,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (2,2,300,11,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (2,4,45,12,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (2,3,20,13,0);

insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (3,1,230,20,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (3,2,700,21,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (3,4,60,22,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (3,3,25,23,6);

insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (4,1,100,31,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (4,2,300, 32,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (4,3,40,33,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (4,5,10,34,0);
insert into beverage_ingredient (beverage_id, ingredient_id, required_quantity, id, version) values (4,6,50,35,0);

