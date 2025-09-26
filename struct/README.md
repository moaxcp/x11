# Very Binary

A fast implementation of C structs in Java.

Features include

* All sorts of data types: int8, uint8, int16, uint16, int32, uint32, int64, uint64, float32, float64, structs, and arrays.
* Uses primitives instead of wrapper classes when possible.
* Position-based lookups instead of strings (maybe become optional in the future).
* A schema that can be reused in many structs (sharing type definitions saves memory)
* A fluent api for building schemas and structs.

A struct is an object that combines a schema of fields and memory (byte array). It provides a number of convenient 
methods for reading and writing data in memory based on the schema. Like a pointer a struct can be changed to point
to different memory for reuse.

Memory can be Little Endian or Big Endian. Currently, it is limited to the max size of a byte array.

This project is built to support the protocol in x11-client, which has enough real-world examples of structs.