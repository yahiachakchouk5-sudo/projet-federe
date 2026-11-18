-- Create Database
CREATE DATABASE GestionScolaire;
GO

USE GestionScolaire;
GO

-- 1. Table: AnnéeScolaire
CREATE TABLE AnneeScolaire (
    annee VARCHAR(20) PRIMARY KEY, -- e.g., '2025-2026'
    dateDebut DATE NOT NULL,
    dateFin DATE NOT NULL
);

-- 2. Table: Élève
CREATE TABLE Eleve (
    idEleve INT PRIMARY KEY IDENTITY(1,1),
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    dateNaissance DATE NOT NULL,
    adresse VARCHAR(255),
    telephoneParent VARCHAR(20)
);

-- 3. Table: Niveau
CREATE TABLE Niveau (
    idNiveau INT PRIMARY KEY IDENTITY(1,1),
    nomNiveau VARCHAR(100) NOT NULL
);

-- 4. Table: Inscription
CREATE TABLE Inscription (
    idInscription INT PRIMARY KEY IDENTITY(1,1),
    idEleve INT NOT NULL,
    annee VARCHAR(20) NOT NULL,
    niveauEtude VARCHAR(100) NOT NULL,
    idNiveau INT NOT NULL,
    CONSTRAINT FK_Inscription_Eleve FOREIGN KEY (idEleve) REFERENCES Eleve(idEleve),
    CONSTRAINT FK_Inscription_Annee FOREIGN KEY (annee) REFERENCES AnneeScolaire(annee),
    CONSTRAINT FK_Inscription_Niveau FOREIGN KEY (idNiveau) REFERENCES Niveau(idNiveau),
    CONSTRAINT UQ_Eleve_Annee UNIQUE (idEleve, annee) -- Rules: One enrollment per year
);

-- 5. Table: Classe
CREATE TABLE Classe (
    idClasse INT PRIMARY KEY IDENTITY(1,1),
    nomClasse VARCHAR(100) NOT NULL,
    capaciteMax INT DEFAULT 20,
    idNiveau INT NOT NULL,
    CONSTRAINT FK_Classe_Niveau FOREIGN KEY (idNiveau) REFERENCES Niveau(idNiveau)
);

-- 6. Table: AffectationÉlève
CREATE TABLE AffectationEleve (
    idAffectation INT PRIMARY KEY IDENTITY(1,1),
    idInscription INT NOT NULL,
    idClasse INT NOT NULL,
    dateAffectation DATE DEFAULT GETDATE(),
    CONSTRAINT FK_Affectation_Inscription FOREIGN KEY (idInscription) REFERENCES Inscription(idInscription),
    CONSTRAINT FK_Affectation_Classe FOREIGN KEY (idClasse) REFERENCES Classe(idClasse)
);

-- 7. Table: Matière
CREATE TABLE Matiere (
    idMatiere INT PRIMARY KEY IDENTITY(1,1),
    nomMatiere VARCHAR(100) NOT NULL,
    idNiveau INT NOT NULL,
    CONSTRAINT FK_Matiere_Niveau FOREIGN KEY (idNiveau) REFERENCES Niveau(idNiveau)
);

-- 8. Table: Enseignant
CREATE TABLE Enseignant (
    code VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    nomUtilisateur VARCHAR(50) UNIQUE NOT NULL,
    motDePasse VARCHAR(255) NOT NULL
);

-- 9. Table: Note
CREATE TABLE Note (
    idNote INT PRIMARY KEY IDENTITY(1,1),
    valeur FLOAT NOT NULL CHECK (valeur >= 0 AND valeur <= 20),
    trimestre INT NOT NULL CHECK (trimestre BETWEEN 1 AND 3),
    coefficient INT DEFAULT 1,
    idInscription INT NOT NULL,
    idMatiere INT NOT NULL,
    codeEnseignant VARCHAR(50) NOT NULL,
    CONSTRAINT FK_Note_Inscription FOREIGN KEY (idInscription) REFERENCES Inscription(idInscription),
    CONSTRAINT FK_Note_Matiere FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere),
    CONSTRAINT FK_Note_Enseignant FOREIGN KEY (codeEnseignant) REFERENCES Enseignant(code)
);

-- 10. Table: Bulletin
CREATE TABLE Bulletin (
    idBulletin INT PRIMARY KEY IDENTITY(1,1),
    idInscription INT NOT NULL,
    moyenneGenerale FLOAT,
    rangClasse INT,
    appreciation VARCHAR(MAX),
    CONSTRAINT FK_Bulletin_Inscription FOREIGN KEY (idInscription) REFERENCES Inscription(idInscription)
);

-- 11. Optional: ChargeEnseignement (Teacher assignment to subjects and classes)
CREATE TABLE ChargeEnseignement (
    codeEnseignant VARCHAR(50),
    idMatiere INT,
    idClasse INT,
    PRIMARY KEY (codeEnseignant, idMatiere, idClasse),
    CONSTRAINT FK_Charge_Enseignant FOREIGN KEY (codeEnseignant) REFERENCES Enseignant(code),
    CONSTRAINT FK_Charge_Matiere FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere),
    CONSTRAINT FK_Charge_Classe FOREIGN KEY (idClasse) REFERENCES Classe(idClasse)
);
GO

-- ==========================================
-- Sample Data Insertion
-- ==========================================

-- Insert Years
INSERT INTO AnneeScolaire (annee, dateDebut, dateFin) VALUES ('2025-2026', '2025-09-15', '2026-06-30');

-- Insert Levels
INSERT INTO Niveau (nomNiveau) VALUES ('1ère année'), ('2ème année');

-- Insert Classes
INSERT INTO Classe (nomClasse, capaciteMax, idNiveau) VALUES ('Classe A1', 20, 1), ('Classe A2', 20, 1);

-- Insert Subjects
INSERT INTO Matiere (nomMatiere, idNiveau) VALUES ('Mathématiques', 1), ('Physique', 1), ('Informatique', 1);

-- Insert Teachers
INSERT INTO Enseignant (code, nom, prenom, telephone, nomUtilisateur, motDePasse) 
VALUES ('T001', 'CHAABANE', 'Achraf', '12345678', 'achraf.c', 'password123');

-- Insert Students
INSERT INTO Eleve (nom, prenom, dateNaissance, adresse, telephoneParent) 
VALUES ('Dupont', 'Jean', '2010-05-20', 'Tunis, Avenue Habib Bourguiba', '98765432');

-- Register Student
INSERT INTO Inscription (idEleve, annee, niveauEtude, idNiveau) 
VALUES (1, '2025-2026', '1ère année GLSI', 1);

-- Assign Student to Class
INSERT INTO AffectationEleve (idInscription, idClasse) VALUES (1, 1);

-- Add Note
INSERT INTO Note (valeur, trimestre, coefficient, idInscription, idMatiere, codeEnseignant) 
VALUES (15.5, 1, 2, 1, 1, 'T001');

SELECT 'Database Ready with Sample Data' AS Status;
GO
