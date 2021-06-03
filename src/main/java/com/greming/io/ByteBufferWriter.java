package com.greming.io;

/**
 *
 * @author RomnSD
 */
public class ByteBufferWriter extends ByteBufferWrapper
{
    
    
    protected int expandFactor = 32;
    
    
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
        if (freeBytes() < bytes) {
            buffer.expand(buffer.length() + (expandFactor * bytes));
        }
    }
    
    
    /**
     * Write one byte to the buffer.
     * 
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
            writeVar(value);
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
            (byte)  (value & 0xFF),
            (byte) ((value & 0xFF) >> 8),
        });
    }
    
    
    /**
     * @param value short 
     */
    public void writeBigInt16(short value)
    {
        put(new byte[] {
            (byte) ((value & 0xFF) >> 8),
            (byte)  (value & 0xFF)
        });
    }
    
    
    /**
     * @param value int 
     */
    public void writeInt24(int value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVar(value);
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
            (byte)  (value & 0xFF),
            (byte) ((value & 0xFF) >> 8 ),
            (byte) ((value & 0xFF) >> 16)
        });
    }
    

    /**
     * @param value int 
     */
    public void writeBigInt24(int value)
    {
        put(new byte[] {
           (byte) ((value & 0xFF) >> 16),
           (byte) ((value & 0xFF) >> 8 ),
           (byte)  (value & 0xFF)
        });
    }
    
    
    /**
     * @param value int 
     */
    public void writeInt32(int value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVar(value);
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
            (byte)  (value & 0xFF),
            (byte) ((value & 0xFF) >> 8 ),
            (byte) ((value & 0xFF) >> 16),
            (byte) ((value & 0xFF) >> 24)
        });
    }
    
    
    /**
     * @param value int 
     */
    public void writeBigInt32(int value)
    {
        put(new byte[] {
           (byte) ((value & 0xFF) >> 24),
           (byte) ((value & 0xFF) >> 16),
           (byte) ((value & 0xFF) >> 8 ),
           (byte)  (value & 0xFF)
        });
    }
    
    
    /**
     * @param value long 
     */
    public void writeInt64(long value)
    {
        if (bbtype.equals(ByteBufferType.VarInt)) {
            writeVar(value);
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
            (byte)  (value & 0xFF),
            (byte) ((value & 0xFF) >> 8 ),
            (byte) ((value & 0xFF) >> 16),
            (byte) ((value & 0xFF) >> 24),
            (byte) ((value & 0xFF) >> 32),
            (byte) ((value & 0xFF) >> 40),
            (byte) ((value & 0xFF) >> 48),
            (byte) ((value & 0xFF) >> 56)            
        });
    }
    
    
    /**
     * @param value long 
     */
    public void writeBigInt64(long value)
    {
        put(new byte[] {
           (byte) ((value & 0xFF) >> 56),
           (byte) ((value & 0xFF) >> 48),
           (byte) ((value & 0xFF) >> 40),
           (byte) ((value & 0xFF) >> 32),
           (byte) ((value & 0xFF) >> 24),
           (byte) ((value & 0xFF) >> 16),
           (byte) ((value & 0xFF) >> 8 ),
           (byte)  (value & 0xFF)
        });
    }

    
    /**
     * @param value long 
     */
    public void writeVar(long value)
    {
        do {
            long temp = value;
            value >>>= 7;
            
            if (value != 0L)
                temp |= 0x80;
            
            writeByte((byte) temp);
        } while (value != 0L);
    }
    
    
}
