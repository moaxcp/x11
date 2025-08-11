package com.github.moaxcp.x11.struct;

public interface StructMember<T> {

  /**
   * Creates a new deep copy of this field.
   * @return
   */
  StructMember<T> copy();

  /**
   * Returns the position of this field in a struct
   * @return
   */
  int getPosition();

  /**
   * Sets the position of this field in a struct
   * @param position
   */
  void setPosition(int position);

  /**
   * Returns the offset of this field within the provided struct
   * @param struct
   * @return
   */
  default int getOffset(Struct struct) {
    int offset = struct.getOffset();
    for (int i = 0; i < getPosition(); i++) {
      offset += struct.getType().getType(i).getByteLength(struct);
    }
    return offset;
  }

  /**
   * Returns the size of this object in the provided struct.
   * @param struct
   * @return
   */
  int getByteLength(Struct struct);

  /**
   * Returns the value of this field in the struct
   * @param struct
   * @return
   */
  T get(Struct struct);

  /**
   * Sets the value of this field in the struct
   * @param struct
   * @param data
   */
  void set(Struct struct, T data);
}
