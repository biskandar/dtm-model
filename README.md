# dtm-model
Directtomobile Model Module

v1.0.83

- Put prefix number on the group connection to provider bean , factory , dao , and service

- Add new library :
  . subscriber2beepcast-v1.2.40.jar
  . beepcast_database-v1.2.01.jar
  . transaction2beepcast-v1.1.37.jar
  . mysql-connector-java-5.1.37-bin.jar
  . channel2beepcast-v1.0.68.jar
  . provider2beepcast-v2.2.29.jar

v1.0.82

- Alter gateway_log table and channel_log table to use partitioning
  based on keys , with total partitions
  
  mysql> ALTER TABLE `gateway_log` PARTITION BY KEY(`log_id`) PARTITIONS 20 ;
  mysql> ALTER TABLE `channel_log` PARTITION BY KEY(`id`) PARTITIONS 10 ;

- Switch to Database Server MySQL 5.6 Port 3307

- Upgrade and use the new database library v1.2.00

- Use jdk 7

- Add new library :
  . servlet-api.jar from apache tomcat 7
  . commons-logging-1.2.jar
  . commons-pool2-2.4.2.jar
  . commons-dbcp2-2.1.1.jar
  . mysql-connector-java-5.1.35-bin.jar
  . beepcast_database-v1.2.00.jar

v1.0.81

- Add search list event based on include and exclude keywords

- Migrate all the existing projects to use IDE MyEclipse Pro 2014
  . Build as java application ( not as web project )

- Add new library :
  . provider2beepcast-v2.2.26.jar
  . router2beepcast-v1.0.38.jar

v1.0.80

- Execute sql 

    ALTER TABLE `outgoing_number_to_provider` 
      ADD COLUMN `prefix_number` VARCHAR(20) NOT NULL DEFAULT '*' AFTER `country_code` ;

- Add feature suspend for the outgoingNumberToProvider , this suspend field
  will exclude provider the filter
  
- Execute sql below 

    ALTER TABLE `outgoing_number_to_provider` 
      ADD COLUMN `suspend` BOOLEAN NOT NULL DEFAULT 0 AFTER `active` ;

- Add new library :
  . transaction2beepcast-v1.1.31.jar
  . client_request2beepcast-v1.0.05.jar
  . 

v1.0.79

- Add field "From Email Addresses" under client profile

- Execute sql below :

    ALTER TABLE `client` 
      ADD COLUMN `from_email_addresses` VARCHAR(256) AFTER `notification_mobile_numbers` ;

- Add feature to display all the member clients with state condition
  suspend , suspend_login , suspend_traffic , test , trial , etc

- Add new library :
  . 

v1.0.78

- Add new field emailBean.fromName

- Add new library :
  . 

v1.0.77

- Fixed event email to use base64 to encode the email's body
    EmailClobSupport

- Add new library :
  . channel2beepcast-v1.0.65.jar
  . router2beepcast-v1.0.36.jar
  . transaction2beepcast-v1.1.28.jar
  . provider2beepcast-v2.2.25.jar
  . subscriber2beepcast-v1.2.36.jar
  . beepcast_dbmanager-v1.1.35.jar
  . beepcast_onm-v1.2.09.jar
  . 

v1.0.76

- Provide to select gateway log bean based on provider and external message id

- Add new library :
  . 

v1.0.75

- Execute sql below

    ALTER TABLE `client` 
      ADD COLUMN `notification_email_addresses` VARCHAR(256) AFTER `phone` ,
      ADD COLUMN `notification_mobile_numbers`  VARCHAR(256) AFTER `notification_email_addresses` ;

- Execute sql below

    ALTER TABLE `special_message` 
      MODIFY COLUMN `type` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL ;

- Add record type "email_address_group" in the special messages

- Exclude "login as user" in the client profile / usage

- Include "login as user" in the original client profile / usage

- Add new library :
  . channel2beepcast-v1.0.63.jar
  . router2beepcast-v1.0.35.jar
  . transaction2beepcast-v1.1.27.jar
  . provider2beepcast-v2.2.23.jar
  . subscriber2beepcast-v1.2.35.jar
  . dwh_model-v1.0.32.jar
  . beepcast_dbmanager-v1.1.34.jar
  . 

v1.0.74

- Remove user id validation in the user log service insert 

- Execute this sql below

    ALTER TABLE `client` 
      ADD COLUMN `allow_ip_addresses` VARCHAR(128) AFTER `password_recycle_interval` ;

- Restructure role table

    ALTER TABLE `role`
      ADD COLUMN `menus` VARCHAR(256) AFTER `child_roles` ;

    ALTER TABLE `role`
      ADD COLUMN `child_roles` VARCHAR(256) AFTER `role` ;

- Add function select by clint id on usersService model

- Restructure gateway xipme table

- Execute this sql below 

    ALTER TABLE `gateway_xipme` 
      ADD COLUMN `xipme_code_encrypted` VARCHAR(20) AFTER `xipme_code` ;

- Add new library :
  . channel2beepcast-v1.0.61.jar
  . router2beepcast-v1.0.34.jar
  . transaction2beepcast-v1.1.25.jar
  . provider2beepcast-v2.2.21.jar
  . client_request2beepcast-v1.0.04.jar

v1.0.73

- Execute sql below

  ALTER TABLE `mobile_user`
    DROP INDEX `client_id_email` ,
    ADD INDEX `client_id_encrypt_email`( client_id , encrypt_email(128) ) ;

- Select function to select mobile user based on client id and email

- Add searching on user log activities

- Restructure client type model

