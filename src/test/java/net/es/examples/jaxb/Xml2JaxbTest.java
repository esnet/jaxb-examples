package net.es.examples.jaxb;

import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import net.es.examples.jaxb.nsa.NsaType;
import net.es.examples.jaxb.topology.NmlTopologyType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This module tests the conversion of XML documents to JAXB objects.
 *
 * @author hacksaw
 */
@Slf4j
public class Xml2JaxbTest {
    private final static net.es.examples.jaxb.topology.ObjectFactory topologyFactory = new net.es.examples.jaxb.topology.ObjectFactory();
    private final static net.es.examples.jaxb.nsa.ObjectFactory nsaFactory = new net.es.examples.jaxb.nsa.ObjectFactory();

    /**
     * Test the conversion of an NSA XML document to corresponding NSA JAXB objects.
     *
     * @throws JAXBException
     */
    @Test
    public void xml2NsaJaxb() throws JAXBException {
        NsaType xml2Jaxb = NsaParser.getInstance().xml2Jaxb(NSA_DOCUMENT);
        log.debug("Parsed XML document nsaId=" + xml2Jaxb.getId());
        assertEquals("urn:ogf:network:geant.net:2013:nsa", xml2Jaxb.getId());
    }

    /**
     * Test the conversion of an NSI Topology XML document to corresponding Topology JAXB objects.
     *
     * @throws JAXBException
     */
    @Test
    public void xml2TopologyJaxb() throws JAXBException {
        NmlTopologyType xml2Jaxb = NmlParser.getInstance().xml2Jaxb(TOPOLOGY_DOCUMENT);
        log.debug("Parsed XML document topologyId=" + xml2Jaxb.getId());
        assertEquals("urn:ogf:network:ja.net:2013:topology", xml2Jaxb.getId());
    }

    final String NSA_DOCUMENT = """
        <ns5:nsa xmlns:nml="http://schemas.ogf.org/nml/2013/05/base#"
                xmlns:vc="urn:ietf:params:xml:ns:vcard-4.0"
                xmlns:ns4="http://schemas.ogf.org/nsi/2013/12/services/definition"
                xmlns:ns5="http://schemas.ogf.org/nsi/2014/02/discovery/nsa"
                xmlns:nsi="http://schemas.ogf.org/nsi/2013/09/topology#"
                id="urn:ogf:network:geant.net:2013:nsa"
                version="2015-02-06T13:18:33.922Z">
            <name>geant.net</name>
            <softwareVersion>AutoBAHN 3.1</softwareVersion>
            <adminContact>
                <vc:vcard>
                    <vc:kind>
                        <vc:text>org</vc:text>
                    </vc:kind>
                    <vc:fn>
                        <vc:text>GEANT</vc:text>
                    </vc:fn>
                    <vc:email>
                        <vc:text>mdsd@geant.net</vc:text>
                    </vc:email>
                </vc:vcard>
            </adminContact>
            <location>
                <longitude>8.24</longitude>
                <latitude>49.1</latitude>
            </location>
            <networkId>urn:ogf:network:geant.net:2013:topology</networkId>
            <networkId>urn:ogf:network:heanet.ie:2013:topology</networkId>
            <networkId>urn:ogf:network:ja.net:2013:topology</networkId>
            <networkId>urn:ogf:network:pionier.net.pl:2013:topology</networkId>
            <networkId>urn:ogf:network:funet.fi:2013:topology</networkId>
            <networkId>urn:ogf:network:grnet.gr:2013:topology</networkId>
            <networkId>urn:ogf:network:dfn.de:2013:topology</networkId>
            <networkId>urn:ogf:network:deic.dk:2013:topology</networkId>
            <interface>
                <type>application/vnd.ogf.nsi.cs.v2.provider+soap</type>
                <href>https://prod-bod.geant.net:8091/nsi/ConnectionProvider</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:geant.net:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:heanet.ie:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:ja.net:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:pionier.net.pl:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:funet.fi:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:grnet.gr:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:dfn.de:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.topology.v2+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/network/urn:ogf:network:deic.dk:2013:topology</href>
            </interface>
            <interface>
                <type>application/vnd.ogf.nsi.discovery.v1+xml</type>
                <href>http://bodportal.geant.net:8080/autobahn-ts/export/nsa/urn:ogf:network:geant.net:2013:nsa</href>
            </interface>
            <feature type="vnd.ogf.nsi.cs.v2.role.uPA"/>
            <feature type="vnd.ogf.nsi.cs.v2.role.aggregator"/>
            <peersWith>urn:ogf:network:es.net:2013:nsa:nsi-aggr-west</peersWith>
            <peersWith>urn:ogf:network:nordu.net:2013:nsa</peersWith>
            <peersWith>urn:ogf:network:surfnet.nl:1990:nsa:safnari</peersWith>
            <other>
                <gns:TopologyReachability xmlns:gns="http://nordu.net/namespaces/2013/12/gnsbod">
                    <Topology cost="1" id="urn:ogf:network:manlan.internet2.edu:2013"/>
                    <Topology cost="1" id="urn:ogf:network:heanet.ie:2013:topology"/>
                    <Topology cost="1" id="urn:ogf:network:ja.net:2013:topology"/>
                    <Topology cost="1" id="urn:ogf:network:funet.fi:2013:topology"/>
                    <Topology cost="1" id="urn:ogf:network:nordu.net:2013:topology"/>
                    <Topology cost="1" id="urn:ogf:network:grnet.gr:2013:topology"/>
                    <Topology cost="1" id="urn:ogf:network:surfnet.nl:1990:production7"/>
                    <Topology cost="1" id="urn:ogf:network:deic.dk:2013:topology"/>
                    <Topology cost="1" id="urn:ogf:network:pionier.net.pl:2013:topology"/>
                    <Topology cost="2" id="urn:ogf:network:es.net:2013"/>
                </gns:TopologyReachability>
            </other>
        </ns5:nsa>""";

