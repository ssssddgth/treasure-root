package com.carbon.map.treasure.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Classe permettant la lecture et l'écriture d'un fichier
 */
public class CarteFileReader {

	/**
	 * Lis le fichier en entrée et détermine un objet Carte
	 */
	public static Carte lireFichierEntree(@NonNull final String fichier) throws IOException {
		final BufferedReader locReader = new BufferedReader(new FileReader(fichier));
		Carte locCarte = null;
		String locLigne;

		while ((locLigne = locReader.readLine()) != null) {
			if (locLigne.startsWith("#") || locLigne.trim().isEmpty()) {
				continue; // Ignorer les lignes de commentaire ou vides
			}

			final String[] locElements = locLigne.split(" - ");
			switch (locElements[0].trim().replaceAll("[^\\p{Print}]", "") // Supprime les caractères non imprimables
					.replaceAll("\\s+", "")) {
			case "C": // Carte
				final int locLargeur = Integer.parseInt(locElements[1]);
				final int locHauteur = Integer.parseInt(locElements[2]);
				locCarte = new Carte(locLargeur, locHauteur);
				break;

			case "M": // Montagne
				final int locMontagneX = Integer.parseInt(locElements[1]);
				final int locMontagneY = Integer.parseInt(locElements[2]);
				locCarte.ajouterMontagne(new Position(locMontagneX, locMontagneY));
				break;

			case "T": // Trésor
				final int locTresorX = Integer.parseInt(locElements[1]);
				final int locTresorY = Integer.parseInt(locElements[2]);
				final int locNbTresors = Integer.parseInt(locElements[3]);
				locCarte.ajouterTresor(new Position(locTresorX, locTresorY), locNbTresors);
				break;

			case "A": // Aventurier
				final String locNom = locElements[1];
				final int locAventurierX = Integer.parseInt(locElements[2]);
				final int locAventurierY = Integer.parseInt(locElements[3]);
				final String locOrientation = locElements[4];
				final String locMouvements = locElements[5];
				final Aventurier locAventurier = new Aventurier(locNom, new Position(locAventurierX, locAventurierY),
						locOrientation, locMouvements);
				locCarte.ajouterAventurier(locAventurier);
				break;

			default:
				System.out.println("Ligne non reconnue : " + locLigne);
				break;
			}
		}

		locReader.close();
		return locCarte;
	}

	/**
	 * Écrit le résultat de la quête des aventuriers. Écrase le contenu déjà présent
	 * s'il y en a un.
	 */
	public static void ecrireFichierSortie(@NonNull final Carte parCarte, @NonNull final String parFichierSortie)
			throws IOException {
		final BufferedWriter locWriter = new BufferedWriter(new FileWriter(parFichierSortie));

		// Écrire les dimensions de la carte
		locWriter.write("C - " + parCarte.getLargeur() + " - " + parCarte.getHauteur());
		locWriter.newLine();

		// Écrire les montagnes
		for (final Position parMontagne : parCarte.getMontagnes()) {
			locWriter.write("M - " + parMontagne.getX() + " - " + parMontagne.getY());
			locWriter.newLine();
		}

		// Écrire les trésors restants
		for (final Map.Entry<Position, Integer> parEntry : parCarte.getTresors().entrySet()) {
			final Position locPos = parEntry.getKey();
			int locQuantite = parEntry.getValue();
			if (locQuantite > 0) {
				locWriter.write("T - " + locPos.getX() + " - " + locPos.getY() + " - " + locQuantite);
				locWriter.newLine();
			}
		}

		// Écrire les aventuriers
		for (final Aventurier parAventurier : parCarte.getAventuriers()) {
			locWriter.write("A - " + parAventurier.getNom() + " - " + parAventurier.getPosition().getX() + " - "
					+ parAventurier.getPosition().getY() + " - " + parAventurier.getOrientation() + " - "
					+ parAventurier.getTresorsCollectes());
			locWriter.newLine();
		}

		locWriter.close();
	}

}
