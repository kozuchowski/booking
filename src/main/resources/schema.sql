

drop table if exists facilities CASCADE;


drop table if exists landlords CASCADE;


drop table if exists reservations CASCADE;


drop table if exists tenants CASCADE;


drop sequence if exists hibernate_sequence;
     create sequence hibernate_sequence start with 1 increment by 1;


create table facilities (
                            id bigint not null,
                            area_in_meters double not null,
                            created_on timestamp,
                            description varchar(255),
                            gross_price_per_day_in_pln numeric(19,2),
                            name varchar(255),
                            landlord_id bigint,
                            primary key (id)
);


create table landlords (
                           id bigint not null,
                           created_on timestamp,
                           name varchar(255),
                           primary key (id)
);


create table reservations (
                              id bigint not null,
                              created_on timestamp,
                              end_date timestamp,
                              start_date timestamp,
                              summary_price numeric(19,2),
                              tenancy_period_in_days bigint not null,
                              facility_id bigint,
                              tenant_id bigint,
                              primary key (id)
);


create table tenants (
                         id bigint not null,
                         created_on timestamp,
                         name varchar(255),
                         primary key (id)
);


alter table facilities
    add constraint FKhd0enx0sav93thioj7jfkf2il
        foreign key (landlord_id)
            references landlords;


alter table reservations
    add constraint FKd5gn5n5tnrf5l16o9koaqxsok
        foreign key (facility_id)
            references facilities;


alter table reservations
    add constraint FKijxli8hjdst4on1h3au936a17
        foreign key (tenant_id)
            references tenants;