    final String TOPOLOGY_DOCUMENT = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <nml:Topology xmlns:nml="http://schemas.ogf.org/nml/2013/05/base#"
                xmlns:ns3="http://schemas.ogf.org/nsi/2013/12/services/definition"
                xmlns:nsi="http://schemas.ogf.org/nsi/2013/09/topology#"
                xmlns:vc="urn:ietf:params:xml:ns:vcard-4.0"
                id="urn:ogf:network:ja.net:2013:topology" version="2015-12-02T05:26:58.087Z">
            <nml:name>ja.net</nml:name>
            <nml:BidirectionalPort id="urn:ogf:network:ja.net:2013:topology:bonfire-1">
                <nml:name>bonfire-1</nml:name>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:bonfire-1-in"/>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:bonfire-1-out"/>
            </nml:BidirectionalPort>
            <nml:BidirectionalPort id="urn:ogf:network:ja.net:2013:topology:caliban-ethfib">
                <nml:name>caliban-ethfib</nml:name>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:caliban-ethfib-in"/>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:caliban-ethfib-out"/>
            </nml:BidirectionalPort>
            <nml:BidirectionalPort id="urn:ogf:network:ja.net:2013:topology:p-to-janet">
                <nml:name>p-to-janet</nml:name>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:p-to-janet-in"/>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:p-to-janet-out"/>
            </nml:BidirectionalPort>
            <nml:BidirectionalPort id="urn:ogf:network:ja.net:2013:topology:ganymede-ethfib">
                <nml:name>ganymede-ethfib</nml:name>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ganymede-ethfib-in"/>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ganymede-ethfib-out"/>
            </nml:BidirectionalPort>
            <nml:BidirectionalPort id="urn:ogf:network:ja.net:2013:topology:ge-1__0__1">
                <nml:name>ge-1__0__1</nml:name>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__0__1-in"/>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__0__1-out"/>
            </nml:BidirectionalPort>
            <nml:BidirectionalPort id="urn:ogf:network:ja.net:2013:topology:ge-1__1__5">
                <nml:name>ge-1__1__5</nml:name>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__1__5-in"/>
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__1__5-out"/>
            </nml:BidirectionalPort>
            <ns3:serviceDefinition id="urn:ogf:network:ja.net:2013:topologyServiceDefinition:EVTS.A-GOLE">
                <name>GLIF Automated GOLE Ethernet VLAN Transfer Service</name>
                <serviceType>http://services.ogf.org/nsi/2013/07/definitions/EVTS.A-GOLE</serviceType>
            </ns3:serviceDefinition>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:bonfire-1-out">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">940-951</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasInboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:bonfire-1-in">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">940-951</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:caliban-ethfib-out">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2020</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasInboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:caliban-ethfib-in">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2020</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:p-to-janet-out">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2020</nml:LabelGroup>
                    <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#isAlias">
                        <nml:PortGroup id="urn:ogf:network:geant.net:2013:topology:p-to-geant-in"/>
                    </nml:Relation>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasInboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:p-to-janet-in">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2020</nml:LabelGroup>
                    <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#isAlias">
                        <nml:PortGroup id="urn:ogf:network:geant.net:2013:topology:p-to-geant-out"/>
                    </nml:Relation>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ganymede-ethfib-out">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2020</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasInboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ganymede-ethfib-in">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2020</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__0__1-out">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2022</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasInboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__0__1-in">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2022</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__1__5-out">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2022</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasInboundPort">
                <nml:PortGroup id="urn:ogf:network:ja.net:2013:topology:ge-1__1__5-in">
                    <nml:LabelGroup labeltype="http://schemas.ogf.org/nml/2012/10/ethernet#vlan">2003-2022</nml:LabelGroup>
                </nml:PortGroup>
            </nml:Relation>
            <nml:Relation type="http://schemas.ogf.org/nml/2013/05/base#hasService">
                <nml:SwitchingService labelSwapping="true" labelType="http://schemas.ogf.org/nml/2012/10/ethernet#vlan" id="urn:ogf:network:ja.net:2013:topologyServiceDomain:a-gole:testbed:A-GOLE-EVTS">
                    <ns3:serviceDefinition id="urn:ogf:network:ja.net:2013:topologyServiceDefinition:EVTS.A-GOLE">
                        <name>GLIF Automated GOLE Ethernet VLAN Transfer Service</name>
                        <serviceType>http://services.ogf.org/nsi/2013/07/definitions/EVTS.A-GOLE</serviceType>
                    </ns3:serviceDefinition>
                </nml:SwitchingService>
            </nml:Relation>
        </nml:Topology>""";
}
