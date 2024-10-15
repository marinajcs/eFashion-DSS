-- H2 2.1.214;
;             
CREATE USER IF NOT EXISTS "SA" SALT '0b53477d680b6429' HASH 'c91acce1c25df8b6e362754fd026553be30f6851f9a1fbf22864ac5d3d2fb2a1' ADMIN;         
CREATE SEQUENCE "PUBLIC"."PRODUCT_SEQ" START WITH 1 RESTART WITH 9601 INCREMENT BY 50;        
CREATE SEQUENCE "PUBLIC"."TODO_SEQ" START WITH 1 INCREMENT BY 50;             
CREATE CACHED TABLE "PUBLIC"."TODO"(
    "ID" BIGINT NOT NULL,
    "DESCRIPTION" CHARACTER VARYING(255),
    "DONE" BOOLEAN,
    "DUE_DATE" TIMESTAMP(6),
    "SUMMARY" CHARACTER VARYING(255)
);       
ALTER TABLE "PUBLIC"."TODO" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" PRIMARY KEY("ID");         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TODO;    
CREATE CACHED TABLE "PUBLIC"."USERS"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 34) NOT NULL,
    "PASSWORD" CHARACTER VARYING(255) NOT NULL,
    "ROLE" CHARACTER VARYING(255) NOT NULL,
    "USERNAME" CHARACTER VARYING(255) NOT NULL
);             
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID");        
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.USERS;   
INSERT INTO "PUBLIC"."USERS" VALUES
(1, '$2a$10$zhMKQPzkP5IsVFyIsVbnK.iEmC2.r4Oy6YdvbxjgZBY.hCCsXhNZ.', 'ROLE_ADMIN', 'admin'),
(2, '$2a$10$fYfOWNmkSD1yLsfF6LeGduSMqYSIdI2AiDDvyfNZawzijKOoiOr6m', 'ROLE_USER', 'user');   
CREATE CACHED TABLE "PUBLIC"."BAG"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 65) NOT NULL,
    "USER_ID" BIGINT
);       
ALTER TABLE "PUBLIC"."BAG" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID");          
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.BAG;     
INSERT INTO "PUBLIC"."BAG" VALUES
(1, 1),
(33, 2);          
CREATE CACHED TABLE "PUBLIC"."PRODUCT"(
    "ID" BIGINT NOT NULL,
    "NAME" CHARACTER VARYING(255),
    "PRICE" FLOAT(53) NOT NULL,
    "BAG_ID" BIGINT
);              
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("ID");      
-- 6 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT; 
INSERT INTO "PUBLIC"."PRODUCT" VALUES
(1552, 'zapatos', 33.33, NULL),
(1553, 'gorra', 10.0, NULL),
(1554, 'chaqueta', 52.99, 1),
(3152, 'pantalones', 21.21, NULL),
(4752, 'abrigo', 66.0, NULL),
(7952, 'americana', 40.0, 33);        
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."UK_R43AF9AP4EDM43MMTQ01ODDJ6" UNIQUE("USERNAME");       
ALTER TABLE "PUBLIC"."BAG" ADD CONSTRAINT "PUBLIC"."UK_1AV1Y7XOHPAM81BRJF4QKDA0F" UNIQUE("USER_ID");          
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."FKS00SE7A055HBGF4NC3AJMBYFO" FOREIGN KEY("BAG_ID") REFERENCES "PUBLIC"."BAG"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."BAG" ADD CONSTRAINT "PUBLIC"."FK4JQUDJJUM1XNA7CPHV53D51DY" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;            