- Add new library :
  . provider2beepcast-v2.2.20.jar

v1.0.72

- Password recycle interval

    ALTER TABLE `client` 
      ADD COLUMN `password_recycle_interval` INTEGER UNSIGNED NOT NULL DEFAULT 0 AFTER `enable_client_api` ;

- Changed the password date time

    ALTER TABLE `user` 
      ADD COLUMN `date_password_updated` DATETIME AFTER `date_updated` ;

- Changed on the Special Message back end to list all the available
  types , and names based on the table

- Add new library :
  . transaction2beepcast-v1.1.24.jar

v1.0.71

- Re structure client credit unit table

    ALTER TABLE `client_credit_unit` 
      ADD COLUMN `transaction_id` VARCHAR(20) AFTER `id` ,
      ADD COLUMN `status` VARCHAR(20) AFTER `unit` ,
      ADD COLUMN `method` VARCHAR(20) AFTER `client_id` ;

- Added new index for table user_log

    ALTER TABLE `user_log` 
      ADD INDEX `user_id_visit_id`( `user_id` , `visit_id` ) ;

- Enrich backend for client_to_senderid and client_to_number like client_keyword

- Add new library :
  . beepcast_encrypt-v1.0.04.jar
  . provider2beepcast-v2.2.19.jar
  . transaction2beepcast-v1.1.23.jar
  . channel2beepcast-v1.0.60.jar
  . router2beepcast-v1.0.33.jar
  . client2beepcast-v2.3.06.jar
  . subscriber2beepcast-v1.2.34.jar
  . beepcast_online_properties-v1.0.05.jar
  . beepcast_dbmanager-v1.1.33.jar
  . beepcast_onm-v1.2.08.jar
  
v1.0.70

- Add feature to calculate total visitors

- Add feature to support visitId in the gatewayXipmeHit

    ALTER TABLE `gateway_xipme_hit` 
      ADD COLUMN `visit_id` VARCHAR(45) AFTER `xipme_code` ;

- Add new library :
  . 

v1.0.69

- Add channel session information on the list event beans 

- Add "date" inserted and updated in the event email table 

    ALTER TABLE `event_email` 
      ADD COLUMN `date_inserted` DATETIME AFTER `email_clob` ,
      ADD COLUMN `date_updated`  DATETIME AFTER `date_inserted` ;

- Rebuild the event_email table to use id (auto inc) as primary key

    ALTER TABLE `event_email`
      ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;
      
    ALTER TABLE `event_email`
      DROP PRIMARY KEY ;
      
    ALTER TABLE `event_email`
      ADD PRIMARY KEY USING BTREE( `id` ) ;
    
    ALTER TABLE `event_email`
      DROP INDEX `id` ;
      
    ALTER TABLE `event_email`
      ADD INDEX `event_id`(`event_id`) ;

- Add new library :
  . channel2beepcast-v1.0.56.jar
  . provider2beepcast-v2.2.17.jar
  . client_request2beepcast-v1.0.03.jar
  . billing2beepcast-v1.1.07.jar
  . subscriber2beepcast-v1.2.29.jar
  . beepcast_loadmanagement-v1.2.04.jar
  . beepcast_keyword-v1.0.06.jar
  . beepcast_session-v1.0.02.jar

v1.0.68 

- Disable all the email sending and use onm instead to send 

- Add new library :
  . transaction2beepcast-v1.1.20.jar
  . client2beepcast-v2.3.04.jar
  . beepcast_onm-v1.2.07.jar

v1.0.67

- Support ssl to send email "EmailSender"

- Clean up the Event Email : Service , Model , and Bean

- Add new library :
  . channel2beepcast-v1.0.55.jar
  . router2beepcast-v1.0.31.jar
  . transaction2beepcast-v1.1.19.jar
  . client2beepcast-v2.3.03.jar
  . subscriber2beepcast-v1.2.27.jar
  . dwh_model-v1.0.29.jar
  . beepcast_online_properties-v1.0.04.jar
  . beepcast_onm-v1.2.06.jar
  . 

v1.0.66

