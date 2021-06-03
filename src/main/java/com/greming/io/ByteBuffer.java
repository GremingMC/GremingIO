package com.greming.io;

import java.util.Arrays;


/**
 * @author RomnSD
 */
public class ByteBuffer
{
    
    
    protected byte[] buffer;
    
    protected ByteBufferWriter writer;
    protected ByteBufferReader reader;
    
    
    
    /**
     * @param initialSize int 
     */
    public ByteBuffer(int initialSize) { this(new byte[initialSize]); }
    
    
    /**
     * @param buffer byte[]
     */
    public ByteBuffer(byte[] buffer)
    {
        this.buffer = buffer;
        this.writer = new ByteBufferWriter(this);
        this.reader = new ByteBufferReader(this);
    }
    
    
    /**
     * @param buffer byte[]
     * @param writer ByteBufferWriter
     * @param reader ByteBufferReader
     */
    public ByteBuffer(byte[] buffer, ByteBufferWriter writer, ByteBufferReader reader)
    {
        this.buffer = buffer;
        setWriter(writer);
        setReader(reader);
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
        buffer = bytes;
        writer.offset(0);
        reader.offset(0);
    }
    

    /**
     * @return ByteBufferWriter
     */
    public ByteBufferWriter getWriter() { return writer; }
    
    
    /**
     * @param writer ByteBufferWriter
     */
    public final void setWriter(ByteBufferWriter writer)
    {
        assert(writer != null);
        this.writer = writer;
    }
    
    
    /**
     * @return ByteBufferReader 
     */
    public ByteBufferReader getReader() { return reader; }
    
    
    /**
     * @param reader ByteBufferReader 
     */
    public final void setReader(ByteBufferReader reader) 
    {
        assert(reader != null);
        this.reader = reader;
    }
    

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
        System.arraycopy(buffer, offset, result, size, size);
        
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
    public boolean equals(ByteBuffer other)
    {
        return Arrays.equals(buffer, other.getBytes());
    }
    
    
}
