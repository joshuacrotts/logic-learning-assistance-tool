create table User
(
    UserID int auto_increment
        primary key,
    UserName varchar(255) not null,
    Password varchar(255) not null,
    LName varchar(255) null,
    FName varchar(255) null,
    constraint UserName
        unique (UserName)
);

create table Language
(
    UserID int not null
        primary key,
    Language varchar(255) null,
    constraint Language_ibfk_1
        foreign key (UserID) references User (UserID)
);

create table Query_History
(
    UserID int null,
    QueryID int auto_increment
        primary key,
    TextInput varchar(255) null,
    constraint Query_History_ibfk_1
        foreign key (UserID) references User (UserID)
);

create index UserID
	on Query_History (UserID);

create table Theme
(
    UserID int not null
        primary key,
    Theme varchar(255) default 'Default' null,
    constraint Theme_ibfk_1
        foreign key (UserID) references User (UserID)
);