- Execute sql below :

    ALTER TABLE `gateway_xipme`
      ADD COLUMN `xipme_master_code` VARCHAR(20) NOT NULL AFTER `gateway_xipme_id` ;
      
    ALTER TABLE `gateway_xipme_hit`
      ADD COLUMN `xipme_master_code` VARCHAR(20) NOT NULL AFTER `gateway_xipme_id` ;

    ALTER TABLE `gateway_xipme`
      MODIFY COLUMN `gateway_xipme_id` VARCHAR(20) NOT NULL ,
      DROP COLUMN `total_hits` ,
      DROP COLUMN `date_updated` ;

    CREATE TABLE `gateway_xipme_hit` (
      `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT ,
      `gateway_xipme_id` VARCHAR(20) NOT NULL ,
      `xipme_code` VARCHAR(20) NOT NULL ,
      `date_hit` DATETIME NOT NULL ,
      `date_inserted` DATETIME NOT NULL ,
      PRIMARY KEY (`id`) ,
      INDEX `gateway_xipme_id`(`gateway_xipme_id`)
    ) ;
      
      
- Add new model for "gateway_xipme" table

- There is a change inside "gateway_log" table : 
  . Add new field "gateway_xipme_id" as string

- Add new library :
  . 

v1.0.65

- List client with all profile inside like state , level , etc ...

- Add new library :
  . 

v1.0.64

- Take out the log from user login sql 

- Execute the sql below :

    UPDATE mobile_user SET
      encrypt_ic        = AES_ENCRYPT( ic        , 'Mu8wmk8E5YisvsyebTw7' ) ,
      encrypt_last_name = AES_ENCRYPT( last_name , 'louF4r0PaJrsH9KRWCoI' ) ,
      encrypt_name      = AES_ENCRYPT( name      , 'louF4r0PaJrsH9KRWCoI' ) ,
      encrypt_email     = AES_ENCRYPT( email     , 'yKwyCX4vtwVlMGXLVGT9' ) ;

- Put debug sql feature on the global propertis file

      <property field="Model.DebugAllSqlInsert" value="false"
        description="Show sql insert statement into the log file" />
      <property field="Model.DebugAllSqlUpdate" value="false"
        description="Show sql update statement into the log file" />
      <property field="Model.DebugAllSqlDelete" value="false"
        description="Show sql delete statement into the log file" />
      <property field="Model.DebugAllSqlSelect" value="false"
        description="Show sql select statement into the log file" />

- Put encryption on mobile user fields : name , ic , ...

    ALTER TABLE `mobile_user` 
      ADD COLUMN `encrypt_ic` BLOB AFTER `ic` ,
      ADD COLUMN `encrypt_last_name` BLOB AFTER `last_name` ,
      ADD COLUMN `encrypt_name` BLOB AFTER `name` ,
      ADD COLUMN `encrypt_email` BLOB AFTER `email` ;

- Hide the log info at the mobile user service level

- Put field "channelSessionId" in the insert outgoing message to gateway log service


- Add new library :
  . transaction2beepcast-v1.1.14.jar
  . router2beepcast-v1.0.30.jar
  . beepcast_encrypt-v1.0.02.jar

v1.0.63

- Clean the debug log for the gateway log table

- Add backend to support list client users based on master client 

- Use special messages table as template for support notification

  ALTER TABLE `special_message` 
    MODIFY COLUMN `content` MEDIUMTEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL ,
    MODIFY COLUMN `description` MEDIUMTEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL ,
    ADD COLUMN `active` BOOLEAN DEFAULT 0 AFTER `description` ,
    ADD COLUMN `date_inserted` DATETIME AFTER `active` ,
    ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;

- Add new library :
  . channel2beepcast-v1.0.51.jar
  . router2beepcast-v1.0.29.jar
  . transaction2beepcast-v1.1.12.jar
  . provider2beepcast-v2.2.14.jar
  . client2beepcast-v2.3.02.jar
  . subscriber2beepcast-v1.2.25.jar
  . dwh_model-v1.0.27.jar
  . beepcast_dbmanager-v1.1.31.jar
  . 

v1.0.62

- Clean up the gateway and mobile user table ( phone and message )

  gatewayLogDAO
  mobileUserDAO

- Add new library :
  . channel2beepcast-v1.0.50.jar
  . transaction2beepcast-v1.1.11.jar
  . provider2beepcast-v2.2.11.jar
  . client_request2beepcast-v1.0.02.jar
  . client2beepcast-v2.3.01.jar
  . subscriber2beepcast-v1.2.23.jar
  . beepcast_keyword-v1.0.04.jar
  . beepcast_onm-v1.2.04.jar
  . 

v1.0.61

- Add clone method for process bean 

- Add new library :
  . channel2beepcast-v1.0.49.jar
  . router2beepcast-v1.0.27.jar
  . transaction2beepcast-v1.1.07.jar
  . provider2beepcast-v2.2.10.jar
  . client2beepcast-v2.3.00.jar
  . subscriber2beepcast-v1.2.21.jar
  . dwh_model-v1.0.25.jar
  . beepcast_onm-v1.2.02.jar
  . 

v1.0.60

- Display list country sort by country name , to easier to pick up

- Put data format , and engine version under client api table and bean 

- Execute sql below :

    ALTER TABLE `client_api` 
      ADD COLUMN `mo_format` VARCHAR(20) AFTER `mo_uri` ,
      ADD COLUMN `mo_apiver` VARCHAR(20) AFTER `mo_format` ,
      ADD COLUMN `dn_format` VARCHAR(20) AFTER `dn_uri` ,
      ADD COLUMN `dn_apiver` VARCHAR(20) AFTER `dn_format` ;

- Add function to update mobile user's mobile_mccc

- Add new library :
  . 

v1.0.59

- Change backend for client to sender id :
  . Additional field "outgoing_number" shall be apply in each of the sender id
  . Put intelligent with value "*" to apply across all the field like 
    * when outgoing_number is * and sender_id is * , means that particular client can 
      change the sender_id whatever what they want to
    * when outgoing_number is * and sender_id is test , means that particular client can
      only masked with value test across all the number
    * when outgoing_number is 12345 and sender_id is * , means that particular client can
      chagne the sender_id whetever what they want but only for certain number 

- Execute sql below :

    ALTER TABLE `client_to_senderid`
      ADD COLUMN `outgoing_number` VARCHAR(20) AFTER `client_id` ;

    UPDATE client_to_senderid
      SET outgoing_number = '*' , date_updated = NOW() ;

- Add new library :
  . dwh_model-v1.0.24.jar

v1.0.58

- Take out info log for simple query event and/or client

- Add new library :
  . channel2beepcast-v1.0.47.jar
  . router2beepcast-v1.0.25.jar
  . transaction2beepcast-v1.1.03.jar
  . provider2beepcast-v2.2.06.jar
  . client2beepcast-v2.2.13.jar
  . subscriber2beepcast-v1.2.20.jar
  . dwh_model-v1.0.22.jar
  . beepcast_keyword-v1.0.03.jar
  . beepcast_database-v1.1.05.jar

v1.0.57

- Add list for user log activies for super admin purpose

- Add new library :
  . 

v1.0.56

- Can't update event when found the name is already exists

- Add new library :
  . 

v1.0.55

- Add function to add list masking number of the outgoing numbers

- Add new library :
  . 

v1.0.54

- Display list of outgoing events from event service

- Add new library :
  . 

v1.0.53

- Add function inside process bean common to get next process bean

- Add new library :
  . 

v1.0.52

- Add phone country id under gateway log

    ALTER TABLE `gateway_log` 
      ADD COLUMN `phone_country_id` INTEGER UNSIGNED NOT NULL DEFAULT 0 AFTER `encrypt_phone` ;

- Add feature to encrypt phone number and transaction content message under gateway log table

    ALTER TABLE `gateway_log`
      ADD COLUMN `encrypt_phone` VARBINARY(512) AFTER `phone` ,
      ADD COLUMN `encrypt_message` MEDIUMBLOB AFTER `message` ;

    UPDATE `gateway_log`
       SET `encrypt_phone`   = AES_ENCRYPT( `phone`   , 'jkiZmu0s575xFbgGFkdQ' ) ,
           `encrypt_message` = AES_ENCRYPT( `message` , 'ycJ3s5rUa4Bh36a7abdE' ) ;

    ALTER TABLE `gateway_log`
      ADD COLUMN `encrypt_short_code` VARBINARY(512) AFTER `short_code` ,
      ADD COLUMN `encrypt_senderID` VARBINARY(512) AFTER `senderID` ;

    UPDATE `gateway_log`
       SET `encrypt_short_code` = AES_ENCRYPT( `short_code` , 'jkiZmu0s575xFbgGFkdQ' ) ,
           `encrypt_senderID`   = AES_ENCRYPT( `senderID`   , 'jkiZmu0s575xFbgGFkdQ' ) ;
           
- Add feature to encrypt mobile number inside mobile_user table

    ALTER TABLE `mobile_user` 
      ADD COLUMN `encrypt_phone` VARBINARY(512) AFTER `phone` ;
      
    UPDATE `mobile_user` 
      SET encrypt_phone = AES_ENCRYPT( phone , 'jkiZmu0s575xFbgGFkdQ' ) ;
    
    ALTER TABLE `mobile_user` 
      ADD INDEX `client_id_encrypt_phone`(`client_id`, `encrypt_phone`) ;

    ALTER TABLE `mobile_user` 
      DROP INDEX `client_id_phone` ;     
 
- Add new library :
  . channel2beepcast-v1.0.39.jar
  . router2beepcast-v1.0.21.jar
  . transaction2beepcast-v1.0.70.jar
  . provider2beepcast-v2.2.02.jar
  . client2beepcast-v2.2.12.jar
  . subscriber2beepcast-v1.2.18.jar
  . beepcast_loadmanagement-v1.2.03.jar
  . dwh_model-v1.0.16.jar
  . beepcast_unique_code-v1.0.00.jar
  . beepcast_onm-v1.1.09.jar
  . beepcast_encrypt-v1.0.01.jar

v1.0.51

- Add feature to list all display event include the deleted one .

- Add new library :
  . transaction2beepcast-v1.0.61.jar
  . client2beepcast-v2.2.11.jar

v1.0.50

- Add table , bean , dao , and service for client file 

    CREATE TABLE `client_file` (
      `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
      `client_id` INTEGER UNSIGNED NOT NULL DEFAULT 0,
      `caption` VARCHAR(45) NOT NULL,
      `file_name` VARCHAR(256) NOT NULL,
      `file_type` VARCHAR(45) NOT NULL,
      `web_link` VARCHAR(256) NOT NULL,
      `size_bytes` INTEGER UNSIGNED NOT NULL,
      `length` INTEGER UNSIGNED NOT NULL DEFAULT 0,
      `width` INTEGER UNSIGNED NOT NULL DEFAULT 0,
      PRIMARY KEY (`id`)
    ) ENGINE = InnoDB ;

    ALTER TABLE `client_file` 
      ADD COLUMN `active` BOOLEAN NOT NULL DEFAULT 0 AFTER `width` , 
      ADD COLUMN `date_inserted` DATETIME NOT NULL AFTER `active` , 
      ADD COLUMN `date_updated` DATETIME NOT NULL AFTER `date_inserted` ;
    
