/**
 *
 *    ____                    _
 *   / ___|_ __ ___ _ __ ___ (_)_ __   __ _
 *  | |  _| '__/ _ \ '_ ` _ \| | '_ \ / _` |
 *  | |_| | | |  __/ | | | | | | | | | (_| |
 *   \____|_|  \___|_| |_| |_|_|_| |_|\__, |
 *                                    |___/
 *
 * This file is part of Greming.
 *
 * Greming is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Greming is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Greming. If not, see <https://www.gnu.org/licenses/>.
 *
 * @author Brayan Roman
 *
 */

package com.greming.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;


public interface ByteBufferUtils
{
    
    
    /**
     * @param buffer byte[]
     * @return       byte[]
     */
    public static byte[] encodeBase64(byte[] buffer)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encode(buffer);
    }
    
    
    /**
     * @param buffer byte[]
     * @return       byte[]
     */
    public static byte[] decodeBase64(byte[] buffer)
    {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(buffer);
    }
    
   
    
    public static byte[] encodeZip(byte[] buffer, int compressionLevel)
    {
        Deflater deflater = new Deflater(compressionLevel, true);
        deflater.setInput(buffer);
        
        try (
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            DeflaterOutputStream stream = new DeflaterOutputStream(output, deflater);
            ) {
            
            stream.finish();
            deflater.end();
            
            return output.toByteArray();
            
        } catch (Throwable exception) {
            exception.printStackTrace();
            return new byte[0];
        }
    }
    
    
    /**
     * @param buffer        byte[]
     * @param bufferLength  int 
     * @param maxOutputSize int
     * @param nowrap        boolean 
     * @return              byte[]
     */
    public static byte[] decodeZip(byte[] buffer, int bufferLength, int maxOutputSize, boolean nowrap)
    {
        try(
           ByteArrayOutputStream output = new ByteArrayOutputStream();
           InflaterInputStream stream = new InflaterInputStream(new ByteArrayInputStream(buffer), new Inflater(true));
           ) {
            
            byte[] temp = new byte[bufferLength];
            int length;
            
            while (true) {
                length = stream.read(temp);
                
                if (length < 1 || output.size() >= maxOutputSize)
                    break;
                
                output.write(temp, 0, length);
            }
            
            return output.toByteArray();
            
        } catch (Throwable exception) {
            exception.printStackTrace();
            return new byte[0];
        }
    }
    
    
    
    
}
