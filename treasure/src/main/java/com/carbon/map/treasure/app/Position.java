package com.carbon.map.treasure.app;

import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Représente une position sur la carte
 */
public class Position {
	private int _x;
	private int _y;

	public Position(final int parX, final int parY) {
		this._x = parX;
		this._y = parY;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	@Override
	public boolean equals(@Nullable final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Position position = (Position) o;
		return _x == position._x && _y == position._y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_x, _y);
	}

	@Override
	public String toString() {
		return "Position{" + "x=" + _x + ", y=" + _y + '}';
	}
}