- Add new library :
  . transaction2beepcast-v1.0.58.jar
  . subscriber2beepcast-v1.2.09.jar
  . beepcast_online_properties-v1.0.01.jar
  . beepcast_onm-v1.1.08.jar

v1.0.49

- Change client state :
  
    . SUSPEND -> will suspend all login and traffic
    . SUSPEND_LOGIN -> client can not login
    . SUSPEND_TRAFFIC -> block all processing traffic
    
- Add email information under client master info bean

- Add service to provide list only for client's email 

- Add new library :
  . 

v1.0.48

- Add function to list all ( active / inactive / hidden / display ) events

- Add function under ClientsService to list of active clients with specific payment type : prepaid or postpaid

- Add new library :
  . 

v1.0.47

- Provide a function to list all special messages

- Restructure special message bean , service , and dao 

- Restructure special message table to add index id field as primary key

    ALTER TABLE `special_message`
      ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;
  
    ALTER TABLE `special_message`
      DROP PRIMARY KEY ,
      ADD PRIMARY KEY USING BTREE( `id` ) ;
      
    ALTER TABLE `special_message` 
      DROP INDEX `id` ;

    ALTER TABLE `special_message`
      ADD INDEX `name`(`name`) ;
      
- Add new library :
  . subscriber2beepcast-v1.2.07.jar
  . channel2beepcast-v1.0.31.jar

