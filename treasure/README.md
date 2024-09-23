# Projet Carte au Trésor pour Carbon IT

## Description

Ce projet est une simulation d'un jeu où des aventuriers cherchent des trésors sur une carte. La carte peut contenir des montagnes et des trésors, et les aventuriers peuvent se déplacer, collecter des trésors, et éviter les obstacles.

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- [Java JDK 11 ou supérieur](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi) (si vous utilisez Maven pour la gestion des dépendances)

## Installation

1. **Clonez le dépôt :**

```bash
git clone https://github.com/yourusername/nom-du-repo.git
cd nom-du-repo
```
2. **Compilez le projet :**

Si vous utilisez Maven, exécutez la commande suivante dans le répertoire du projet :

```bash
mvn clean install
```
3. **Lancer l'application :**

Pour lancer l'application, modifier le fichier d'entrée (/data/input.txt) avec la configuration de la carte. Par exemple, le fichier input.txt peut contenir :


```css
C​ - 3 - 4
M​ - 1 - 0
M​ - 2 - 1
T​ - 0 - 3 - 2
T​ - 1 - 3 - 3
A​ - Vincent - 1 - 1 - S - AADADAGGA
```

Ensuite, utilisez la classe principale QueteDesTresorsMain.java pour lire le fichier et simuler les mouvements des aventuriers.
4. **Exécuter les tests :**

Pour exécuter les tests unitaires, assurez-vous d'être dans le répertoire du projet, puis exécutez la commande suivante :

```bash

mvn test
```

Cela exécutera tous les tests unitaires et affichera un rapport sur les résultats des tests.

**Structure du projet**

    src/main/java : Contient le code source de l'application.
    src/test/java : Contient les tests unitaires.
    src/data : Contient les fichiers de ressources (si applicable).

