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
import net.fchauvel.mutable.expression.Expression;

/**
 * Factor out some common code between ResultSet and DataTable
 */
public abstract class AbstractTable implements Table {

    @Override
    public boolean isEmpty() {
        return getRowCount() == 0;
    }

    @Override
    public Object getData(int rowIndex, String fieldName) {
        return getRow(rowIndex).getField(fieldName);
    }

    @Override
    public Object getData(int rowIndex, int columnIndex) {
        return getRow(rowIndex).getField(columnIndex);
    }

    @Override
    public int getDataCount() {
        return getRowCount() * getColumnCount();
    }

    @Override
    public ResultSet where(Expression query) {
        return new ResultSet(this, query);
    }

    @Override
    public Iterator<Row> iterator() {
        return new RowIterator(new Cursor(this, 0));
    }

    @Override
    public Cursor newCursor() {
        return new Cursor(this, 1);
    }

}
