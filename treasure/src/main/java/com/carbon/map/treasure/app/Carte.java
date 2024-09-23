package com.carbon.map.treasure.app;

import java.util.*;

import org.eclipse.jdt.annotation.NonNull;

public class Carte {
	private int _largeur;
	private int _hauteur;
	private List<Aventurier> _aventuriers;
	private Set<Position> _montagnes;
	private Map<Position, Integer> _tresors;

	public Carte(final int parLargeur, final int parHauteur) {
		this._largeur = parLargeur;
		this._hauteur = parHauteur;
		this._aventuriers = new ArrayList<>();
		this._montagnes = new HashSet<>();
		this._tresors = new HashMap<>();
	}

	// Ajouter une montagne
	public void ajouterMontagne(@NonNull final Position parPosition) {
		_montagnes.add(parPosition);
	}

	// Ajouter un trésor
	public void ajouterTresor(@NonNull final Position parPosition, final int parQuantite) {
		_tresors.put(parPosition, _tresors.getOrDefault(parPosition, 0) + parQuantite);
	}

	// Ajouter un aventurier
	public void ajouterAventurier(final Aventurier parAventurier) {
		_aventuriers.add(parAventurier);
	}

	/**
	 * Simuler les mouvements pour chaque aventurier du fichier d'entrée. Un tour
	 * correspond à une séquence de mouvement d'un aventurier.
	 */
	public void simulerMouvements() {
		final Set<Position> locPositionsOccupees = new HashSet<>();
		for (final Aventurier parAventurier : _aventuriers) {
			locPositionsOccupees.add(parAventurier.getPosition());
		}

		// Simuler tous les mouvements pour chaque aventurier
		for (final Aventurier parAventurier : _aventuriers) {
			parAventurier.executerMouvements(this._montagnes, locPositionsOccupees, this._tresors);
		}
	}

	// Getters pour la largeur et la hauteur
	public int getLargeur() {
		return _largeur;
	}

	public int getHauteur() {
		return _hauteur;
	}

	@NonNull
	public Aventurier getAventurier(@NonNull final String parNom) {
		return _aventuriers.stream().filter(a -> a.getNom().equals(parNom)).findFirst().orElse(null);
	}

	@NonNull
	public Map<Position, Integer> getTresors() {
		return this._tresors;
	}

	@NonNull
	public Set<Position> getMontagnes() {
		return this._montagnes;
	}

	@NonNull
	public List<Aventurier> getAventuriers() {
		return this._aventuriers;
	}

}