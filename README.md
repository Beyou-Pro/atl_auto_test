# 🛒 E-commerce API – Projet Full Stack

Ce projet est une application e-commerce simplifiée construite avec **Java Spring Boot** pour le back-end et un front-end léger basé sur **JavaScript/HTML/CSS**.

---

## 🔧 1. Technologies utilisées

### 🖥️ Back-end
- **Langage : Java 17**
- **Framework : Spring Boot**
- **Base de données : PostgreSQL**
- **Client HTTP : FeignClient**
- **Tests : JUnit 5, AssertJ, WireMock**

**Justification :**
- Le choix de **Java Spring Boot** est directement lié à mon alternance, où je développe des APIs REST avec cette technologie. Cela me permet de consolider mes compétences tout en explorant des sujets plus poussés comme :
  - La gestion de session (panier en mémoire)
  - La communication interservices (FeignClient)
  - Les tests end-to-end avec WireMock
- Ce projet me permet également de mieux comprendre l’architecture d’une API REST complète, en simulant un microservice de catalogue produit avec JSON Server.

### 🖼️ Front-end
- **Technologie choisie : HTML / JavaScript (Vanilla)**
- **Justification :** Je souhaitais un front-end simple à mettre en place afin de concentrer mon travail sur le back-end et les tests. L’objectif était de pouvoir interagir avec l’API (ajout au panier, passage de commande) sans alourdir le projet.

---

## ⚙️ 2. Instructions de Setup

### 🐘 Prérequis
- Java 17
- Maven 3.8+
- PostgreSQL en local (ou un service cloud configuré dans `application.properties`)
- Node.js (v18+ recommandé)
- JSON Server

### 📦 Installation des dépendances

```bash
# Clone du projet
git clone https://github.com/votre-utilisateur/ecommerce-app.git
cd ecommerce-app

# Installation des dépendances back-end
mvn clean install

mvn spring-boot:run

# Lancement du json server

cd jsonserver

npm install -g json-server

json-server data.json --port 3000


# Installation des dépendances du front-end et lancement

cd frontend

npm install

npm start

```