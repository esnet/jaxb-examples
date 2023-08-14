package net.es.examples.jaxb;

import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import net.es.examples.jaxb.nsa.NsaType;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hacksaw
 */
@Slf4j
public class EncoderTest {
  private final static net.es.examples.jaxb.nsa.ObjectFactory nsaFactory = new net.es.examples.jaxb.nsa.ObjectFactory();

  /**
   * This test builds a small NSA JAXB document and converts it to an XML document.
   *
   * @throws DatatypeConfigurationException
   * @throws IllegalArgumentException
   * @throws JAXBException
   */
  @Test
  public void encodeNsaDocument() throws DatatypeConfigurationException, IllegalArgumentException, JAXBException {
    log.debug("encodeNsaDocument nsaId = {}", "urn:ogf:network:example.com:2013:nsa:vixen");

    NsaType nsa = nsaFactory.createNsaType();
    nsa.setId("urn:ogf:network:example.com:2013:nsa:vixen");
    nsa.setVersion(XmlUtilities.longToXMLGregorianCalendar(System.currentTimeMillis()));
    nsa.setExpires(XmlUtilities.longToXMLGregorianCalendar(System.currentTimeMillis() + 100000L));
    nsa.setName("Example NSA");
    nsa.setSoftwareVersion("ExampleNsa-Version-1.0");
    nsa.setStartTime(nsa.getVersion());
    nsa.getNetworkId().add("urn:ogf:network:example.com:2013:network:theworkshop");

    String xml = NsaParser.getInstance().jaxb2Xml(nsaFactory.createNsa(nsa));
    log.debug(xml);

    NsaType decoded_nsa = NsaParser.getInstance().xml2Jaxb(xml);
    assertEquals(decoded_nsa.getId(), nsa.getId());
  }

}
