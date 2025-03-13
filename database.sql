/*
Created: 08.12.2024
Modified: 08.12.2024
Project: Kort Tenisowy
Model: Logiczny
Company: WEITI, Politechnika Warszawska
Author: Marcin Barej, Leon Zowczak
Version: 2.0
Database: Oracle 19c
*/


-- Create tables section -------------------------------------------------

-- Table Kluby_Tenisowe

CREATE TABLE Kluby_Tenisowe(
  ID_klub Integer NOT NULL,
  ID_wlasciciel Integer NOT NULL,
  ID_adres Integer NOT NULL,
  Nazwa Varchar2(100 ) NOT NULL,
  Data_zalozenia Date NOT NULL
)
/

-- Create indexes for table Kluby_Tenisowe

CREATE INDEX IX_Klub_ma_adres ON Kluby_Tenisowe (ID_adres)
/

-- Add keys for table Kluby_Tenisowe

ALTER TABLE Kluby_Tenisowe ADD CONSTRAINT Klub_TenisowyKD PRIMARY KEY (ID_klub)
/

-- Table Korty_Tenisowe

CREATE TABLE Korty_Tenisowe(
  ID_kort Integer NOT NULL,
  ID_klub Integer NOT NULL,
  ID_adres Integer,
  Nazwa Varchar2(100 ) NOT NULL,
  Adres Varchar2(400 ) NOT NULL
)
/

-- Create indexes for table Korty_Tenisowe

CREATE INDEX IX_Klub_posiada_kort ON Korty_Tenisowe (ID_klub)
/

CREATE INDEX IX_Kort_ma_adres ON Korty_Tenisowe (ID_adres)
/

-- Add keys for table Korty_Tenisowe

ALTER TABLE Korty_Tenisowe ADD CONSTRAINT Kort_TenisowyKD PRIMARY KEY (ID_kort)
/

-- Table Pracownicy

CREATE TABLE Pracownicy(
  ID_pracownik Integer NOT NULL,
  ID_klub Integer NOT NULL,
  ID_adres Integer NOT NULL,
  ID_kontakt Integer,
  Imię Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(20 ) NOT NULL,
  Data_Zatrudnienia Date NOT NULL,
  Data_Zwolnienia Date
)
/

-- Create indexes for table Pracownicy

CREATE INDEX IX_Klub_zatrudnia_pracownika ON Pracownicy (ID_klub)
/

CREATE INDEX IX_Pracownik_ma_adres ON Pracownicy (ID_adres)
/

CREATE INDEX IX_Pracownik_posiada_kontakt ON Pracownicy (ID_kontakt)
/

-- Add keys for table Pracownicy

ALTER TABLE Pracownicy ADD CONSTRAINT PracownikKD PRIMARY KEY (ID_pracownik)
/

-- Table Trenerzy

CREATE TABLE Trenerzy(
  ID_pracownik Integer NOT NULL,
  Stopien_Trenerski Varchar2(30 ) NOT NULL
        CHECK (Stopien_Trenerski IN ('Animator', 'Instruktor', 'Trener')),
  Lata_Doswiadzczenia Integer NOT NULL
)
/

-- Add keys for table Trenerzy

ALTER TABLE Trenerzy ADD CONSTRAINT TrenerKD PRIMARY KEY (ID_pracownik)
/

-- Table Grupy_Tenisowe

CREATE TABLE Grupy_Tenisowe (
  ID_Grupy Integer NOT NULL,
  ID_pracownik Integer NOT NULL,
  Max_Osob Integer NOT NULL,
  Stopien_Zaawansowania Float(2) NOT NULL
        CHECK (Stopien_Zaawansowania IN (1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0, 7.0))
)
/

-- Create indexes for table Grupy_Tenisowe

CREATE INDEX IX_Pracownik_obsluguje_grupe ON Grupy_Tenisowe (ID_pracownik)
/

-- Add keys for table Grupy_Tenisowe

ALTER TABLE Grupy_Tenisowe ADD CONSTRAINT Grupa_TenisowaKD PRIMARY KEY (ID_Grupy)
/

-- Table Klienci

CREATE TABLE Klienci(
  ID_klient Integer NOT NULL,
  ID_adres Integer NOT NULL,
  ID_kontakt Integer,
  Imię Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(20 ) NOT NULL,
  Data_Zapisu Date NOT NULL
)
/

-- Create indexes for table Klienci

CREATE INDEX IX_Klient_posiada_adres ON Klienci (ID_adres)
/

CREATE INDEX Klient_posiada_kontakt ON Klienci (ID_kontakt)
/

-- Add keys for table Klienci

ALTER TABLE Klienci ADD CONSTRAINT KlientKD PRIMARY KEY (ID_klient)
/

-- Table Pracownicy_biurowi

CREATE TABLE Pracownicy_biurowi(
  ID_pracownik Integer NOT NULL,
  Numer_Biurka Varchar2(10 ) NOT NULL,
  Dostep_Do_Sejfu Char(1 ) NOT NULL
)
/

-- Add keys for table Pracownicy_biurowi

ALTER TABLE Pracownicy_biurowi ADD CONSTRAINT Pracownik_BiurowyKD PRIMARY KEY (ID_pracownik)
/

-- Table Rezerwace

