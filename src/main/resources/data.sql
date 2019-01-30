-- normalize csv into lookup tables

truncate table `abcproducts`.`BodyLocation`;
truncate table `abcproducts`.`ProductCategory`;
truncate table `abcproducts`.`Company`;
truncate table `abcproducts`.`Product`;
truncate table `abcproducts`.`ProductSource`;

insert into `abcproducts`.`ProductSource` (source_name) select distinct trim(Source) from `abcproducts`.`WearablesRaw`;

insert into `abcproducts`.`BodyLocation` (body_location_name) select distinct trim(Body_Location) from `abcproducts`.`WearablesRaw`;

insert into `abcproducts`.`ProductCategory` (category_name) select distinct trim(Category) from `abcproducts`.`WearablesRaw` where Category is not null and Category != '';

insert into `abcproducts`.`Company` (company_name,company_url,company_mapping_location,company_city,company_u_s_state,company_country)
   select distinct Company_Name, Company_URL, Company_Mapping_Location, Company_City, Company_U_S_State, Company_Country from `abcproducts`.`WearablesRaw` where Company_Name is not null;

insert into `abcproducts`.`Product` (
             product_name, 
             product_price, 
             body_location_id, 
             category_id, 
             source_id, 
             product_link,
             product_image, 
             company_id, 
             original_id)
    select Name, 
           cast(replace(replace(trim(Price), '$'), ',') AS DECIMAL(6,2)),
           (select bl.body_location_id from `abcproducts`.`BodyLocation` bl where bl.body_location_name = wr.Body_Location), 
           (select pc.category_id from `abcproducts`.`ProductCategory` pc where pc.category_name = wr.Category),
           (select ps.source_id from `abcproducts`.`ProductSource` ps where ps.source_name = wr.Source),
           Link,
           Image,
           (select c.company_id from `abcproducts`.`Company` c where c.company_name = wr.Company_Name and c.company_url = wr.Company_URL and c.company_mapping_location = wr.Company_Mapping_Location),
           id
    from `abcproducts`.`WearablesRaw` wr;

    