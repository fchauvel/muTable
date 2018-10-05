/**
 * This file is part of MuTable.
 *
 * Copyright (C) 2015 Franck Chauvel <franck.chauvel@gmail.com>
 *
 * MuTable is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mutable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MuTable.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fchauvel.mutable;

import java.util.Iterator;

public class RowIterator implements Iterator<Row> {

    private final Cursor current;

    public RowIterator(Cursor startPosition) {
        this.current = startPosition;
    }

    @Override
    public boolean hasNext() {
        return current.hasNext();
    }

    @Override
    public Row next() {
        if (current.hasNext()) {
            current.moveToNext();
            return new Cursor(current);
        }
        throw new IllegalStateException("There is no next row!");
    }

}
