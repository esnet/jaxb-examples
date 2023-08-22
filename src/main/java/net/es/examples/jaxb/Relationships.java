package net.es.examples.jaxb;

/**
 *
 * @author hacksaw
 */
public class Relationships {
  // Topology relationship types.
  public final static String HAS_INBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasInboundPort";
  public final static String HAS_OUTBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort";
  public final static String HAS_SERVICE = "http://schemas.ogf.org/nml/2013/05/base#hasService";
  public final static String IS_ALIAS = "http://schemas.ogf.org/nml/2013/05/base#isAlias";
  public final static String PROVIDES_LINK = "http://schemas.ogf.org/nml/2013/05/base#providesLink";

  public static boolean hasInboundPort(String type) {
    return Relationships.HAS_INBOUND_PORT.equalsIgnoreCase(type);
  }

  public static boolean hasOutboundPort(String type) {
    return Relationships.HAS_OUTBOUND_PORT.equalsIgnoreCase(type);
  }

  public static boolean hasService(String type) {
    return Relationships.HAS_SERVICE.equalsIgnoreCase(type);
  }

  public static boolean isAlias(String type) {
    return Relationships.IS_ALIAS.equalsIgnoreCase(type);
  }

  public static boolean providesLink(String type) {
    return Relationships.PROVIDES_LINK.equalsIgnoreCase(type);
  }
}
