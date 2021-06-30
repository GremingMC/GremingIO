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


public class ByteBufferReader extends ByteBufferWrapper
{

    
    /**
     * @param buffer ByteBuffer 
     */
    public ByteBufferReader(ByteBuffer buffer) { super(buffer, 0, ByteBufferType.BigEndian); }
    
    
    /**
     * @param buffer ByteBuffer 
     * @param bbtype ByteBufferType
     */
    public ByteBufferReader(ByteBuffer buffer, ByteBufferType bbtype) { super(buffer, 0, bbtype); }
    
    
    /**
     * @param buffer ByteBuffer 
     * @param offset int 
     * @param bbtype ByteBufferType
     */
    public ByteBufferReader(ByteBuffer buffer, int offset, ByteBufferType bbtype) { super(buffer, offset, bbtype); }
    
    
    /**
     * @param buffer byte[]
     * @return       ByteBufferReader
     */
    public static ByteBufferReader wrap(byte[] buffer) { return wrap(buffer, ByteBufferType.BigEndian); }
    
    
    /**
     * @param buffer byte[]
     * @param bbtype ByteBufferType
     * @return       ByteBufferReader
     */
    public static ByteBufferReader wrap(byte[] buffer, ByteBufferType bbtype) { return (new ByteBuffer(buffer)).buildReader(bbtype); }    
    
    
    /**
     * @return byte
     */
    public byte readByte() { return buffer.get(offset++); }
    
    
    /**
     * @return int
     */
    public int readUnsignedByte() { return (readByte() & 0xFF); }
    
    
    /**
     * @return boolean 
     */
    public boolean readBoolean() { return buffer.get(offset++) == (byte) 1; }
    
    
    /**
     * @param length int 
     * @return       byte[]
     */
    public byte[] get(int length)
    {
        if (length > freeBytes())
            length = freeBytes();
        
        byte[] result = buffer.get(offset, length);
        offset += result.length;
        
        return result;
    }
    
    
    /**
     * @return short 
     */
    public short readInt16()
    {
        if (bbtype.equals(ByteBufferType.VarInt))
            return (short) readVarInt32();
        
        if (bbtype.equals(ByteBufferType.LittleEndian))
            return readLittleInt16();
        
        return readBigInt16();
    }
    
    
    /**
     * @return short
     */
    public short readLittleInt16()
    {
        return (short) ((readByte() & 0xFF) |
                       ((readByte() & 0xFF) << 8));
    }
    
    
    /**
     * @return short
     */
    public short readBigInt16()
    {
        return (short) ((readByte() & 0xFF) << 8 |
                        (readByte() & 0xFF));
    }

    
    /**
     * @return 
     */
    public int readInt24()
    {
        if (bbtype.equals(ByteBufferType.VarInt))
            return readVarInt32();
        
        if (bbtype.equals(ByteBufferType.LittleEndian))
            return readLittleInt24();
        
        return readBigInt24();
    }
    
    
    /**
     * @return int 
     */
    public int readLittleInt24()
    {
        return ((readByte() & 0xFF)      |
                (readByte() & 0xFF) << 8 |
                (readByte() & 0xFF) << 16);
    }
    
    
    /**
     * @return int 
     */
    public int readBigInt24()
    {
        return ((readByte() & 0xFF) << 16 |
                (readByte() & 0xFF) << 8  |
                (readByte() & 0xFF));
    }
    
    
    /**
     * @return int 
     */
    public int readInt32()
    {
        if (bbtype.equals(ByteBufferType.VarInt))
            return readVarInt32();
        
        if (bbtype.equals(ByteBufferType.LittleEndian))
            return readLittleInt32();
        
        return readBigInt32();
    }
    
    
    /**
     * @return int 
     */
    public int readLittleInt32()
    {
        return ((readByte() & 0xFF)       |
                (readByte() & 0xFF) << 8  |
                (readByte() & 0xFF) << 16 |
                (readByte() & 0xFF) << 24);
    }
    
    
    /**
     * @return int 
     */
    public int readBigInt32()
    {
        return ((readByte() & 0xFF) << 24 |
                (readByte() & 0xFF) << 16 |
                (readByte() & 0xFF) << 8  |
                (readByte() & 0xFF));
    }
    
    
    /**
     * @return long 
     */
    public long readInt64()
    {
        if (bbtype.equals(ByteBufferType.VarInt))
            return readVarInt64();
        
        if (bbtype.equals(ByteBufferType.LittleEndian))
            return readLittleInt64();
        
        return readBigInt64();
    }
    
    
    /**
     * @return long
     */
    public long readLittleInt64()
    {
        return ((long) (readByte() & 0xFF)       |
                (long) (readByte() & 0xFF) << 8  |
                (long) (readByte() & 0xFF) << 16 |
                (long) (readByte() & 0xFF) << 24 | 
                (long) (readByte() & 0xFF) << 32 | 
                (long) (readByte() & 0xFF) << 40 | 
                (long) (readByte() & 0xFF) << 48 | 
                (long) (readByte() & 0xFF) << 56); 
    }
    
    
    /**
     * @return long 
     */
    public long readBigInt64()
    {
        return ((long) (readByte() & 0xFF) << 56 |
                (long) (readByte() & 0xFF) << 48 |
                (long) (readByte() & 0xFF) << 40 |
                (long) (readByte() & 0xFF) << 32 | 
                (long) (readByte() & 0xFF) << 24 | 
                (long) (readByte() & 0xFF) << 16 | 
                (long) (readByte() & 0xFF) << 8  | 
                (long) (readByte() & 0xFF)); 
    }
    
    
    /**
     * @return float
     */
    public float readFloat32() { return Float.intBitsToFloat(readInt32()); }
    
    
    /**
     * @return double
     */
    public double readFloat64() { return Double.longBitsToDouble(readInt64()); }
    
    
    /**
     * @return int
     */
    public int readVarInt32()
    {
        int result = 0, size = 0;
        byte head = readByte();
        
        while (true) {
            result |= (head & 0x7F) << (7 * size++);
            
            if ((head & 0x80) != 0x80 || size >= 6)
                break;
            
            head = readByte();
        }
        
        return result;
    }
    
    
    /**
     * @return long
     */
    public long readVarInt64()
    {
        long result = 0L, size = 0L;
        byte head = readByte();
        
        while (true) {
            result |= (head & 0x7FL) << (7 * size++);
            
            if ((head & 0x80) != 0x80 || size >= 11L)
                break;
            
            head = readByte();
        }
        
        return result;
    }
    
    
    /**
     * @return String
     */
    public String readString() { return readString(Charset.forName("UTF-8")); }
    
    
    /**
     * @param charset Charset
     * @return        String
     */
    public String readString(Charset charset) { return new String(get((int) readInt16()), charset); }

    
}
