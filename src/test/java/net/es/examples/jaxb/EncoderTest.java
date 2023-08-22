package net.es.examples.jaxb;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import net.es.examples.jaxb.nsa.NsaType;
import net.es.examples.jaxb.topology.*;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;

import static net.es.examples.jaxb.Nsi.NSI_SERVICETYPE_EVTS;
import static net.es.examples.jaxb.Relationships.HAS_INBOUND_PORT;
import static net.es.examples.jaxb.Relationships.IS_ALIAS;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author hacksaw
 */
@Slf4j
public class EncoderTest {
  private final static net.es.examples.jaxb.nsa.ObjectFactory nsaFactory = new net.es.examples.jaxb.nsa.ObjectFactory();
  private final static net.es.examples.jaxb.topology.ObjectFactory topFactory = new net.es.examples.jaxb.topology.ObjectFactory();

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

  @Test
  public void encodeTopologyDocument() throws DatatypeConfigurationException, IllegalArgumentException, JAXBException {
    log.debug("encodeTopologyDocument: Starting");

    // We populate the label group with VLAN information.
    NmlLabelGroupType nmlLabelGroupType = topFactory.createNmlLabelGroupType();
    nmlLabelGroupType.setLabeltype(NmlEthernet.VLAN_LABEL);
    nmlLabelGroupType.setValue("1780-1787");

    // Add the isAlias relationship if present.
    NmlPortGroupType pgIsAlias = topFactory.createNmlPortGroupType();
    pgIsAlias.setId("urn:ogf:network:NetworkA:portX-out");

    NmlPortGroupRelationType isAliasRelation = topFactory.createNmlPortGroupRelationType();
    isAliasRelation.setType(IS_ALIAS);
    isAliasRelation.getPortGroup().add(pgIsAlias);

    // Now we create the PortGroup and add the VLAN label.
    NmlPortGroupType pg = topFactory.createNmlPortGroupType();
    pg.setId("urn:ogf:network:dockertest.net:2021:topology:ps-in");
    pg.getLabelGroup().add(nmlLabelGroupType);
    pg.getRelation().add(isAliasRelation);

    // Create the Relation element and add the PortGroup.
    NmlTopologyRelationType relationType = topFactory.createNmlTopologyRelationType();
    relationType.setType(HAS_INBOUND_PORT);
    relationType.getPortGroup().add(pg);

    // Add the serviceDefinition to the topology.
    ServiceDefinitionType sdType = topFactory.createServiceDefinitionType();
    sdType.setId("urn:ogf:network:dockertest.net:2021:topology:sd:EVTS.A-GOLE");
    sdType.setName("GLIF Automated GOLE Ethernet VLAN Transfer Service");
    sdType.setServiceType(NSI_SERVICETYPE_EVTS);

    // Now create a JAXB version of the serviceDefinition.
    JAXBElement<ServiceDefinitionType> serviceDefinition = topFactory.createServiceDefinition(sdType);

    // Create the topology document root element.
    NmlTopologyType topologyType = topFactory.createNmlTopologyType();
    topologyType.getRelation().add(relationType);
    topologyType.getAny().add(serviceDefinition);

    // Create the JAXB topology object.
    JAXBElement<NmlTopologyType> topology = topFactory.createTopology(topologyType);

    // Marshal the JAXB objects to XML.
    String xml = NmlParser.getInstance().jaxb2Xml(topology);
    log.debug(xml);

    // Unmarshal and test.
    NmlTopologyType decoded_top = NmlParser.getInstance().xml2Jaxb(xml);
    assertEquals(decoded_top.getId(), topologyType.getId());
  }
}
