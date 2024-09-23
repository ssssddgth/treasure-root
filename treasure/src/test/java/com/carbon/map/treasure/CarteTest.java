package com.carbon.map.treasure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbon.map.treasure.app.Aventurier;
import com.carbon.map.treasure.app.Carte;
import com.carbon.map.treasure.app.Position;

import static org.junit.jupiter.api.Assertions.*;

public class CarteTest {

	private Carte carte;

	@BeforeEach
	public void setup() {
		// Créer une carte de taille 3x3
		carte = new Carte(3, 3);

		// Ajouter une montagne à la position (1, 1)
		carte.ajouterMontagne(new Position(1, 1));

		// Ajouter un trésor à la position (2, 2)
		carte.ajouterTresor(new Position(2, 2), 1);
	}

	@Test
	public void testDeuxAventuriersSurLaMemeCase() {
		// Ajouter deux aventuriers qui tentent d'atteindre la même case (2, 2)
		final Aventurier locAventurier1 = new Aventurier("Isabelle", new Position(1, 2), "E", "A");
		final Aventurier locAventurier2 = new Aventurier("Dorian", new Position(3, 2), "O", "A");
		carte.ajouterAventurier(locAventurier1);
		carte.ajouterAventurier(locAventurier2);

		// Simuler les mouvements des aventuriers
		carte.simulerMouvements();

		// Vérifier que le premier aventurier (Isabelle) a pris la case avec le trésor
		assertEquals(new Position(2, 2), locAventurier1.getPosition(),
				"Isabelle aurait dû atteindre la position (2, 2).");
		assertEquals(1, locAventurier1.getTresorsCollectes(), "Isabelle aurait dû collecter 1 trésor.");

		// Vérifier que Dorian n'a pas bougé
		assertEquals(new Position(3, 2), locAventurier2.getPosition(),
				"Dorian aurait dû rester sur sa position initiale.");
	}

	@Test
	public void testAventuriersEviterMontagne() {
		// Ajouter deux aventuriers qui doivent éviter la montagne à (1, 1)
		final Aventurier locAventurier1 = new Aventurier("Isabelle", new Position(0, 1), "E", "A");
		final Aventurier locAventurier2 = new Aventurier("Dorian", new Position(2, 1), "O", "A");
		carte.ajouterAventurier(locAventurier1);
		carte.ajouterAventurier(locAventurier2);

		// Simuler les mouvements des aventuriers
		carte.simulerMouvements();

		// Vérifier que Isabelle a évité la montagne et n'a pas bougé
		assertEquals(new Position(0, 1), locAventurier1.getPosition(),
				"Isabelle aurait dû rester en (0, 1) car il y a une montagne.");

		// Vérifier que Dorian a évité la montagne et n'a pas bougé non plus
		assertEquals(new Position(2, 1), locAventurier2.getPosition(),
				"Dorian aurait dû rester en (2, 1) car il y a une montagne.");
	}

	@Test
	public void testDeuxAventuriersNePeuventPasSeTrouverSurLaMemeCase() {
		// Ajouter deux aventuriers qui veulent se déplacer sur la même case
		final Aventurier locAventurier1 = new Aventurier("Isabelle", new Position(2, 1), "S", "A");
		final Aventurier locAventurier2 = new Aventurier("Dorian", new Position(1, 2), "E", "A");
		carte.ajouterAventurier(locAventurier1);
		carte.ajouterAventurier(locAventurier2);

		// Simuler les mouvements
		carte.simulerMouvements();

		// Isabelle se déplace vers (2, 2), mais Dorian ne peut pas car Isabelle occupe
		// déjà la
		// case
		assertEquals(new Position(2, 2), locAventurier1.getPosition(), "Isabelle devrait être sur la case (2, 2).");
		assertEquals(new Position(1, 2), locAventurier2.getPosition(),
				"Dorian devrait être resté sur la case (1, 2) car (2, 2) est occupée.");
	}

	@Test
	public void testAventurierRestesurPlaceSiCaseOccupee() {
		// Ajouter deux aventuriers qui vont essayer de se déplacer vers une même case
		final Aventurier locAventurier1 = new Aventurier("Isabelle", new Position(2, 1), "S", "A");
		final Aventurier locAventurier2 = new Aventurier("Dorian", new Position(1, 2), "E", "A");
		carte.ajouterAventurier(locAventurier1);
		carte.ajouterAventurier(locAventurier2);

		// Simuler les mouvements
		carte.simulerMouvements();

		// Isabelle se déplace en premier vers (2, 2), Dorian ne peut pas se déplacer
		// vers
		// cette case
		assertEquals(new Position(2, 2), locAventurier1.getPosition(), "Isabelle devrait être sur la case (2, 2).");
		assertEquals(new Position(1, 2), locAventurier2.getPosition(),
				"Dorian devrait être resté sur la case (1, 2) car la case est occupée.");

		// Vérifier que Isabelle a collecté un trésor
		assertEquals(1, locAventurier1.getTresorsCollectes(), "Isabelle aurait dû collecter 1 trésor.");
	}

	@Test
	public void testCollecteDeTresorSurCaseOccupee() {
		// Ajouter un aventurier sur une case avec un trésor
		final Aventurier locAventurier = new Aventurier("Lara", new Position(2, 2), "S", "A");
		carte.ajouterAventurier(locAventurier);

		// Simuler les mouvements
		carte.simulerMouvements();

		// Lara ne doit pas avoir collecté le trésor
		assertEquals(0, locAventurier.getTresorsCollectes(), "Lara devrait avoir collecté 1 trésor.");
	}

	@Test
	public void testCollecteMultipleDeTresors() {
		// Ajouter un aventurier qui peut collecter plusieurs trésors
		carte.ajouterTresor(new Position(2, 3), 2);
		final Aventurier locAventurier = new Aventurier("Lara", new Position(2, 1), "S", "AA"); // Mouvements vers le
																								// bas
		carte.ajouterAventurier(locAventurier);

		// Simuler les mouvements
		carte.simulerMouvements();

		// Lara devrait avoir collecté un trésor et être sur (2, 2)
		assertEquals(new Position(2, 3), locAventurier.getPosition(), "Lara devrait être sur la case (2, 2).");
		assertEquals(2, locAventurier.getTresorsCollectes(), "Lara devrait avoir collecté 1 trésor.");
	}

	@Test
	public void testCollecteDeTresorSurCaseLibere() {
		// Ajouter un aventurier qui collecte un trésor et doit libérer la case
		final Aventurier locAventurier1 = new Aventurier("Lara", new Position(2, 1), "S", "A");
		carte.ajouterAventurier(locAventurier1);

		// Simuler le premier mouvement pour collecter le trésor
		carte.simulerMouvements();

		// Lara collecte le trésor
		assertEquals(1, locAventurier1.getTresorsCollectes(), "Lara devrait avoir collecté 1 trésor.");

		// Maintenant, ajouter un autre aventurier qui se déplace vers la même case
		final Aventurier aventurier2 = new Aventurier("John", new Position(2, 1), "S", "A");
		carte.ajouterAventurier(aventurier2);

		// Simuler les mouvements
		carte.simulerMouvements();

		// Vérifier que John ne peut pas collecter car la case est maintenant vide
		assertEquals(0, aventurier2.getTresorsCollectes(),
				"John ne devrait pas avoir pu collecter un trésor car la case est vide.");
	}

}
