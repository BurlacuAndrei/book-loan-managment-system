-- In case you didn't open mysql using docker-compose, uncomment line below
-- create database bookDB;

create table if not exists book (
    book_id int not null auto_increment primary key,
    book_title varchar(100) not null unique key,
    authors VARCHAR(255) not null,
    publisher VARCHAR(255) not null,
    published_year SMALLINT not null,
    total_pages_of_the_book SMALLINT not null,
    book_status ENUM('LOAN', 'REQUESTED', 'SHELF') not null default 'SHELF'
);

insert into book (book_title, authors, publisher, published_year, total_pages_of_the_book, book_status) value ('War and Peace', 'Leo Tolstoi', 'The Russian Messenger', 1869, 1225, 'LOAN');
insert into book (book_title, authors, publisher, published_year, total_pages_of_the_book, book_status) value ('Nineteen Eighty-Four', 'George Orwell', 'Secker & Warburg', 1949, 328, 'REQUESTED');
insert into book (book_title, authors, publisher, published_year, total_pages_of_the_book, book_status) value ('The Last Wish', 'Andrzej Sapkowski', 'superNOWA', 1993, 288, 'SHELF');

create table if not exists user (
    user_id int not null auto_increment primary key,
    username VARCHAR(255) not null unique key,
    password VARCHAR(255) not null,
    books_borrowed TINYINT(1) UNSIGNED not null default 0 check ( books_borrowed <= 6 ),
    is_admin BOOL default false
);

insert into user (username, password, books_borrowed, is_admin) value ('admin', 'admin', 0, true);
insert into user (username, password, books_borrowed, is_admin) value ('dummy', 'pass', 1, false);

create table if not exists borrowed_book (
    borrowed_book_id int not null auto_increment primary key,
    book_id int not null,
    user_id int not null,
    foreign key (book_id) references book(book_id),
    foreign key (user_id) references user(user_id)
);

insert into borrowed_book (book_id, user_id) value (1, 2);

