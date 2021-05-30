package com.greming.io;

/**
 *
 * @author RomnSD
 */
public class ByteBufferWriter extends ByteBufferWrapper
{
    
    /**
     * @param buffer ByteBuffer 
     */
    public ByteBufferWriter(ByteBuffer buffer) { super(buffer, 0, ByteBufferType.BigEndian); }
    
    
    /**
     * @param buffer ByteBuffer 
     * @param offset int 
     * @param bbtype ByteBufferType
     */
    public ByteBufferWriter(ByteBuffer buffer, int offset, ByteBufferType bbtype) { super(buffer, offset, bbtype); }

    
    
}