v1.0.46

- Add function to get message response under event service

- Add function to update email under mobile user table

- Add new library :
  . channel2beepcast-v1.0.30.jar
  . router2beepcast-v1.0.20.jar
  . transaction2beepcast-v1.0.56.jar

v1.0.45

- Fixed the return result from userLogService

- Add new field mobile_ccnc into mobile user , 
  thru this field will track from which telco the mobile user came from .
  
- Add new library :
  . channel2beepcast-v1.0.28.jar
  . transaction2beepcast-v1.0.55.jar
  . provider2beepcast-v2.1.24.jar
  . dwh_model-v1.0.12.jar

v1.0.44

- Restructure user log table

  ALTER TABLE `user_log` 
    ADD COLUMN `active` BOOLEAN NOT NULL AFTER `action` DEFAULT 0 ,
    ADD COLUMN `date_updated` DATETIME NOT NULL AFTER `date_inserted` ;

- Add bean , dao , and model for user log

- Fixed bugs to retrieve member client balance

- Add new library :
  . billing2beepcast-v1.1.04.jar
  . subscriber2beepcast-v1.2.05.jar
  . beepcast_dbmanager-v1.1.24.jar
  
v1.0.43

- Add model , service , and bean for web transaction

- Execute sql below :

    ALTER TABLE `web_transaction`
      ADD COLUMN `status_code` VARCHAR(45) NOT NULL AFTER `message_response` ,
      ADD COLUMN `status_description` VARCHAR(128) NOT NULL AFTER `status_code` ;

- Add new library :
  . channel2beepcast-v1.0.26.jar
  . router2beepcast-v1.0.18.jar
  . transaction2beepcast-v1.0.51.jar
  . provider2beepcast-v2.1.23.jar
  . client_request2beepcast-v1.0.01.jar
  . client2beepcast-v2.2.09.jar
  . beepcast_loadmanagement-v1.2.02.jar
  . beepcast_idgen-v1.0.00.jar
  . 
  
v1.0.42

- Add feature to create incoming advanced event with one response message .
  . bundle into one function
  . in order to make easer other application to call

- Add new library :
  .   
  
v1.0.41

- Add feature to support provision for Client To Api Table

- Add new library :
  . subscriber2beepcast-v1.2.03.jar
  . beepcast_dbmanager-v1.1.22.jar
  
v1.0.40

- Add new client state named as suspend

- Give more log info for event support library

- Add new library :
  . channel2beepcast-v1.0.24.jar
  . router2beepcast-v1.0.15.jar
  . transaction2beepcast-v1.0.46.jar
  . provider2beepcast-v2.1.22.jar
  . client2beepcast-v2.2.04.jar
  . subscriber2beepcast-v1.2.00.jar
  . beepcast_dbmanager-v1.1.20.jar
  . beepcast_encrypt-v1.0.00.jar
  
v1.0.39

- Add feature to change new encrypt password , and reset password .

- Encrypt the password before insert into user .

- Separate function to update password and user profile .

- Takeout query to select password from the field .

- Add feature to perform the password validity .

- Add new library :
  . 
  
v1.0.38

- Add module to extract process beans from event

- Add new library :
  . 
  
v1.0.37

- Alter client keyword table to support country code

- Restructure provider bean , service , and dao .

- Add new library :  
  
v1.0.36

- Add RSVP event process type , and put it on event bean .

- Add new library :
  . router2beepcast-v1.0.12.jar
  . transaction2beepcast-v1.0.37.jar
  . provider2beepcast-v2.1.20.jar
  . client2beepcast-v2.2.02.jar
  . subscriber2beepcast-v1.1.03.jar
  . beepcast_dbmanager-v1.1.16.jar
  
v1.0.35

- Add service , bean and dao class for provider

- Put suspend field inside the event model , service and bean .

- Add function inside event service to update suspend status .

- Execute sql below :

    ALTER TABLE `event` 
      ADD COLUMN `suspend` BOOLEAN DEFAULT 0 AFTER `display` ;

- List event and order by date modified ( not by date inserted ) .

- Add new library :
  . transaction2beepcast-v1.0.36.jar
  
v1.0.34

- Take out all reminder model , will exchange with now reminder service module
  
v1.0.33

- Support to add model to query event codes based on event id  
  
v1.0.32

