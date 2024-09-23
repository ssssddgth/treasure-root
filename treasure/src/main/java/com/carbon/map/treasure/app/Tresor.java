package com.carbon.map.treasure.app;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Représente les trésors sur la carte, plusieurs trésors peuvent être à une
 * même position.
 */
public class Tresor {
	private Position _position;
	private int _quantite;

	public Tresor(@NonNull final Position parPosition, final int parQuantite) {
		this._position = parPosition;
		this._quantite = parQuantite;
	}

	public int collecter() {
		if (_quantite > 0) {
			_quantite--;
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Trésor (" + _position.getX() + "," + _position.getY() + ") - Restant: " + _quantite;
	}

	@NonNull
	public Position getPosition() {
		return _position;
	}

	public void setPosition(@NonNull final Position parPosition) {
		this._position = parPosition;
	}

	public int getQuantite() {
		return _quantite;
	}

	public void setQuantite(final int parQuantite) {
		this._quantite = parQuantite;
	}

}