CREATE TABLE Rezerwace(
  ID_rezerwacji Integer NOT NULL,
  ID_klient Integer NOT NULL,
  ID_kort Integer NOT NULL,
  Data_Rezerwacji Date NOT NULL,
  Godzina_Rozpoczecia_Rezerwacji Timestamp(6) NOT NULL,
  Godzina_Zakonczenia_Rezerwacji Timestamp(6) NOT NULL,
  Status_Aktywnosci Char(1 ) NOT NULL
)
/

-- Create indexes for table Rezerwace

CREATE INDEX IX_Klient_robi_rezerwacje ON Rezerwace (ID_klient)
/

CREATE INDEX IX_Kort_posiada_rezerwacje ON Rezerwace (ID_kort)
/

-- Add keys for table Rezerwace

ALTER TABLE Rezerwace ADD CONSTRAINT RezerwacjaKD PRIMARY KEY (ID_rezerwacji)
/

-- Table Obsluga

CREATE TABLE Obsluga(
  ID_pracownik Integer NOT NULL,
  ID_kort Integer NOT NULL
)
/

-- Table Przynaleznosc

CREATE TABLE Przynaleznosc(
  ID_Grupy Integer NOT NULL,
  ID_klient Integer NOT NULL
)
/

-- Table Wlasciciele

CREATE TABLE Wlasciciele(
  ID_wlasciciel Integer NOT NULL,
  Imie Varchar2(30 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL
)
/

-- Add keys for table Wlasciciele

ALTER TABLE Wlasciciele ADD CONSTRAINT wlascicielKD PRIMARY KEY (ID_wlasciciel)
/

-- Table Wynagrodzenia

CREATE TABLE Wynagrodzenia(
  ID_wynagrodzenie Integer NOT NULL,
  ID_pracownik Integer NOT NULL,
  Pensja Number(10,2) NOT NULL
)
/

-- Add keys for table Wynagrodzenia

ALTER TABLE Wynagrodzenia ADD CONSTRAINT WynagrodzenieKD PRIMARY KEY (ID_wynagrodzenie,ID_pracownik)
/

-- Table Adresy

CREATE TABLE Adresy(
  ID_adres Integer NOT NULL,
  Kraj Varchar2(100 ) NOT NULL,
  Miasto Varchar2(100 ) NOT NULL,
  Ulica Varchar2(100 ) NOT NULL,
  Numer_Budynku Char(10 ) NOT NULL
)
/

-- Add keys for table Adresy

ALTER TABLE Adresy ADD CONSTRAINT PK_Adresy PRIMARY KEY (ID_adres)
/

-- Table Kontakty

CREATE TABLE Kontakty(
  ID_kontakt Integer NOT NULL,
  Numer_Telefonu Varchar2(12 ) NOT NULL,
  Adres_Email Varchar2(100 ) NOT NULL
)
/

-- Add keys for table Kontakty

ALTER TABLE Kontakty ADD CONSTRAINT PK_Kontakty PRIMARY KEY (ID_kontakt)
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE Korty_Tenisowe ADD CONSTRAINT Klub_posiada_kort FOREIGN KEY (ID_klub) REFERENCES Kluby_Tenisowe (ID_klub)
/



ALTER TABLE Grupy_Tenisowe ADD CONSTRAINT Pracownik_obsluguje_grupe FOREIGN KEY (ID_pracownik) REFERENCES Pracownicy (ID_pracownik)
/



ALTER TABLE Rezerwace ADD CONSTRAINT Klient_robi_rezerwacje FOREIGN KEY (ID_klient) REFERENCES Klienci (ID_klient)
/



ALTER TABLE Pracownicy ADD CONSTRAINT Klub_zatrudnia_pracownika FOREIGN KEY (ID_klub) REFERENCES Kluby_Tenisowe (ID_klub)
/



ALTER TABLE Rezerwace ADD CONSTRAINT Kort_posiada_rezerwacje FOREIGN KEY (ID_kort) REFERENCES Korty_Tenisowe (ID_kort)
/



ALTER TABLE Kluby_Tenisowe ADD CONSTRAINT Klub_ma_wlasciciela FOREIGN KEY (ID_wlasciciel) REFERENCES Wlasciciele (ID_wlasciciel)
/



ALTER TABLE Wynagrodzenia ADD CONSTRAINT Pracownik_ma_wynagrpodzenie FOREIGN KEY (ID_pracownik) REFERENCES Pracownicy (ID_pracownik)
/



ALTER TABLE Kluby_Tenisowe ADD CONSTRAINT Klub_ma_adres FOREIGN KEY (ID_adres) REFERENCES Adresy (ID_adres)
/



ALTER TABLE Pracownicy ADD CONSTRAINT Pracownik_ma_adres FOREIGN KEY (ID_adres) REFERENCES Adresy (ID_adres)
/



ALTER TABLE Klienci ADD CONSTRAINT Klient_posiada_adres FOREIGN KEY (ID_adres) REFERENCES Adresy (ID_adres)
/



ALTER TABLE Korty_Tenisowe ADD CONSTRAINT Kort_ma_adres FOREIGN KEY (ID_adres) REFERENCES Adresy (ID_adres)
/



ALTER TABLE Pracownicy ADD CONSTRAINT Pracownik_posiada_kontakt FOREIGN KEY (ID_kontakt) REFERENCES Kontakty (ID_kontakt)
/



ALTER TABLE Klienci ADD CONSTRAINT Klient_posiada_kontakt FOREIGN KEY (ID_kontakt) REFERENCES Kontakty (ID_kontakt)
/





