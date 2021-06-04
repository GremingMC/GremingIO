package com.greming.io;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.greming.io.*;


public class ByteBufferTest
{
    
    @Test
    public void bufferTest()
    {
        validate(ByteBufferType.VarInt);
        validate(ByteBufferType.LittleEndian);
        validate(ByteBufferType.BigEndian);
    }
    
    private void validate(ByteBufferType type)
    {
        ByteBuffer buffer = new ByteBuffer(1);
        
        ByteBufferReader reader = new ByteBufferReader(buffer, type);
        ByteBufferWriter writer = new ByteBufferWriter(buffer, type);
        
        byte byte8 = 55;
        writer.writeByte(byte8);
        
        boolean booleanTrue = true;
        writer.writeBoolean(booleanTrue);
        
        short int16 = 560;
        writer.writeInt16(int16);
        
        int int24 = 150003;
        writer.writeInt24(int24);
        
        int int32 = 78438743;
        writer.writeInt32(int32);
        
        long int64 = 5775575734344L;
        writer.writeInt64(int64);
        
        float float32 = 4783.3444F;
        writer.writeFloat32(float32);
        
        double float64 = 784783.784343D;
        writer.writeFloat64(float64);
        
        byte[] bytes = new byte[] { 1, 1, 1, 0, 1, 1, 0, 1 };
        writer.put(bytes);
        
        
        Assertions.assertEquals(byte8, reader.readByte());
        Assertions.assertEquals(booleanTrue, reader.readBoolean());
        Assertions.assertEquals(int16, reader.readInt16());
        Assertions.assertEquals(int24, reader.readInt24());
        Assertions.assertEquals(int32, reader.readInt32());
        Assertions.assertEquals(int64, reader.readInt64());
        Assertions.assertEquals(float32, reader.readFloat32());
        Assertions.assertEquals(float64, reader.readFloat64());
        Assertions.assertTrue(Arrays.equals(bytes, reader.get(bytes.length)));
    }
    
    
}
