package com.greming.io;

/**
 * @author RomnSD
 */
public abstract class ByteBufferWrapper
{
    
    public enum ByteBufferType { VarInt, LittleEndian, BigEndian }
    
    protected ByteBuffer buffer;
    protected int offset;
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
    public int offset() { return offset; }
    
    
    /**
     * @param offset int 
     */
    public void offset(int offset)
    {
        assert(offset < buffer.length() && offset >= 0);
        this.offset = offset;
    }
    
    
    /**
     * @param plus int 
     */
    public void seek(int plus) { offset(offset + plus); }
    
    
    /**
     * @return ByteBufferType
     */
    public ByteBufferType bufferType() { return bbtype; }
    
    
    /**
     * @param bbtype ByteBufferType
     */
    public void bufferType(ByteBufferType bbtype) { this.bbtype = bbtype; }
    
    
    /**
     * @return boolean 
     */
    public boolean eof() { return (offset >= buffer.length()); }
    
    
}
