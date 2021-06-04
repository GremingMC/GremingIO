package com.greming.io;

import java.util.Arrays;


/**
 * @author RomnSD
 */
public class ByteBuffer
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
    public byte get(int offset) { return buffer[offset]; }
    
    
    /**
     * @param offset int 
     * @param size   int 
     * @return       byte[]
     */
    public byte[] get(int offset, int size)
    {
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
        if (size > 0)
            buffer = Arrays.copyOf(buffer, size);
    }
    
    
    /**
     * @param other ByteBuffer 
     * @return      boolean 
     */
    public boolean equals(ByteBuffer other) { return Arrays.equals(buffer, other.getBytes()); }
    
    
}
