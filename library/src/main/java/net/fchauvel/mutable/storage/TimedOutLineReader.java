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

package net.fchauvel.mutable.storage; 

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Utility class that read line on an input stream, without blocking
 * indefinitely on a 'readline'.
 */
public class TimedOutLineReader {

    public static final long DEFAULT_READING_TIMEOUT = 1000L;
    
    private final ExecutorService executor;
    private final BufferedReader reader;
    private final long readTimeout;
    private String buffer;
    private int lineCounter;

    
    public TimedOutLineReader(InputStream input) {
        this(input, DEFAULT_READING_TIMEOUT);
    }

    public TimedOutLineReader(InputStream input, long readTimeout) {
        this.executor = Executors.newFixedThreadPool(1);
        this.reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        this.readTimeout = readTimeout;
        this.buffer = null;
        this.lineCounter = 0;
    }

    public int getLineCount() {
        return this.lineCounter;
    }

    public boolean hasMoreLines() throws ReaderException {
        if (buffer == null) {
            buffer = readLine();
        }
        return buffer != null;
    }

    private String readLine() throws ReaderException {
        final Callable<String> readLineTask = new Callable<String>() {
            @Override
            public String call() throws IOException {
                return reader.readLine();
            }
        };
        
        final Future<String> future = executor.submit(readLineTask);
        try {
            return future.get(readTimeout, TimeUnit.MILLISECONDS);
      
        } catch (InterruptedException ex) {
            throw new ReaderException("Interrupted while waiting for readline() to complete", ex);
        
        } catch (ExecutionException ex) {
            throw new ReaderException("Error while retrieving the result of the line read on stream", ex);
        
        } catch (TimeoutException ex) {
            final String error = String.format("Timeout! Reading one line on take more than %d ms.", readTimeout);
            throw new ReaderException(error, ex);
        
        }
    }

    public String nextLine() {
        String result = buffer;
        buffer = null;
        lineCounter++;
        return result;
    }

    public String nextLineOrThrow(String message) throws ReaderException {
        if (hasMoreLines()) {
            return nextLine();
        } else {
            throw new ReaderException(message);
        }
    }

}
