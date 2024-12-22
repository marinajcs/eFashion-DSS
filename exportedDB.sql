-- H2 2.1.214;
;             
CREATE USER IF NOT EXISTS "SA" SALT '0b53477d680b6429' HASH 'c91acce1c25df8b6e362754fd026553be30f6851f9a1fbf22864ac5d3d2fb2a1' ADMIN;         
CREATE SEQUENCE "PUBLIC"."PRODUCT_SEQ" START WITH 1 RESTART WITH 16151 INCREMENT BY 50;       
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
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 97) NOT NULL,
    "USER_ID" BIGINT
);       
ALTER TABLE "PUBLIC"."BAG" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID");          
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.BAG;     
INSERT INTO "PUBLIC"."BAG" VALUES
(1, 1),
(33, 2),
(65, NULL);             
CREATE CACHED TABLE "PUBLIC"."PRODUCT"(
    "ID" BIGINT NOT NULL,
    "NAME" CHARACTER VARYING(255),
    "PRICE" FLOAT(53) NOT NULL,
    "BAG_ID" BIGINT
);              
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("ID");      
-- 7 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT; 
INSERT INTO "PUBLIC"."PRODUCT" VALUES
(3152, 'pantalones', 21.21, NULL),
(4752, 'abrigo', 66.65, NULL),
(7952, 'americana', 43.0, 33),
(14352, 'zapatos', 33.33, NULL),
(15952, 'camiseta', 10.0, NULL),
(16002, 'chaqueta', 53.22, NULL),
(16052, 'camisa', 12.0, NULL);              
CREATE CACHED TABLE "PUBLIC"."BAG_PRODUCTS"(
    "BAG_ID" BIGINT NOT NULL,
    "QUANTITY" INTEGER,
    "PRODUCT_ID" BIGINT NOT NULL
);    
ALTER TABLE "PUBLIC"."BAG_PRODUCTS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" PRIMARY KEY("BAG_ID", "PRODUCT_ID");               
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.BAG_PRODUCTS;            
INSERT INTO "PUBLIC"."BAG_PRODUCTS" VALUES
(65, 1, 4752),
(1, 2, 3152),
(1, 3, 15952),
(1, 1, 7952);      
ALTER TABLE "PUBLIC"."USERS" ADD CONSTRAINT "PUBLIC"."UK_R43AF9AP4EDM43MMTQ01ODDJ6" UNIQUE("USERNAME");       
ALTER TABLE "PUBLIC"."BAG" ADD CONSTRAINT "PUBLIC"."UK_1AV1Y7XOHPAM81BRJF4QKDA0F" UNIQUE("USER_ID");          
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."FKS00SE7A055HBGF4NC3AJMBYFO" FOREIGN KEY("BAG_ID") REFERENCES "PUBLIC"."BAG"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."BAG" ADD CONSTRAINT "PUBLIC"."FK4JQUDJJUM1XNA7CPHV53D51DY" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."USERS"("ID") NOCHECK;            
ALTER TABLE "PUBLIC"."BAG_PRODUCTS" ADD CONSTRAINT "PUBLIC"."FKIUP23VN74K20E48O9S95WQKU6" FOREIGN KEY("BAG_ID") REFERENCES "PUBLIC"."BAG"("ID") NOCHECK;      
ALTER TABLE "PUBLIC"."BAG_PRODUCTS" ADD CONSTRAINT "PUBLIC"."FKRV3XTB9BCTO0B4AJ4T3PUTLHN" FOREIGN KEY("PRODUCT_ID") REFERENCES "PUBLIC"."PRODUCT"("ID") NOCHECK;              
