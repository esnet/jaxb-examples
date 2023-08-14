package net.es.examples.jaxb;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import net.es.examples.jaxb.topology.NmlTopologyType;
import net.es.examples.jaxb.topology.ObjectFactory;

import java.io.IOException;

/**
 * A singleton to load the very expensive NMWG JAXBContext once.
 *
 * @author hacksaw
 */
public class NmlParser extends JaxbParser {
    private static final String PACKAGES = "net.es.examples.jaxb.topology";
    private static final ObjectFactory factory = new ObjectFactory();

    /**
     * Constructor initializes the packages to scan for JAXB objects.
     */
    private NmlParser() {
        super(PACKAGES);
    }

    /**
     * An internal static class that invokes our private constructor on object
     * creation.
     */
    private static class ParserHolder {
        public static final NmlParser INSTANCE = new NmlParser();
    }

    /**
     * Convert an topology XML document into JAXB objects.
     *
     * @param xml
     * @return
     * @throws JAXBException
     * @throws IllegalArgumentException
     */
    public NmlTopologyType xml2Jaxb(String xml) throws JAXBException, IllegalArgumentException {
        return this.xml2Jaxb(NmlTopologyType.class, xml);
    }

    /**
     * Returns an instance of this singleton class.
     *
     * @return An object of the NmwgParser.
     */
    public static NmlParser getInstance() {
            return ParserHolder.INSTANCE;
    }

    public NmlTopologyType readTopology(String filename) throws JAXBException, IOException {
        return this.parseFile(NmlTopologyType.class, filename);
    }

    public void writeTopology(String file, NmlTopologyType nml) throws JAXBException, IOException {
        // Parse the specified file.
        JAXBElement<NmlTopologyType> element = factory.createTopology(nml);
        this.writeFile(element, file);
    }
}
