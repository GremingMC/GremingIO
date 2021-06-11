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

import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class ByteBuffer implements Cloneable
{
    
    
    protected byte[] buffer;
    
    
    /**
     * @param initialSize int 
     */
    public ByteBuffer(int initialSize) { this(new byte[initialSize]); }
    
    
    /**
     * @param buffer byte[]
     */
    public ByteBuffer(byte[] buffer)
    {
        if (buffer == null)
            buffer = new byte[64];
        
        this.buffer = buffer;
    }
    
    
    /**
     * @return byte[]
     */
    public byte[] getBytes() { return buffer; }
    
    
    /**
     * @param bytes byte[]
     */
    public void setBytes(byte[] bytes) { buffer = bytes; }
    

    /**
     * @return int 
     */
    public int length() { return buffer.length; }
    
    
    /**
     * @param offset int
     * @return       byte 
     */
    public byte get(int offset) { return offset >= buffer.length ? (byte) 0 : buffer[offset]; }
    
    
    /**
     * @param offset int 
     * @param size   int 
     * @return       byte[]
     */
    public byte[] get(int offset, int size)
    {
        if (size > buffer.length)
            return new byte[0];
        
        byte[] result = new byte[size];
        System.arraycopy(buffer, offset, result, 0, size);
        
        return result;
    }
    
    
    /**
     * @param offset int 
     * @param value  byte
     */
    public void put(int offset, byte value) { buffer[offset] = value; }
    
    
    /**
     * @param offset int
     * @param value  byte[]
     */
    public void put(int offset, byte[] value) { System.arraycopy(value, 0, buffer, offset, value.length); }
    
    
    /**
     * @param size int
     */
    public void expand(int size)
    {
        if (size > buffer.length)
            buffer = Arrays.copyOf(buffer, size);
    }
    
    
    /**
     * @param other ByteBuffer 
     * @return      boolean 
     */
    public boolean equals(ByteBuffer other) { return Arrays.equals(buffer, other.getBytes()); }
    
    
    /**
     * @return ByteBuffer
     */
    public ByteBuffer clone() { return new ByteBuffer(Arrays.copyOf(buffer, buffer.length)); }
    
    
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
    
    
    /**
     * @param buffer           byte[]
     * @param compressionLevel int 
     * @return 
     */
    public static byte[] encodeZip(byte[] buffer, int compressionLevel) { return encodeZip(buffer, 1024 * 1024 * 2, compressionLevel); }
    
    
    /**
     * @param buffer           byte[]
     * @param outSize          int 
     * @param compressionLevel int
     * @return                 byte[]
     */
    public static byte[] encodeZip(byte[] buffer, int outSize , int compressionLevel)
    {
        Deflater deflater = new Deflater(compressionLevel, true);
        deflater.setInput(buffer);
        deflater.finish();
        
        byte[] output = new byte[outSize];
        deflater.deflate(output);
        deflater.end();
        
        return output;
    }
    
    
    /**
     * @param buffer           byte[]
     * @param compressionLevel int 
     * @return 
     */
    public static byte[] decodeZip(byte[] buffer, int compressionLevel) { return encodeZip(buffer, 1024 * 1024 * 2, compressionLevel); }
    
    
    /**
     * @param buffer           byte[]
     * @param outSize          int 
     * @param compressionLevel int 
     * @return                 byte[]
     */
    public static byte[] decodeZip(byte[] buffer, int outSize , int compressionLevel)
    {
        Inflater inflater = new Inflater(true);
        inflater.setInput(buffer);
        
        byte[] output = new byte[outSize];
        
        try { output = Arrays.copyOf(output, inflater.inflate(output)); } catch (Throwable exception) { 
            exception.printStackTrace();
        }
        
        inflater.end();
         
       return output;
    }
    
    
}
