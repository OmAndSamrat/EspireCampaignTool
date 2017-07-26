/*
Created: 02-01-2017
Modified: 02-01-2017
Model: Sales Campaign Tracker
Database: MySQL 5.6
*/


-- Create tables section -------------------------------------------------

-- Table Contact

CREATE TABLE `Contact`
(
  `ContactID` Int NOT NULL AUTO_INCREMENT,
  `ContactName` Varchar(50) NOT NULL,
  `ContactFirstName` Varchar(30),
  `ContactSurName` Varchar(30),
  `ContactGender` Char(1),
  `ContactEmail` Varchar(50) NOT NULL,
  `DesignationID` Int,
  `OrganisationID` Int,
  `SoftDelete` Bool,
  PRIMARY KEY (`ContactID`),
  --PRIMARY KEY (`ContactID`),
 UNIQUE `ContactID` (`ContactID`)
)
;

CREATE INDEX `IX_Relationship5` ON `Contact` (`DesignationID`)
;

CREATE INDEX `IX_Relationship8` ON `Contact` (`OrganisationID`)
;

-- Table Designation

CREATE TABLE `Designation`
(
  `DesignationID` Int NOT NULL AUTO_INCREMENT,
  `Designation` Varchar(50),
  `DesignationGroupID` Int,
  `SoftDelete` Bool,
  PRIMARY KEY (`DesignationID`)
)
;

CREATE INDEX `IX_Relationship4` ON `Designation` (`DesignationGroupID`)
;

-- Table DesignationGroup

CREATE TABLE `DesignationGroup`
(
  `DesignationGroupID` Int NOT NULL AUTO_INCREMENT,
  `DesignationGroup` Varchar(50),
  `SoftDelete` Bool,
  PRIMARY KEY (`DesignationGroupID`)
)
;

-- Table Organisation

CREATE TABLE `Organisation`
(
  `OrganisationID` Int NOT NULL AUTO_INCREMENT,
  `Organisation` Varchar(150),
  `OrganisationRemark` Varchar(500),
  `GeographyID` Int,
  `DomainID` Int,
  `SoftDelete` Bool,
  PRIMARY KEY (`OrganisationID`)
)
;

CREATE INDEX `IX_Relationship6` ON `Organisation` (`GeographyID`)
;

CREATE INDEX `IX_Relationship7` ON `Organisation` (`DomainID`)
;

-- Table Geography

CREATE TABLE `Geography`
(
  `GeographyID` Int NOT NULL AUTO_INCREMENT,
  `GeographyName` Varchar(100),
  `SoftDelete` Bool,
  PRIMARY KEY (`GeographyID`)
)
;

-- Table Domain

CREATE TABLE `Domain`
(
  `DomainID` Int NOT NULL AUTO_INCREMENT,
  `DomainName` Varchar(100),
  `DomainDescription` Varchar(300),
  `SoftDelete` Bool,
  PRIMARY KEY (`DomainID`)
)
;

-- Table Campaign

CREATE TABLE `Campaign`
(
  `CampaignID` Int NOT NULL AUTO_INCREMENT,
  `CampaignName` Varchar(300),
  `CampaignDescription` Varchar(500),
  `CampaignStartDate` Date,
  `CampaignEndDate` Date,
  `SoftDelete` Bool,
  PRIMARY KEY (`CampaignID`)
)
;

-- Table CampaignContact

CREATE TABLE `CampaignContact`
(
  `CampaignID` Int,
  `ContactID` Int,
  `SoftDelete` Bool,
  `CommunicationID` Int NOT NULL AUTO_INCREMENT,
  `IterationNumber` Tinyint,
  PRIMARY KEY (`CommunicationID`),
  PRIMARY KEY (`CommunicationID`),
 UNIQUE `CommunicationID` (`CommunicationID`)
)
;

CREATE INDEX `IX_Relationship9` ON `CampaignContact` (`CampaignID`)
;

CREATE INDEX `IX_Relationship10` ON `CampaignContact` (`ContactID`)
;

-- Table EDM

CREATE TABLE `EDM`
(
  `EDMID` Int NOT NULL,
  `EDMHTMLRaw` Varchar(10000),
  `CampaignID` Int,
  `EDMHTML` Varchar(10000),
  `SoftDelete` Bool
)
;

CREATE INDEX `IX_Relationship11` ON `EDM` (`CampaignID`)
;

ALTER TABLE `EDM` ADD  PRIMARY KEY (`EDMID`)
;

-- Table Event

CREATE TABLE `Event`
(
  `EventID` Int NOT NULL AUTO_INCREMENT,
  `EventName` Varchar(300),
  `EventDescription` Char(20),
  `SoftDelete` Bool,
  `EventGroupID` Int,
  PRIMARY KEY (`EventID`)
)
;

CREATE INDEX `IX_Relationship12` ON `Event` (`EventGroupID`)
;

-- Table EventGroup

CREATE TABLE `EventGroup`
(
  `EventGroupID` Int NOT NULL AUTO_INCREMENT,
  `EventGroup` Varchar(200),
  `SoftDelete` Bool,
  PRIMARY KEY (`EventGroupID`)
)
;

