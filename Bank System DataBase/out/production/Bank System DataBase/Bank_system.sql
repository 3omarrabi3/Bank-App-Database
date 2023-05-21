create database Bank_system
/*==============================================================*/
/* Table: ACCOUNT                                               */
/*==============================================================*/
create table ACCOUNT 
(
   ACCOUNT_NUMBER       int                            not null,
   CUSTOMER_SSN         int                            null,
   BRANCH_NUMBER        int                            null,
   TYPE                 text                           null,
   BALANCE              float                         null,
   constraint PK_ACCOUNT primary key clustered (ACCOUNT_NUMBER)
);

/*==============================================================*/
/* Table: ADMIN                                                 */
/*==============================================================*/
create table ADMIN 
(
   SSN                  int                            not null,
   FNAME                varchar(50)                    not null,
   LNAME                varchar(50)                    null,
   EMAIL                varchar(50)                    null,
   PASSWORD             varchar(50)                    null,
   constraint PK_ADMIN primary key clustered (SSN)
);

/*==============================================================*/
/* Table: BANK                                                  */
/*==============================================================*/
create table BANK 
(
   CODE                 int                            not null,
   NAME                 varchar(50)                    not null,
   CITY                 varchar(50)                    null,
   COUNTRY              varchar(50)                    null,
   STREET               varchar(50)                    null,
   constraint PK_BANK primary key clustered (CODE)
);

/*==============================================================*/
/* Table: BRANCH                                                */
/*==============================================================*/
create table BRANCH 
(
   BRANCH_NUMBER        int                            not null,
   BANK_CODE            int                            null,
   NAME                 varchar(25)                    null,
   COUNTRY              varchar(50)                    null,
   STREET               varchar(50)                    null,
   CITY                 varchar(50)                    null,
   constraint PK_BRANCH primary key (BRANCH_NUMBER)
);

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table CUSTOMER 
(
   CUSTOMER_SSN         int                            not null,
   BRANCH_NUMBER        int                            null,
   FNAME                varchar(50)                    null,
   LNAME                varchar(50)                    null,
   BUILDING_NUMBER      int                            null,
   COUNTRY              varchar(50)                    null,
   CITY                 varchar(50)                    null,
   STREET               varchar(50)                    null,
   PHONE                varchar(11)                null,
   EMAIL                varchar(25)                    null,
   PASSWORD             varchar(25)                    null,
   constraint PK_CUSTOMER primary key clustered (CUSTOMER_SSN)
);
ALTER TABLE CUSTOMER
ADD CONSTRAINT CK_PhoneNumber
CHECK (Phone LIKE '0%')

/*==============================================================*/
/* Table: EMPLOYEE                                              */
/*==============================================================*/
create table EMPLOYEE 
(
   EMPLOYEE_SSN         int                            not null,
   BRANCH_NUMBER        int                            null,
   FNAME                varchar(50)                    null,
   LNAME                varchar(50)                    null,
   SALARY               float                          null,
   EMAIL                varchar(50)                    null,
   PASSWORD             text                           null,
   constraint PK_EMPLOYEE primary key clustered (EMPLOYEE_SSN)
);

/*==============================================================*/
/* Table: LOAN                                                  */
/*==============================================================*/
create table LOAN 
(
   LOAN_NUMBER          int                            not null,
   BRANCH_NUMBER        int                            null,
   LOAN_AMOUNT          float                          null,
   LOAN_TYPE            varchar(50)                    null,
   constraint PK_LOAN primary key clustered (LOAN_NUMBER)
);

/*==============================================================*/
/* Table: LOANREQUESTS                                          */
/*==============================================================*/
create table LOANREQUESTS 
(
   LOAN_NUMBER          int                            not null,
   CUSTOMER_SSN         int                            not null,
   STATUS               varchar(10)                    null,
   constraint PK_LOANREQUESTS primary key clustered (LOAN_NUMBER, CUSTOMER_SSN)
);

/*==============================================================*/
/* Table: MAINTAIN                                              */
/*==============================================================*/
create table MAINTAIN 
(
   BRANCH_NUMBER        int                            not null,
   CODE                 int                            not null,
   ACCOUNT_NUMBER       int                            not null,
   constraint PK_MAINTAIN primary key clustered (BRANCH_NUMBER, CODE, ACCOUNT_NUMBER)
);

alter table ACCOUNT
   add constraint FK_ACCOUNT_ASSIGNTO_CUSTOMER foreign key (CUSTOMER_SSN)
      references CUSTOMER (CUSTOMER_SSN)
      on update cascade
      on delete set null

alter table ACCOUNT
   add constraint FK_ACCOUNT_MAINTAINB_BRANCH foreign key (BRANCH_NUMBER)
      references BRANCH (BRANCH_NUMBER)
      on update cascade
      on delete set null

alter table BRANCH
   add constraint FK_BRANCH_BELONGSTO_BANK foreign key (BANK_CODE)
      references BANK (CODE)
      on update cascade
      on delete set null;


alter table CUSTOMER
   add constraint FK_CUSTOMER_BELONGSTO_BRANCH foreign key (BRANCH_NUMBER)
      references BRANCH (BRANCH_NUMBER)

alter table EMPLOYEE
   add constraint FK_EMPLOYEE_WORKSFOR_BRANCH foreign key (BRANCH_NUMBER)
      references BRANCH (BRANCH_NUMBER)
         on update cascade
      on delete set null

alter table LOAN
   add constraint FK_LOAN_ASSIGNTO_BRANCH foreign key (BRANCH_NUMBER)
      references BRANCH (BRANCH_NUMBER)
         on update cascade
      on delete set null

alter table LOANREQUESTS
   add constraint FK_LOANREQU_HAS_CUSTOMER foreign key (CUSTOMER_SSN)
      references CUSTOMER (CUSTOMER_SSN)
         on update cascade

alter table LOANREQUESTS
   add constraint FK_LOANREQU_HAS_LOAN foreign key (LOAN_NUMBER)
      references LOAN (LOAN_NUMBER)
          on update cascade
select * from ACCOUNT
select * from EMPLOYEE
select * from CUSTOMER
select * from BANK
select * from BRANCH
select * from LOANREQUESTS
select * from LOAN