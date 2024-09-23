package com.carbon.map.treasure.app;

import java.io.IOException;

/**
 * Simulation des mouvements de l'aventurier
 */
public class QueteDesTresorsMain {

	public static void main(String[] args) throws IOException {
		// Lire le fichier d'entrée à modifier si besoin
		final String locInputFileName = "data/input.txt";
		final Carte locCarte = CarteFileReader.lireFichierEntree(locInputFileName);

		// Exécution des mouvements
		locCarte.simulerMouvements();

		// Écrire le fichier de sortie avec les résultats finaux
		final String fichierSortie = "data/output.txt";
		CarteFileReader.ecrireFichierSortie(locCarte, fichierSortie);

		System.out.println("Simulation terminée. Résultats écrits dans " + fichierSortie);
	}
}
