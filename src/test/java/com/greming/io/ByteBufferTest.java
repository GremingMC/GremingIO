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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class ByteBufferTest
{
    
    
    @Test
    public void test()
    {
        test(ByteBufferType.VarInt);
        test(ByteBufferType.LittleEndian);
        test(ByteBufferType.BigEndian);
    }
    
    
    private void test(ByteBufferType type)
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
        
        writer.writeVarInt32(int32);
        writer.writeVarInt64(int64);
        
        byte[] bytes = new byte[] { 1, 1, 1, 0, 1, 1, 0, 1 };
        writer.put(bytes);
        
        String string = "ByteBuffer TEST";
        writer.writeString(string);
        
        
        Assertions.assertEquals(byte8, reader.readByte());
        Assertions.assertEquals(booleanTrue, reader.readBoolean());
        Assertions.assertEquals(int16, reader.readInt16());
        Assertions.assertEquals(int24, reader.readInt24());
        Assertions.assertEquals(int32, reader.readInt32());
        Assertions.assertEquals(int64, reader.readInt64());
        Assertions.assertEquals(float32, reader.readFloat32());
        Assertions.assertEquals(float64, reader.readFloat64());
        Assertions.assertEquals(int32, reader.readVarInt32());
        Assertions.assertEquals(int64, reader.readVarInt64());
        Assertions.assertArrayEquals(bytes, reader.get(bytes.length));
        Assertions.assertEquals(string, reader.readString());
    }
   
    
}
