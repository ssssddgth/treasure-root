package com.carbon.map.treasure;

import org.junit.jupiter.api.*;

import com.carbon.map.treasure.app.Aventurier;
import com.carbon.map.treasure.app.Carte;
import com.carbon.map.treasure.app.CarteFileReader;
import com.carbon.map.treasure.app.Position;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CarteFileReaderTest {

	private static final String FICHIER_ENTREE = "data/test_fichier_entree.txt";
	private static final String FICHIER_SORTIE = "data/test_fichier_sortie.txt";
	private Carte _carte;

	@BeforeEach
	public void setup() {
		// Créer une carte de test
		_carte = new Carte(3, 3);
		_carte.ajouterMontagne(new Position(1, 1));
		_carte.ajouterTresor(new Position(2, 2), 1);
		_carte.ajouterAventurier(new Aventurier("Lara", new Position(0, 0), "E", "AA"));
	}

	@AfterEach
	public void tearDown() {
		// Supprimer les fichiers temporaires après chaque test
		new File(FICHIER_ENTREE).delete();
		new File(FICHIER_SORTIE).delete();
	}

	@Test
	public void testLireFichierEntree() throws IOException {
		// Écrire le contenu dans le fichier d'entrée
		final String locContenu = "C - 3 - 3\n" + "M - 1 - 1\n" + "T - 2 - 2 - 1\n" + "A - Lara - 0 - 0 - E - AA\n";
		try (BufferedWriter locWriter = new BufferedWriter(new FileWriter(FICHIER_ENTREE))) {
			locWriter.write(locContenu);
		}

		// Lire la carte depuis le fichier
		final Carte locCarteLue = CarteFileReader.lireFichierEntree(FICHIER_ENTREE);

		// Vérifier que la carte lue est correcte
		assertNotNull(locCarteLue, "La carte lue ne devrait pas être nulle.");
		assertEquals(3, locCarteLue.getLargeur(), "La largeur de la carte devrait être 3.");
		assertEquals(3, locCarteLue.getHauteur(), "La hauteur de la carte devrait être 3.");
		assertEquals(1, locCarteLue.getMontagnes().size(), "Il devrait y avoir une montagne.");
		assertEquals(1, locCarteLue.getTresors().size(), "Il devrait y avoir un trésor.");
		assertEquals(1, locCarteLue.getAventuriers().size(), "Il devrait y avoir un aventurier.");
	}

	@Test
	public void testEcrireFichierSortie() throws IOException {
		// Écrire la carte dans le fichier de sortie
		CarteFileReader.ecrireFichierSortie(_carte, FICHIER_SORTIE);

		// Lire le contenu du fichier de sortie
		final StringBuilder locContenuLue = new StringBuilder();
		try (final BufferedReader locReader = new BufferedReader(new FileReader(FICHIER_SORTIE))) {
			String locLigne;
			while ((locLigne = locReader.readLine()) != null) {
				locContenuLue.append(locLigne).append("\n");
			}
		}

		// Vérifier le contenu écrit
		final String locContenuAttendu = "C - 3 - 3\n" + "M - 1 - 1\n" + "T - 2 - 2 - 1\n"
				+ "A - Lara - 0 - 0 - E - 0\n";
		assertEquals(locContenuAttendu.toString().trim().replaceAll("[^\\p{Print}]", "").replaceAll("\\s+", ""),
				locContenuLue.toString().trim().replaceAll("[^\\p{Print}]", "").replaceAll("\\s+", ""),
				"Le contenu du fichier de sortie ne correspond pas à ce qui était attendu.");
	}
}
