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
import java.util.Objects;


public class ByteBuffer implements Cloneable
{
    
    
    protected byte[] buffer;
    protected boolean resizable = true;
    protected final Object locker = new Object();
    
    
    
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
    public void setBytes(byte[] bytes)
    {
        if (!resizable)
            throw new RuntimeException("ByteBuffer cannot be modified!");
        
        buffer = Objects.requireNonNull(bytes);
    }
    
    
    /**
     * @return boolean
     */
    public boolean isResizable() { return resizable; }
    
    
    /**
     * @param resizable boolean
     */
    public void setResizable(boolean resizable) { this.resizable = resizable; }
    

    /**
     * @return int 
     */
    public int length() { return buffer.length; }
    
    
    /**
     * @param offset int
     * @return       byte 
     */
    public byte get(int offset) { return buffer[offset]; }
    
    
    /**
     * @param offset int 
     * @param length int 
     * @return       byte[]
     */
    public byte[] get(int offset, int length)
    {
        if (length < 0 || length > (buffer.length - offset))
            throw new RuntimeException("");

        byte[] result = new byte[length];
        System.arraycopy(buffer, offset, result, 0, length);
        
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
        if (size > buffer.length && resizable)
            setBytes(Arrays.copyOf(buffer, size));
    }
    
    
    /**
     * @return int
     */
    public int lastIndex() { return buffer.length - 1; }
    
    
    /**
     * @param other ByteBuffer 
     * @return      boolean 
     */
    public boolean equals(ByteBuffer other) { return Arrays.equals(buffer, other.getBytes()); }
    
    
    /**
     * @return ByteBuffer
     */
    public ByteBuffer clone()
    {
        ByteBuffer buff = new ByteBuffer(Arrays.copyOf(buffer, buffer.length));
        buff.setResizable(resizable);
        
        return buff;
    }
    
    
    public void clean() { buffer = new byte[0]; }
    
    
    /**
     * @return ByteBufferReader
     */
    public ByteBufferReader buildReader() { return buildReader(ByteBufferType.BigEndian); }
    
    
    /**
     * @param bbtype ByteBufferType
     * @return       ByteBufferReader
     */
    public ByteBufferReader buildReader(ByteBufferType bbtype) { return new ByteBufferReader(this, bbtype); }
    
    
    /**
     * @return ByteBufferWriter 
     */
    public ByteBufferWriter buildWriter() { return buildWriter(ByteBufferType.LittleEndian); }
    
    
    /**
     * @param bbtype ByteBufferType
     * @return       ByteBufferWriter
     */
    public ByteBufferWriter buildWriter(ByteBufferType bbtype) { return new ByteBufferWriter(this, bbtype); }
 
    
}
