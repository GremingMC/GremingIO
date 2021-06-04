package com.greming.io;

/**
 * @author RomnSD
 * @param <T>
 */
public abstract class ByteBufferWrapper
{
    
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
     * @return boolean 
     */
    public boolean eof() { return freeBytes() <= 0; }
    
    
    /**
     * @return int 
     */
    public int freeBytes() { return buffer.length() - offset; }
    
    
    
}