- Create new package com.beepcast.model.number
    to handle group_client_connection and client_connection
  
  CREATE TABLE `client_connection` (
    `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    `group_client_connection_id` INTEGER UNSIGNED NOT NULL,
    `number` VARCHAR(45) NOT NULL,
    `description` VARCHAR(256),
    `active` BOOLEAN NOT NULL DEFAULT 0,
    `date_inserted` DATETIME NOT NULL,
    `date_updated` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
  )
  ENGINE = InnoDB;    

- Restructure Bean , Service and DAO for 

  modem_number_to_client , client_to_number , client_to_senderid
  , and beepcode

- Restructure tables below : 
  
  ALTER TABLE `modem_number_to_client`
    ADD COLUMN `active_started` DATETIME AFTER `description` ,
    ADD COLUMN `active_stopped` DATETIME AFTER `active_started` ;
  
  UPDATE `modem_number_to_client`
    SET `active_started` = NOW() , `active_stopped` = NOW() ;
  
  CREATE TABLE `client_to_number` (
    `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT ,
    `client_id` INTEGER UNSIGNED NOT NULL ,
    `number` VARCHAR(45) NOT NULL ,
    `description` VARCHAR(256) ,
    `active_started` DATETIME ,
    `active_stopped` DATETIME ,
    `active` BOOLEAN NOT NULL DEFAULT 0 ,
    `date_inserted` DATETIME NOT NULL ,
    `date_updated` DATETIME NOT NULL ,
    PRIMARY KEY (`id`)
  ) ENGINE = InnoDB;
  
  ALTER TABLE `client_to_senderid`
    ADD COLUMN `description` VARCHAR(256) AFTER `senderID` ,
    ADD COLUMN `active_started` DATETIME AFTER `description` ,
    ADD COLUMN `active_stopped` DATETIME AFTER `active_started` ;
  
  UPDATE `client_to_senderid`
    SET `active_started` = NOW() , `active_stopped` = NOW() ;
  
  ALTER TABLE `beepcode`
    ADD COLUMN `active_started` DATETIME AFTER `last_hit_date` ,
    ADD COLUMN `active_stopped` DATETIME AFTER `active_started` ;

  UPDATE `beepcode`
    SET `active_started` = NOW() , `active_stopped` = NOW() ;
  
v1.0.31

- Add function to get list of dedicated numbers for particular client

- Add function to calculate total active events , total codes , and total keywords

- Add function to calculate total active lists , and active numbers
  
- Separated between EventBean , EventService , and EventDAO

v1.0.30

- Add bean and model for client api

- Add new library :
  . channel2beepcast-v1.0.20.jar
  . transaction2beepcast-v1.0.32.jar
  . provider2beepcast-v2.1.19.jar
  . client2beepcast-v2.2.01.jar
  . subscriber2beepcast-v1.1.02.jar
  . dwh_model-v1.0.09.jar
  . beepcast_dbmanager-v1.1.15.jar
  
v1.0.29
  
- Add client band feature inside client model and bean

    ALTER TABLE `client` 
      ADD COLUMN `state` VARCHAR(45) NOT NULL DEFAULT 'PUBLIC' AFTER `url_link` ,
      ADD COLUMN `expired` BOOLEAN NOT NULL DEFAULT 0 AFTER `display` ,
      ADD COLUMN `date_expired` DATETIME AFTER `active` ;
  
v1.0.28

- Validate function com.beepcast.model.channel.ListChannelSessionInfo -> calculateTotalDebitAmount

- Move package from com.beepcast.model.channel.* to com.beepcast.channel.view.*

- add new library :
  . beepcast_onm-v1.1.03.jar
  . beepcast_dbmanager-v1.1.11.jar
  . subscriber2beepcast-v1.0.02.jar
  . client2beepcast-v2.1.08.jar
  . provider2beepcast-v2.1.18.jar
  . transaction2beepcast-v1.0.26.jar
  . router2beepcast-v1.0.10.jar
  . channel2beepcast-v1.0.19.jar
  
v1.0.27

- fixed bean and model for new client user , 
  there are additional fields : feature_band and feature_api

- Create a list for client request for UI View

- Add new library :
  . billing2beepcast-v1.1.03.jar
  . beepcast_dbmanager-v1.1.10.jar
  . client_request2beepcast-v1.0.00.jar  
  
v1.0.26

- Change client master info model , to hide the hidden client and inactive client .

- Change primary key inside role table :

  ALTER TABLE `role`
    ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;
  
  ALTER TABLE `role`
    ADD PRIMARY KEY USING BTREE( `id` ) ;
  
  ALTER TABLE `role`
    DROP INDEX `id` ;
  
  ALTER TABLE `role`
    ADD INDEX `role` ( `role` ) ;

- Change primary key inside user table :
  
  ALTER TABLE `user`
    ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;
  
  ALTER TABLE `user`
    DROP PRIMARY KEY ,
    ADD PRIMARY KEY USING BTREE( `id` ) ;
  
  ALTER TABLE `user`
    DROP INDEX `id` ;
  
  ALTER TABLE `user`
    ADD INDEX `user_id` ( `user_id` ) ;

- Restructure role table :

  ALTER TABLE `role`
    ADD COLUMN `active` BOOLEAN NOT NULL DEFAULT 1 AFTER `role` ,
    ADD COLUMN `date_inserted` DATETIME AFTER `active` ,
    ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;

  UPDATE `role` SET `date_inserted` = NOW() , `date_updated` = NOW() ;
    
- Restructure user table :

  ALTER TABLE `user`
    ADD COLUMN `date_inserted` DATETIME AFTER `roles` ,
    ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;
  
  UPDATE `user` SET `date_inserted` = NOW() , `date_updated` = NOW() ;
  
  ALTER TABLE `user`
    ADD COLUMN `display` BOOLEAN NOT NULL DEFAULT 1 AFTER `roles` ,
    ADD COLUMN `active` BOOLEAN NOT NULL DEFAULT 1 AFTER `display` ;

- Add a service to list the member clients  

- Update Clients bean and model : 
  . add function to delete , shown and hide
  
- Restructure client table :

  ALTER TABLE `client` 
    ADD COLUMN `date_inserted` DATETIME AFTER `enable_client_api` ,
    ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;
    
  UPDATE `client` SET `date_inserted` = NOW() , `date_updated` = NOW() ;

  ALTER TABLE `client` 
    ADD COLUMN `display` BOOLEAN NOT NULL DEFAULT 1 AFTER `enable_client_api` ,
    ADD COLUMN `active` BOOLEAN NOT NULL DEFAULT 1 AFTER `display` ;
  
