GremingIO

Description
GremingIO is a library for easily encoding and decoding 

Usage
GremingIO is very easy to use, ByteBuffer is where the content we want to decode or encode is
located, ByteBufferWriter is used to write data into ByteBuffer, ByteBufferReader is used to read
data from ByteBuffer and ByteBufferType is used to specify what kind of data we want to write (VarInt, Little Endian or Big Endian).

"""code
ByteBuffer buffer = new ByteBuffer(64);

ByteBufferWriter writer = new ByteBufferWriter(buffer, ByteBufferType.LittleEndian);
writer.writeInt16((short) 32000);
writer.writeInt24(833);
writer.writeInt32(73837738);
writer.writeString("Hello world");

ByteBufferReader reader = new ByteBufferReader(buffer, ByteBufferType.LittleEndian);

""""

Installation
