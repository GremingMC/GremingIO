package com.greming.io;

/**
 * @author RomnSD
 */
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
     * Read one byte from the buffer.
     * 
     * @return byte
     */
    public byte readByte() { return buffer.get(offset++); }
    
    
    /**
     * @return boolean 
     */
    public boolean readBoolean() { return buffer.get(offset++) == 1; }
    
    
    /**
     * @param length int 
     * @return       byte[]
     */
    public byte[] get(int length)
    {
        try { return buffer.get(offset, length); } finally {
            offset += length;
        }
    }
    
    
    /**
     * Read a signed Int16 from the buffer.
     * 
     * @return short 
     */
    public short readInt16()
    {
        if (bbtype.equals(ByteBufferType.VarInt))
            return (short) readVar(3);
        
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
     * Read a signed Int24 from the buffer.
     * 
     * @return 
     */
    public int readInt24()
    {
        if (bbtype.equals(ByteBufferType.VarInt))
            return (int) readVar(4);
        
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
            return (int) readVar(5);
        
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
            return readVar(9);
        
        if (bbtype.equals(ByteBufferType.LittleEndian))
            return readLittleInt64();
        
        return readBigInt64();
    }
    
    
    /**
     * Read a signed Int64 from the buffer.
     * 
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
     * Read a float from the buffer.
     *
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
    public int readVarInt()
    {
        int result = (int) readVar(5);
        return ((result << 1) ^ (-(result & 1)));
    }
    
    
    /**
     * @return int 
     */
    public int readUnsignedVarInt() { return (int) readVar(5); }
    
    
    /**
     * @return long 
     */
    public long readVarLong()
    {
        long result = readVar(9);
        return ((result << 1) ^ (-(result & 1)));
    }
    
    
    /**
     * @return long 
     */
    public long readUnsignedVarLong() { return readVar(9); }
    
    
    /**
     * @param maxSize int 
     * @return        long 
     */
    protected long readVar(int maxSize)
    {
        long result = 0L;
        
        byte head;
        byte size = 0;
        
        do {
            head = readByte();
            result |= (long) (head & 0x7F) << (7 * size++);
            
            if (size > maxSize)
                return 0;
            
        } while ((head & 0x80) == 0x80);
        
        return result;
    }

    
}
