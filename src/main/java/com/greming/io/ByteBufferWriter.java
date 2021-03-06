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

import java.nio.charset.Charset;


public class ByteBufferWriter extends ByteBufferWrapper
{
    
    
    protected int expandFactor = 32;
    
    
    /**
     * @param buffer ByteBuffer 
     */
    public ByteBufferWriter(ByteBuffer buffer) { super(buffer, 0, ByteBufferType.BigEndian); }
    
    
    /**
     * @param buffer ByteBuffer 
     * @param bbtype ByteBufferType
     */
    public ByteBufferWriter(ByteBuffer buffer, ByteBufferType bbtype) { super(buffer, 0, bbtype); }
    
    
    /**
     * @param buffer ByteBuffer 
     * @param offset int 
     * @param bbtype ByteBufferType
     */
    public ByteBufferWriter(ByteBuffer buffer, int offset, ByteBufferType bbtype) { super(buffer, offset, bbtype); }
    
    
    /**
     * @param buffer byte[]
     * @return       ByteBufferWriter
     */
    public static ByteBufferWriter wrap(byte[] buffer) { return wrap(buffer, ByteBufferType.BigEndian); }
    
    
    /**
     * @param buffer byte[]
     * @param bbtype ByteBufferType
     * @return       ByteBufferWriter
     */
    public static ByteBufferWriter wrap(byte[] buffer, ByteBufferType bbtype) { return (new ByteBuffer(buffer)).buildWriter(bbtype); }
    
    
    /**
     * @return int 
     */
    public int getExpandFactor() { return expandFactor; }
    
    
    /**
     * @param expandFactor int 
     */
    public void setExpandFactor(int expandFactor)
    {
        if (expandFactor > 0)
            this.expandFactor = expandFactor;
    }
    
    
    /**
     * @param bytes int 
     */
    public void allocate(int bytes)
    {
        if (!buffer.resizable)
            return;
        
        if (freeBytes() < bytes) {
            if (bytes < expandFactor)
                bytes = expandFactor;
            
            buffer.expand(buffer.length() + bytes);
        }
    }
    
    
    /**
     * @param value 
     */
    public void writeByte(byte value)
    {
        allocate(1);
        buffer.put(offset++, value);
    }
    
    
    /**
     * @param value 
     */
    public void writeBoolean(boolean value) { writeByte(value ? (byte) 1 : (byte) 0); }
    
    
    /**
     * @param bytes byte[]
     */
    public void put(byte[] bytes)
    {
        allocate(bytes.length);
        buffer.put(offset, bytes);
        
        offset += bytes.length;
    }
    
    
    /**
     * @param value 
     */
    public void writeInt16(short value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVarInt32(value);
            return;
        }
        
        if (bbtype.equals(ByteBufferType.LittleEndian)) {
            writeLittleInt16(value);
            return;
        }
        
        writeBigInt16(value);
    }
    
    
    /**
     * @param value short 
     */
    public void writeLittleInt16(short value)
    {
        put(new byte[] {
            (byte)  value,
            (byte) (value >> 8),
        });
    }
    
    
    /**
     * @param value short 
     */
    public void writeBigInt16(short value)
    {
        put(new byte[] {
            (byte) (value >> 8),
            (byte)  value
        });
    }
    
    
    /**
     * @param value int 
     */
    public void writeInt24(int value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVarInt32(value);
            return;
        }
        
        if (bbtype.equals(ByteBufferType.LittleEndian)) {
            writeLittleInt24(value);
            return;
        }
        
        writeBigInt24(value);
    }
    
    
    /**
     * @param value int 
     */
    public void writeLittleInt24(int value)
    {
        put(new byte[] {
            (byte)  value,
            (byte) (value >> 8 ),
            (byte) (value >> 16)
        });
    }
    

    /**
     * @param value int 
     */
    public void writeBigInt24(int value)
    {
        put(new byte[] {
           (byte) (value >> 16),
           (byte) (value >> 8 ),
           (byte)  value
        });
    }
    
    
    /**
     * @param value int 
     */
    public void writeInt32(int value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVarInt32(value);
            return;
        }
        
        if (bbtype.equals(ByteBufferType.LittleEndian)) {
            writeLittleInt32(value);
            return;
        }
        
        writeBigInt32(value);
    }
    
    
    /**
     * @param value int 
     */
    public void writeLittleInt32(int value)
    {
        put(new byte[] {
            (byte)  value,
            (byte) (value >> 8 ),
            (byte) (value >> 16),
            (byte) (value >> 24)
        });
    }
    
    
    /**
     * @param value int 
     */
    public void writeBigInt32(int value)
    {
        put(new byte[] {
           (byte) (value >> 24),
           (byte) (value >> 16),
           (byte) (value >> 8 ),
           (byte)  value
        });
    }
    
    
    /**
     * @param value long 
     */
    public void writeInt64(long value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVarInt64(value);
            return;
        }
        
        if (bbtype.equals(ByteBufferType.LittleEndian)) {
            writeLittleInt64(value);
            return;
        }
        
        writeBigInt64(value);
    }
    
    
    /**
     * @param value long 
     */
    public void writeLittleInt64(long value)
    {
        put(new byte[] {
            (byte)  value,
            (byte) (value >> 8 ),
            (byte) (value >> 16),
            (byte) (value >> 24),
            (byte) (value >> 32),
            (byte) (value >> 40),
            (byte) (value >> 48),
            (byte) (value >> 56)            
        });
    }
    
    
    /**
     * @param value long 
     */
    public void writeBigInt64(long value)
    {
        put(new byte[] {
           (byte) (value >> 56),
           (byte) (value >> 48),
           (byte) (value >> 40),
           (byte) (value >> 32),
           (byte) (value >> 24),
           (byte) (value >> 16),
           (byte) (value >> 8 ),
           (byte)  value
        });
    }
    
    
    /**
     * @param value 
     */
    public void writeFloat32(float value) { writeInt32(Float.floatToIntBits(value)); }
    
    
    /**
     * @param value 
     */
    public void writeFloat64(double value) { writeInt64(Double.doubleToLongBits(value)); }
    
    
    /**
     * 
     * @param value int
     */
    public void writeVarInt32(int value)
    {
        int temp;
        do {
            temp = value;
            value >>>= 7;
            writeByte(value != 0 ? (byte) (temp | 0x80) : (byte) temp);
        } while (value != 0);
    }

    
    /**
     * @param value long 
     */
    public void writeVarInt64(long value)
    {
        long temp;
        do {
            temp = value;
            value >>>= 7;
            writeByte(value != 0L ? (byte) (temp | 0x80L) : (byte) temp);
        } while (value != 0L);
    }
    
    
    /**
     * @param value String 
     */
    public void writeString(String value) { writeString(value, Charset.forName("UTF-8")); }
    
    
    /**
     * @param value   String 
     * @param charset Charset
     */
    public void writeString(String value, Charset charset)
    {
        byte[] bytes = value.getBytes(charset);
        writeInt16((short) bytes.length);
        put(bytes);
    }
    
    
}