- Update client bean and model

  . separated : bean , service and dao object
  . add new field into bean and update the service and dao   

v1.0.25

- Update display list event model , the client can see all the events
  
v1.0.24

- fixed the gateway log model :
  . found bugs query gateway log

- update client credit unit model :
  . supported negatif unit value
  . change clientCreditUnit became clientCreditUnitService
  
v1.0.23

- move channel log model to channel library

- update channel session model to support invalid list number

- add new library :
  . router2beepcast-v1.0.07.jar
  . transaction2beepcast-v1.0.22.jar
  . subscriber2beepcast-v1.0.01.jar
  . dwh_model-v1.0.08.jar
  
v1.0.22

- add channel session id field in the gateway log bean .

- fixed gateway log model bean : service , bean , dao .
        
v1.0.21

- fixed mobile user bean , put birthday field null when there is no value .
  ( not used current date as default birthday field ) .

v1.0.20

- add total retry property in the broadcast report .

v1.0.19

- exclude the subscriber thing from the model

- add new library :
  . subscriber2beepcast-v1.0.00.jar

v1.0.18

- change client subscriber download file , change the date field

- create table and model to map between client and country

  . execute sql below :

    CREATE TABLE `client_to_country` (
      `id` INTEGER UNSIGNED NOT NULL DEFAULT NULL AUTO_INCREMENT,
      `client_id` INTEGER UNSIGNED NOT NULL DEFAULT 0,
      `country_id` INTEGER UNSIGNED NOT NULL DEFAULT 0,
      `active` BOOLEAN NOT NULL DEFAULT 0,
      `date_inserted` DATETIME NOT NULL,
      `date_updated` DATETIME NOT NULL,
      PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB;

- add new library :
  . beepcast_onm-v1.1.02.jar
  . beepcast_dbmanager-v1.1.07.jar
  . dwh_model-v1.0.05.jar
  . client2beepcast-v2.1.06.jar
  . provider2beepcast-v2.1.14.jar
  . transaction2beepcast-v1.0.19.jar
  . router2beepcast-v1.0.05.jar
  . channel2beepcast-v1.0.12.jar  
    
v1.0.17

- Integrated with new subscription model :
  . unsubscribed can be track from current list or other list ( global )
  . changed inside com.beepcast.model.channel.
    ChannelLogDAO - storeSubscriber ...

- only changed in the package com.beepcast.model.subscriber.*

- update unsubscribe module in order to track from where , it could be from
  current broadcast event or other event , execute sql below :
  
  ALTER TABLE `client_subscriber_unsubs`
    ADD COLUMN `from_event_id` INTEGER UNSIGNED NOT NULL DEFAULT 0 AFTER `phone` ;

  ALTER TABLE `client_subscriber` 
    ADD COLUMN `global_subscribed` INTEGER UNSIGNED NOT NULL DEFAULT 0 AFTER `date_subscribed` ,
    ADD COLUMN `date_global_subscribed` DATETIME AFTER `global_subscribed` ;
  
  UPDATE `client_subscriber` SET `global_subscribed` = 1 ;
  
- because there is a new field named globalUnsubscribed in the client subscriber table ,
  no need to perform set "pre-unsubs" in the description  

v1.0.16

- can not insert event lucky draw into the table

  DEBUG   {HttpProcessor[8080][5]}EventDAO Perform 
  insert into event (EVENT_NAME,CLIENT_ID,CATAGORY_ID,START_DATE,END_DATE,
                     REMIND_DATE,REMIND_FREQ,NUM_CODES,CODE_LENGTH,CODES,COMMENT,
                     PROCESS,PROCESS_TYPE,PING_COUNT,BUDGET,USED_BUDGET,CHANNEL,
                     MOBILE_MENU_ENABLE,MOBILE_MENU_NAME,OVERBUDGET_DATE,
                     UNSUBSCRIBE_IMMEDIATE,BIT_FLAGS,MOBILE_MENU_BRAND_NAME,
                     UNSUBSCRIBE_RESPONSE,OUTGOING_NUMBER,CLIENT_EVENT_ID,SENDERID,
                     DISPLAY,ACTIVE,DATE_INSERTED,DATE_UPDATED) 
             values ('Test Incoming Event Lucky Draw Benny 2',1,38,'2019-10-22 15:12:30',
                     '2019-10-22 15:12:30','2009-10-22 15:12:30','1892,1893,1894,1895,1898,1900',
                     1,4,'null','(for reference only)',
                     '3:1^CODE^(N/A)^(N/A)^Test Incoming Event Lucky Draw Benny 2 Manager^2^~2^VAR^NAMES=^V1^Test Incoming Event Lucky Draw Benny 2 Winner^END^~3^VAR^NAMES=^V2^^END^',
                     9,0,'0.0','0.0',0,0,'Test Incoming Event Lucky Draw Benny 2',
                     '1970-01-01 07:30:00',0,4,'','1','90102337','null','test',1,1,
                     '2009-10-22 15:12:30','2009-10-22 15:12:30')
  ERROR   {HttpProcessor[8080][5]}DatabaseLibrary [profiledb] Database execute query failed , 
  com.mysql.jdbc.MysqlDataTruncation: Data truncation: Data too long for column 'remind_freq' at row 1
    
v1.0.15

- when delete event , it will set active = 0  and eventName = eventName + "(deleted)" 
- add new field in the client table : balanceThreshold

v1.0.14

- fixed bugs , the list can not shown one record .
  in the ListSubscriberGroupInfo , exclude validation of subscriber active field

v1.0.13

- add new primary key id in the beepcode and last code table , with sql below :

    ALTER TABLE `beepcode`
      ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;

    ALTER TABLE `beepcode`
      DROP PRIMARY KEY ,
      ADD PRIMARY KEY USING BTREE( `id` ) ;
    
    ALTER TABLE `beepcode`
      DROP INDEX `id` ;

    ALTER TABLE `beepcode`
      ADD INDEX `code` ( `code` ) ;      
    
    ALTER TABLE `last_code`
      ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;

    ALTER TABLE `last_code`
      ADD PRIMARY KEY USING BTREE( `id` ) ;
    
    ALTER TABLE `last_code`
      DROP INDEX `id` ;      

    ALTER TABLE `last_code` 
      ADD COLUMN `date_inserted` DATETIME AFTER `last_code` ,
      ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;

- randomize direct code .
  . currently it will read sequentially
  . change the way of the read , must use randomized
  
- add new library :
  . transaction2beepcast-v1.0.14.jar
  . router2beepcast-v1.0.04.jar
  . channel2beepcast-v1.0.07.jar

v1.0.12

- change model and bean of channel session , added new field named : date_suspended .

v1.0.11

- add module paging for client credit info

- client has a date inserted and updated field , with sql below :

  ALTER TABLE `mobile_user` 
    ADD COLUMN `date_inserted` DATETIME AFTER `salutation` ,
    ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;
   
- every client has own mobile user table

  . execute sql below :
  
    ALTER TABLE `mobile_user`
      ADD COLUMN `id` INTEGER AUTO_INCREMENT UNIQUE KEY FIRST ;

    ALTER TABLE `mobile_user`
      ADD COLUMN `client_id` INTEGER UNSIGNED NOT NULL DEFAULT 1 AFTER `id` ;      
          
    ALTER TABLE `mobile_user`
      DROP PRIMARY KEY ,
      ADD PRIMARY KEY USING BTREE( `id` ) ;
    
    ALTER TABLE `mobile_user`
      DROP INDEX `id` ;
    
    ALTER TABLE `mobile_user`
      ADD INDEX `client_id_phone` ( `client_id` , `phone` ) ;
      
  . change the mobile user bean and model , must put client_id param inside update / query    

- add custRefCode in the subscriber list , and change the bean and model

    ALTER TABLE `client_subscriber` 
      ADD COLUMN `cust_ref_code` VARCHAR(45) AFTER `cust_ref_id` ;
      
- when do the migration from client_subscriber to channel_log table
  will add also field cust_ref_id , cust_ref_code , and description to 
  copy .      

- add model to download unsubs number

v1.0.10

- add function insertOutgoingMessage

- update ListChannelSessionInfo bean and model :
  . add field total debit amount
  . calculate total debit amount after create a bean   

- restructure gateway log bean and model :
  . add field message type
  . add field message count
  . add field debit amount

v1.0.09

- update event bean only can select active one

v1.0.08

- add send email property in the global env properties

- change the list subscriber db to support paging

- add fitur to hide record of event table

  . execute sql below :
  
  ALTER TABLE `event` 
    ADD COLUMN `display` TINYINT(1) DEFAULT 1 AFTER `dwh_date_updated` ;
    
  . update event bean and model  
    

v1.0.07

- change list available channels in the addChannelSessionInfo :
  . only active = 1  

- change the events model to support list events updated

  . change the eventsBean , the vector use the bean ( / not the eventName )
  . create listeventbeans to support update event / channel  

- add new library :
  . onm v1.1.00
  . loadmanagement v1.1.04
  . provider v2.1.12
  . channel v1.0.04
  . transaction v1.0.08

v1.0.06

- found bugs inside doSubscribed method ( ClientSubcriber Class )

v1.0.05

- main the client subscriber model :
  . let the model set the "pre-unsubs" in the description
  . exclude inside the model from accessing unsubs table

v1.0.04

- Validate if the event not active than it will not shown

- Fixed the event bean to add below fields

- Add field active , date_inserted , and date_updated 
  At table event , with sql below :

  ALTER TABLE `event` 
    ADD COLUMN `active` BOOLEAN DEFAULT 1 AFTER `dwh_date_updated` ,
    ADD COLUMN `date_inserted` DATETIME AFTER `active` ,
    ADD COLUMN `date_updated` DATETIME AFTER `date_inserted` ;
    
  UPDATE `event` SET `date_inserted` = NOW() , `date_updated` = NOW() ;
  
- Create model to ritrieve all events under one client with information
  . event_id
  . event_name

v1.0.03

- toString for ProcessBean's response shall use escape java

- Validate model channel :
  . daily_track if exist
  . monthly_track if exist
  . client prepaid account

- Change the client model , to support client level and client api

- used a new dbmanager lib v1.1.06

- used a new billing2beepcast v1.1.01

v1.0.02

- Can add status message for saving input message into gateway log

- Add date report created in the channel session report info bean

- Insert empty message still in the gateway log

v1.0.01

- Update com.beepcast.model.subscriber.ClientSubscriber , 
  to fixed uploading number without delete when found unsubs

v1.0.00

- add component com.beepcast.model.channel.ChannelLog* 

- remove all the beans that support access = "REMOTE"

- remove Java Applet Log Viewer com.beepcast.model.gateway.LogViewer

- remove package com.beepcast.model.maintenance

- move package reserveCode into transaction class

- added a new java library named util2beepcast-v1.0.00.jar

- Just copy all model , service and util class in the current beepcast admin .


