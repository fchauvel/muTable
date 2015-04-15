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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.fchauvel.mutable.expression.Expression;

/**
 * ResultSet caches the results of a query so that the criteria is evaluated
 * only once per row in the source table.
 */
public class ResultSet extends AbstractTable {

    private final Table source;
    private final Expression criteria;
    private final Iterator<Row> iterator;
    private final Map<Integer, Integer> cache;
    private boolean allFetched;

    ResultSet(Table source, Expression criteria) {
        this(source, criteria, new HashMap<>()); 
    }

    ResultSet(Table source, Expression criteria, Map<Integer, Integer> cache) {
        assert source != null : "Invalid result set source ('null' found)";
        assert criteria != null : "Invalid result set criteria ('null' found)";
        assert cache != null : "Invalid result set cache ('null' found)";

        this.source = source;
        this.criteria = criteria;
        this.cache = cache;
        this.allFetched = false;
        this.iterator = source.iterator();
    }



    @Override
    public Row getRow(int rowIndex) {
        fetch(rowIndex);
        return source.getRow(cache.get(rowIndex));
    }

    /**
     * Fetch the ith row that match the local criteria, if it does.
     *
     * @param rowIndex the index of the row of interest
     */
    private void fetch(int rowIndex) {
        if (!allFetched) {
            while (iterator.hasNext()) {
                updateCacheIfNeeded();
                if (cache.containsKey(rowIndex)) {
                    return;
                }
            }
            allFetched = true;
            if (!cache.containsKey(rowIndex)) {
                final String error = String.format("Invalid row id %d (should be within [1, %d])", rowIndex, getRowCount());
                throw new IllegalArgumentException(error);
            }
        }
    }

    @Override
    public int getRowCount() {
        fetchAll();
        return cache.size();
    }


    private void fetchAll() {
        if (!allFetched) {
            while (iterator.hasNext()) {
                updateCacheIfNeeded();
            }
            allFetched = true;
        }
    }

    private void updateCacheIfNeeded() {
        assert cache.size() < source.getRowCount() : "Invalid cache";

        final Row row = iterator.next();
        boolean isSatisfied = (boolean) criteria.evaluate(row);
        if (isSatisfied) {
            cache.put(cache.size() + 1, row.getRowIndex());
        }
    }

    @Override
    public int getColumnCount() {
        return source.getColumnCount();
    }

    @Override
    public Schema getSchema() {
        return source.getSchema();
    }

}
