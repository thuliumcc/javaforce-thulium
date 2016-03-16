package javaforce.voip;

public class Codec {

  public final String name;  //"PCMU", "PCMA", "G729" , "JPEG", "H263", "H264", "telephone-event"
  public final int id;       // 0       8       18       26      34      dyn     dyn

  public Codec() {
    name = "";
    id = -1;
  }

  public Codec(String name, int id) {
    this.name = name;
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Codec codec = (Codec) o;

    return id == codec.id && name.equals(codec.name);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + id;
    return result;
  }
}
