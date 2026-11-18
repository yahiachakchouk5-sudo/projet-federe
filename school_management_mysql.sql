-- Create Database
CREATE DATABASE IF NOT EXISTS GestionScolaire;
USE GestionScolaire;

-- 1. Table: AnneeScolaire
CREATE TABLE IF NOT EXISTS AnneeScolaire (
    annee VARCHAR(20) PRIMARY KEY,
    dateDebut DATE NOT NULL,
    dateFin DATE NOT NULL
) ENGINE=InnoDB;

-- 2. Table: Eleve
CREATE TABLE IF NOT EXISTS Eleve (
    idEleve INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    dateNaissance DATE NOT NULL,
    adresse VARCHAR(255),
    telephoneParent VARCHAR(20),
    nomUtilisateur VARCHAR(50) UNIQUE,
    motDePasse VARCHAR(255)
) ENGINE=InnoDB;

-- 3. Table: Niveau
CREATE TABLE IF NOT EXISTS Niveau (
    idNiveau INT PRIMARY KEY AUTO_INCREMENT,
    nomNiveau VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- 4. Table: Inscription
CREATE TABLE IF NOT EXISTS Inscription (
    idInscription INT PRIMARY KEY AUTO_INCREMENT,
    idEleve INT NOT NULL,
    annee VARCHAR(20) NOT NULL,
    niveauEtude VARCHAR(100) NOT NULL,
    idNiveau INT NOT NULL,
    CONSTRAINT FK_Inscription_Eleve FOREIGN KEY (idEleve) REFERENCES Eleve(idEleve) ON DELETE CASCADE,
    CONSTRAINT FK_Inscription_Annee FOREIGN KEY (annee) REFERENCES AnneeScolaire(annee) ON DELETE CASCADE,
    CONSTRAINT FK_Inscription_Niveau FOREIGN KEY (idNiveau) REFERENCES Niveau(idNiveau) ON DELETE CASCADE,
    UNIQUE KEY UQ_Eleve_Annee (idEleve, annee)
) ENGINE=InnoDB;

-- 5. Table: Classe
CREATE TABLE IF NOT EXISTS Classe (
    idClasse INT PRIMARY KEY AUTO_INCREMENT,
    nomClasse VARCHAR(100) NOT NULL,
    capaciteMax INT DEFAULT 20,
    idNiveau INT NOT NULL,
    CONSTRAINT FK_Classe_Niveau FOREIGN KEY (idNiveau) REFERENCES Niveau(idNiveau) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 6. Table: AffectationEleve
CREATE TABLE IF NOT EXISTS AffectationEleve (
    idAffectation INT PRIMARY KEY AUTO_INCREMENT,
    idInscription INT NOT NULL,
    idClasse INT NOT NULL,
    dateAffectation DATE DEFAULT (CURRENT_DATE),
    CONSTRAINT FK_Affectation_Inscription FOREIGN KEY (idInscription) REFERENCES Inscription(idInscription) ON DELETE CASCADE,
    CONSTRAINT FK_Affectation_Classe FOREIGN KEY (idClasse) REFERENCES Classe(idClasse) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 7. Table: Matiere
CREATE TABLE IF NOT EXISTS Matiere (
    idMatiere INT PRIMARY KEY AUTO_INCREMENT,
    nomMatiere VARCHAR(100) NOT NULL,
    idNiveau INT NOT NULL,
    CONSTRAINT FK_Matiere_Niveau FOREIGN KEY (idNiveau) REFERENCES Niveau(idNiveau) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 8. Table: Enseignant
CREATE TABLE IF NOT EXISTS Enseignant (
    code VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    nomUtilisateur VARCHAR(50) UNIQUE NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'TEACHER' -- ADMIN or TEACHER
) ENGINE=InnoDB;

-- 9. Table: Note
CREATE TABLE IF NOT EXISTS Note (
    idNote INT PRIMARY KEY AUTO_INCREMENT,
    valeur FLOAT NOT NULL CHECK (valeur >= 0 AND valeur <= 20),
    trimestre INT NOT NULL CHECK (trimestre BETWEEN 1 AND 3),
    coefficient INT DEFAULT 1,
    idInscription INT NOT NULL,
    idMatiere INT NOT NULL,
    codeEnseignant VARCHAR(50) NOT NULL,
    CONSTRAINT FK_Note_Inscription FOREIGN KEY (idInscription) REFERENCES Inscription(idInscription) ON DELETE CASCADE,
    CONSTRAINT FK_Note_Matiere FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere) ON DELETE CASCADE,
    CONSTRAINT FK_Note_Enseignant FOREIGN KEY (codeEnseignant) REFERENCES Enseignant(code) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 10. Table: Bulletin
CREATE TABLE IF NOT EXISTS Bulletin (
    idBulletin INT PRIMARY KEY AUTO_INCREMENT,
    idInscription INT NOT NULL,
    moyenneGenerale FLOAT,
    rangClasse INT,
    appreciation TEXT,
    CONSTRAINT FK_Bulletin_Inscription FOREIGN KEY (idInscription) REFERENCES Inscription(idInscription) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Sample Data
INSERT IGNORE INTO AnneeScolaire (annee, dateDebut, dateFin) VALUES ('2025-2026', '2025-09-15', '2026-06-30');
INSERT IGNORE INTO Niveau (nomNiveau) VALUES ('1ère année'), ('2ème année');
INSERT IGNORE INTO Classe (nomClasse, capaciteMax, idNiveau) VALUES ('Classe A1', 20, 1);
INSERT IGNORE INTO Matiere (nomMatiere, idNiveau) VALUES ('Mathématiques', 1);
INSERT IGNORE INTO Enseignant (code, nom, prenom, telephone, nomUtilisateur, motDePasse) VALUES ('T001', 'CHAABANE', 'Achraf', '12345678', 'achraf.c', 'password123');
INSERT IGNORE INTO Eleve (nom, prenom, dateNaissance, adresse, telephoneParent) VALUES ('Dupont', 'Jean', '2010-05-20', 'Tunis', '98765432');
INSERT IGNORE INTO Inscription (idEleve, annee, niveauEtude, idNiveau) VALUES (1, '2025-2026', '1ère année GLSI', 1);
INSERT IGNORE INTO AffectationEleve (idInscription, idClasse) VALUES (1, 1);
INSERT IGNORE INTO Note (valeur, trimestre, coefficient, idInscription, idMatiere, codeEnseignant) VALUES (15.5, 1, 2, 1, 1, 'T001');
