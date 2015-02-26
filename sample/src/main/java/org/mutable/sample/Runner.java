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
package org.mutable.sample;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mutable.DataTable;
import static org.mutable.FieldBuilder.aField;
import static org.mutable.FieldType.*;
import org.mutable.Schema;
import static org.mutable.SchemaBuilder.aSchema;
import org.mutable.Table;
import static org.mutable.expression.FieldReference.field;
import static org.mutable.expression.Literal.value;
import org.mutable.storage.csv.CSVWriter;

/**
 * A sample application using the muTable library
 */
public class Runner {

    public static void main(String args[]) {
        Schema schema = aSchema()
                .with(aField("name").ofType(STRING))
                .with(aField("age").ofType(INTEGER))
                .with(aField("isMarried").ofType(BOOLEAN))
                .with(aField("salary").ofType(DOUBLE))
                .build();

        final DataTable table = new DataTable(schema);
        table.appendRows(new Object[][]{
            {"franck", 33, true, 6000.00},
            {"di", 32, true, 6000.0},
            {"claire", 2, false, 100.00}
        });

        Table selection = table.where(field("isMarried").is(value(false)));

        FileOutputStream file = null;
        try {
            CSVWriter csv = new CSVWriter();
            file = new FileOutputStream("people.csv");
            csv.write(selection, file);
            try {
                file.close();
            } catch (IOException ioe) {
                Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ioe);
            }
        } catch (FileNotFoundException ex) {

            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