-- Table EDMEvent

CREATE TABLE `EDMEvent`
(
  `EDMID` Int,
  `EventID` Int
)
;

CREATE INDEX `IX_Relationship13` ON `EDMEvent` (`EDMID`)
;

CREATE INDEX `IX_Relationship14` ON `EDMEvent` (`EventID`)
;

-- Table Communcation

CREATE TABLE `Communcation`
(
  `ComDateTime` Date,
  `StatusID` Int,
  `CommunicationID` Int,
  `UserID` Int
)
;

CREATE INDEX `IX_Relationship15` ON `Communcation` (`StatusID`)
;

CREATE INDEX `IX_Relationship16` ON `Communcation` (`CommunicationID`)
;

CREATE INDEX `IX_Relationship17` ON `Communcation` (`UserID`)
;

-- Table Status

CREATE TABLE `Status`
(
  `StatusID` Int NOT NULL AUTO_INCREMENT,
  `Status` Varchar(100),
  `SoftDelete` Bool,
  PRIMARY KEY (`StatusID`)
)
;

-- Table User

CREATE TABLE `User`
(
  `UserID` Int NOT NULL AUTO_INCREMENT,
  `UserName` Varchar(100),
  PRIMARY KEY (`UserID`)
)
;

-- Table Role

CREATE TABLE `Role`
(
  `RoleID` Int NOT NULL AUTO_INCREMENT,
  `RoleName` Varchar(100),
  PRIMARY KEY (`RoleID`)
)
;

-- Table Privilege

CREATE TABLE `Privilege`
(
  `PrivilegeID` Int NOT NULL AUTO_INCREMENT,
  `PrivilegeName` Varchar(100),
  PRIMARY KEY (`PrivilegeID`)
)
;

-- Table PrivilegeRole

CREATE TABLE `PrivilegeRole`
(
  `RoleID` Int,
  `PrivilegeID` Int
)
;

CREATE INDEX `IX_Relationship18` ON `PrivilegeRole` (`RoleID`)
;

CREATE INDEX `IX_Relationship19` ON `PrivilegeRole` (`PrivilegeID`)
;

-- Table UserRole

CREATE TABLE `UserRole`
(
  `RoleID` Int,
  `UserID` Int
)
;

CREATE INDEX `IX_Relationship20` ON `UserRole` (`RoleID`)
;

CREATE INDEX `IX_Relationship21` ON `UserRole` (`UserID`)
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE `Designation` ADD CONSTRAINT `Relationship4` FOREIGN KEY (`DesignationGroupID`) REFERENCES `DesignationGroup` (`DesignationGroupID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Contact` ADD CONSTRAINT `Relationship5` FOREIGN KEY (`DesignationID`) REFERENCES `Designation` (`DesignationID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Organisation` ADD CONSTRAINT `Relationship6` FOREIGN KEY (`GeographyID`) REFERENCES `Geography` (`GeographyID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Organisation` ADD CONSTRAINT `Relationship7` FOREIGN KEY (`DomainID`) REFERENCES `Domain` (`DomainID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Contact` ADD CONSTRAINT `Relationship8` FOREIGN KEY (`OrganisationID`) REFERENCES `Organisation` (`OrganisationID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `CampaignContact` ADD CONSTRAINT `Relationship9` FOREIGN KEY (`CampaignID`) REFERENCES `Campaign` (`CampaignID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `CampaignContact` ADD CONSTRAINT `Relationship10` FOREIGN KEY (`ContactID`) REFERENCES `Contact` (`ContactID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `EDM` ADD CONSTRAINT `Relationship11` FOREIGN KEY (`CampaignID`) REFERENCES `Campaign` (`CampaignID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Event` ADD CONSTRAINT `Relationship12` FOREIGN KEY (`EventGroupID`) REFERENCES `EventGroup` (`EventGroupID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `EDMEvent` ADD CONSTRAINT `Relationship13` FOREIGN KEY (`EDMID`) REFERENCES `EDM` (`EDMID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `EDMEvent` ADD CONSTRAINT `Relationship14` FOREIGN KEY (`EventID`) REFERENCES `Event` (`EventID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Communcation` ADD CONSTRAINT `Relationship15` FOREIGN KEY (`StatusID`) REFERENCES `Status` (`StatusID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Communcation` ADD CONSTRAINT `Relationship16` FOREIGN KEY (`CommunicationID`) REFERENCES `CampaignContact` (`CommunicationID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `Communcation` ADD CONSTRAINT `Relationship17` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `PrivilegeRole` ADD CONSTRAINT `Relationship18` FOREIGN KEY (`RoleID`) REFERENCES `Role` (`RoleID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `PrivilegeRole` ADD CONSTRAINT `Relationship19` FOREIGN KEY (`PrivilegeID`) REFERENCES `Privilege` (`PrivilegeID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `UserRole` ADD CONSTRAINT `Relationship20` FOREIGN KEY (`RoleID`) REFERENCES `Role` (`RoleID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

ALTER TABLE `UserRole` ADD CONSTRAINT `Relationship21` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;

