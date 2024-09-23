package com.carbon.map.treasure.app;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

public class Aventurier {
	private String _nom;
	private Position _position;
	private String _orientation;
	private Queue<Character> _mouvements;
	private int _tresorsCollectes;

	public Aventurier(@NonNull final String parNom, @NonNull final Position parPosition,
			@NonNull final String parOrientation, @NonNull final String parSequenceMouvements) {
		this._nom = parNom;
		this._position = parPosition;
		this._orientation = parOrientation;
		this._mouvements = new LinkedList<>();
		for (final char m : parSequenceMouvements.toCharArray()) {
			_mouvements.add(m);
		}
		this._tresorsCollectes = 0;
	}

	/**
	 * Simule les mouvements d'un aventurier
	 */
	public void executerMouvements(@NonNull final Set<Position> parMontagnes,
			@NonNull final Set<Position> parPositionsOccupees, @NonNull final Map<Position, Integer> parTresors) {
		System.out.println("Début des mouvements pour l'aventurier " + _nom);

		Position locDernierePosition = null; // Pour garder trace de la dernière position avant la collecte

		while (!_mouvements.isEmpty()) {
			final char locMouvement = _mouvements.poll();
			final Position locAnciennePosition = _position;
			System.out.println("Aventurier " + _nom + " à la position " + locAnciennePosition + " fait le mouvement '"
					+ locMouvement + "'.");

			switch (locMouvement) {
			case 'A': // Avancer
				final Position locNouvellePosition = calculerNouvellePosition();
				if (!parMontagnes.contains(locNouvellePosition)
						&& !parPositionsOccupees.contains(locNouvellePosition)) {
					// Mettre à jour les positions si la case est libre
					System.out.println("Aventurier " + _nom + " avance vers " + locNouvellePosition);
					parPositionsOccupees.remove(locAnciennePosition);
					parPositionsOccupees.add(locNouvellePosition);
					locDernierePosition = _position; // Mettre à jour la dernière position avant le déplacement
					_position = locNouvellePosition;
				} else {
					// Si la case est occupée, continuer les mouvements mais rester sur place
					System.out.println("Aventurier " + _nom + " ne peut pas avancer vers " + locNouvellePosition
							+ " car elle est occupée ou bloquée.");
				}
				break;

			case 'G': // Tourner à gauche
				System.out.println("Aventurier " + _nom + " tourne à gauche.");
				tournerAGauche();
				System.out.println("Nouvelle orientation : " + _orientation);
				break;

			case 'D': // Tourner à droite
				System.out.println("Aventurier " + _nom + " tourne à droite.");
				tournerADroite();
				System.out.println("Nouvelle orientation : " + _orientation);
				break;
			}

			// Gérer la collecte de trésors, seulement si l'aventurier a quitté la case
			// après la dernière collecte
			if (parTresors.containsKey(_position)) {
				final int locNbTresors = parTresors.get(_position);
				if (locNbTresors > 0 && (locDernierePosition == null || !locDernierePosition.equals(_position))) {
					System.out.println("Aventurier " + _nom + " collecte un trésor à la position " + _position);
					collecterTresor(); // L'aventurier ramasse un trésor
					parTresors.put(_position, locNbTresors - 1); // Diminuer le nombre de trésors sur la case
					System.out.println("Il reste " + (locNbTresors - 1) + " trésor(s) à la position " + _position);
					locDernierePosition = _position; // Mettre à jour la dernière position après la collecte
				} else if (locNbTresors > 0) {
					System.out.println("Aventurier " + _nom + " ne peut pas collecter le trésor à la position "
							+ _position + " sans bouger.");
				}
			}
		}

		System.out.println("Fin des mouvements pour l'aventurier " + _nom + " à la position " + _position);
	}

	// Calculer la nouvelle position selon l'orientation actuelle
	@NonNull
	private Position calculerNouvellePosition() {
		int x = _position.getX();
		int y = _position.getY();
		switch (_orientation) {
		case "N":
			return new Position(x, y - 1);
		case "S":
			return new Position(x, y + 1);
		case "E":
			return new Position(x + 1, y);
		case "O":
			return new Position(x - 1, y);
		}
		return _position;
	}

	// Tourner à gauche
	private void tournerAGauche() {
		switch (_orientation) {
		case "N":
			_orientation = "O";
			break;
		case "O":
			_orientation = "S";
			break;
		case "S":
			_orientation = "E";
			break;
		case "E":
			_orientation = "N";
			break;
		}
	}

	// Tourner à droite
	private void tournerADroite() {
		switch (_orientation) {
		case "N":
			_orientation = "E";
			break;
		case "E":
			_orientation = "S";
			break;
		case "S":
			_orientation = "O";
			break;
		case "O":
			_orientation = "N";
			break;
		}
	}

	// Collecter un trésor
	public void collecterTresor() {
		_tresorsCollectes++;
	}

	// Getters et setters
	@NonNull
	public Position getPosition() {
		return _position;
	}

	@NonNull
	public String getOrientation() {
		return this._orientation;
	}

	public void setPosition(@NonNull final Position parPosition) {
		this._position = parPosition;
	}

	@NonNull
	public String getNom() {
		return _nom;
	}

	public int getTresorsCollectes() {
		return _tresorsCollectes;
	}

}