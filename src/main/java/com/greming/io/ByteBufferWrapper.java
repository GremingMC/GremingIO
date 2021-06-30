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


public abstract class ByteBufferWrapper
{
    
    protected ByteBuffer buffer;
    protected int offset;
    protected int origin;
    protected ByteBufferType bbtype;
    
    
    /**
     * @param buffer ByteBuffer 
     */
    public ByteBufferWrapper(ByteBuffer buffer) { this(buffer, 0, ByteBufferType.BigEndian); }
    
    
    /**
     * @param buffer ByteBuffer 
     * @param offset int 
     * @param bbtype ByteBufferType
     */
    public ByteBufferWrapper(ByteBuffer buffer, int offset, ByteBufferType bbtype)
    {
        this.buffer = buffer;
        this.offset = offset;
        this.bbtype = bbtype;
    }
    
    
    /**
     * @return ByteBuffer
     */
    public ByteBuffer buffer() { return buffer; }
    
    
    /**
     * @return int 
     */
    public int getOffset() { return offset; }
    
    
    /**
     * @param offset int 
     */
    public void setOffset(int offset)
    {
        if (offset < buffer.length() && offset >= 0);
            this.offset = offset;
    }
    
    
    /**
     * @param plus int 
     */
    public void seek(int plus) { setOffset(offset + plus); }
    
    
    /**
     * @return int
     */
    public int getOrigin() { return origin; }
    
    
    /**
     * @param origin 
     */
    public void setOrigin(int origin) { this.origin = origin; }
    

    /**
     * @return int
     */
    public int rewind() { return (offset = origin); } 
    
    
    /**
     * @return ByteBufferType
     */
    public ByteBufferType bufferType() { return bbtype; }
    
    
    /**
     * @param bbtype ByteBufferType
     */
    public void bufferType(ByteBufferType bbtype) { this.bbtype = bbtype; }
    
    
    /**
     * @return byte[]
     */
    public byte[] slice() { return buffer.get(0, offset); }
    
    
    /**
     * @return int 
     */
    public int flushExtraBytes()
    {
        if (buffer.length() > offset) {
            buffer.setBytes(slice());
            return offset;
        }
        
        return 0;
    }
    
    
    /**
     * @return boolean 
     */
    public boolean eof() { return freeBytes() <= 0; }
    
    
    /**
     * @return int 
     */
    public int freeBytes() { return buffer.length() - offset; }
    
    
}
