package net.es.examples.jaxb;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import net.es.examples.jaxb.nsa.NsaType;
import net.es.examples.jaxb.topology.*;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import static net.es.examples.jaxb.Nsi.NSI_SERVICETYPE_EVTS;
import static net.es.examples.jaxb.Relationships.*;
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
    NmlPortGroupType pgInIsAlias = topFactory.createNmlPortGroupType();
    pgInIsAlias.setId("urn:ogf:network:NetworkA:portX-out");

    NmlPortGroupType pgOutIsAlias = topFactory.createNmlPortGroupType();
    pgOutIsAlias.setId("urn:ogf:network:NetworkA:portX-in");

    NmlPortGroupRelationType isInAliasRelation = topFactory.createNmlPortGroupRelationType();
    isInAliasRelation.setType(IS_ALIAS);
    isInAliasRelation.getPortGroup().add(pgInIsAlias);

    NmlPortGroupRelationType isOutAliasRelation = topFactory.createNmlPortGroupRelationType();
    isOutAliasRelation.setType(IS_ALIAS);
    isOutAliasRelation.getPortGroup().add(pgOutIsAlias);

    // Now we create the PortGroup and add the VLAN label.
    NmlPortGroupType pgIn = topFactory.createNmlPortGroupType();
    pgIn.setId("urn:ogf:network:dockertest.net:2021:topology:ps-in");
    pgIn.getLabelGroup().add(nmlLabelGroupType);
    pgIn.getRelation().add(isInAliasRelation);

    NmlPortGroupType pgOut = topFactory.createNmlPortGroupType();
    pgOut.setId("urn:ogf:network:dockertest.net:2021:topology:ps-out");
    pgOut.getLabelGroup().add(nmlLabelGroupType);
    pgOut.getRelation().add(isOutAliasRelation);

    // Create the Relation element and add the PortGroup.
    NmlTopologyRelationType hasInboundRelationType = topFactory.createNmlTopologyRelationType();
    hasInboundRelationType.setType(HAS_INBOUND_PORT);
    hasInboundRelationType.getPortGroup().add(pgIn);

    NmlTopologyRelationType hasOutboundRelationType = topFactory.createNmlTopologyRelationType();
    hasOutboundRelationType.setType(HAS_OUTBOUND_PORT);
    hasOutboundRelationType.getPortGroup().add(pgOut);

    // Create our serviceDefinition for the topology.
    ServiceDefinitionType sdType = topFactory.createServiceDefinitionType();
    sdType.setId("urn:ogf:network:dockertest.net:2021:topology:sd:EVTS.A-GOLE");
    sdType.setName("GLIF Automated GOLE Ethernet VLAN Transfer Service");
    sdType.setServiceType(NSI_SERVICETYPE_EVTS);

    // Now create a JAXB version of the serviceDefinition.
    JAXBElement<ServiceDefinitionType> serviceDefinition = topFactory.createServiceDefinition(sdType);

    // Now we create the PortGroup and add the portId.
    NmlPortGroupType biIn = topFactory.createNmlPortGroupType();
    biIn.setId("urn:ogf:network:dockertest.net:2021:topology:ps-in");

    NmlPortGroupType biOut = topFactory.createNmlPortGroupType();
    biOut.setId("urn:ogf:network:dockertest.net:2021:topology:ps-out");

    // We also need a switching service.
    NmlSwitchingServiceType switchingService = new NmlSwitchingServiceType();
    switchingService.setId("urn:ogf:network:dockertest.net:2021:topology:ss:EVTS.A-GOLE");
    switchingService.setLabelSwapping(true);
    switchingService.setEncoding(EthEncodingTypes.HTTP_SCHEMAS_OGF_ORG_NML_2012_10_ETHERNET.value());
    switchingService.setLabelType(EthLabelTypes.HTTP_SCHEMAS_OGF_ORG_NML_2012_10_ETHERNET_VLAN.value());
    switchingService.getAny().add(topFactory.createServiceDefinition(sdType));

    NmlSwitchingServiceRelationType inPortRelation = new NmlSwitchingServiceRelationType();
    inPortRelation.setType(HAS_INBOUND_PORT);
    inPortRelation.getPortGroup().add(biIn);
    switchingService.getRelation().add(inPortRelation);

    NmlSwitchingServiceRelationType outPortRelation = new NmlSwitchingServiceRelationType();
    outPortRelation.setType(HAS_OUTBOUND_PORT);
    outPortRelation.getPortGroup().add(biOut);
    switchingService.getRelation().add(outPortRelation);

    NmlTopologyRelationType serviceRelation = new NmlTopologyRelationType();
    serviceRelation.setType(HAS_SERVICE);
    serviceRelation.getService().add(switchingService);

    NmlBidirectionalPortType nmlBidirectionalPortType = topFactory.createNmlBidirectionalPortType();
    nmlBidirectionalPortType.setId("urn:ogf:network:dockertest.net:2021:topology:ps");
    nmlBidirectionalPortType.setName("ps");
    nmlBidirectionalPortType.getRest().add(topFactory.createPortGroup(biIn));
    nmlBidirectionalPortType.getRest().add(topFactory.createPortGroup(biOut));

    // We also need to add the unidirectional ports to the SwitchingService.
    XMLGregorianCalendar start = XmlDate.longToXMLGregorianCalendar(System.currentTimeMillis());
    XMLGregorianCalendar end = XmlDate.longToXMLGregorianCalendar(System.currentTimeMillis()+10000);

    // Create a document lifecycle.
    NmlLifeTimeType nmlLifeTimeType = topFactory.createNmlLifeTimeType();
    nmlLifeTimeType.setStart(start);
    nmlLifeTimeType.setEnd(end);

    // Create the topology document root element.
    NmlTopologyType topologyType = topFactory.createNmlTopologyType();
    topologyType.setId("urn:ogf:network:dockertest.net:2021:topology");
    topologyType.setName("dockertest.net:2021:topology");
    topologyType.setVersion(start);
    topologyType.setLifetime(nmlLifeTimeType);
    topologyType.getGroup().add(nmlBidirectionalPortType);
    topologyType.getRelation().add(hasInboundRelationType);
    topologyType.getRelation().add(hasOutboundRelationType);
    topologyType.getAny().add(serviceDefinition);
    topologyType.getRelation().add(serviceRelation);

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
