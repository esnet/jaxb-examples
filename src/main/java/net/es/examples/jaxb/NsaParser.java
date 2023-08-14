package net.es.examples.jaxb;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import net.es.examples.jaxb.nsa.NsaType;
import net.es.examples.jaxb.nsa.ObjectFactory;

import java.io.IOException;

/**
 * A singleton to load the very expensive NMWG JAXBContext once.
 *
 * @author hacksaw
 */
public class NsaParser extends JaxbParser {
    private static final String PACKAGES = "net.es.examples.jaxb.nsa";
    private static final ObjectFactory factory = new ObjectFactory();

    /**
     * Constructor initializes the packages to scan for JAXB objects.
     */
    private NsaParser() {
        super(PACKAGES);
    }

    /**
     * An internal static class that invokes our private constructor on object
     * creation.
     */
    private static class ParserHolder {
        public static final NsaParser INSTANCE = new NsaParser();
    }

    /**
     * Returns an instance of this singleton class.
     *
     * @return An object of the NmwgParser.
     */
    public static NsaParser getInstance() {
            return ParserHolder.INSTANCE;
    }

    /**
     * Convert an NSA XML document into JAXB objects.
     *
     * @param xml
     * @return
     * @throws JAXBException
     * @throws IllegalArgumentException
     */
    public NsaType xml2Jaxb(String xml) throws JAXBException, IllegalArgumentException {
        return this.xml2Jaxb(NsaType.class, xml);
    }

    /**
     * Convert contents of a file (containing an XML topology document) into JAXB objects.
     *
     * @param filename
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public NsaType readTopology(String filename) throws JAXBException, IOException {
        return this.parseFile(NsaType.class, filename);
    }

    /**
     * Write the provided XML topology document to the specified file.
     *
     * @param file
     * @param nsa
     * @throws JAXBException
     * @throws IOException
     */
    public void writeTopology(String file, NsaType nsa) throws JAXBException, IOException {
        // Parse the specified file.
        JAXBElement<NsaType> element = factory.createNsa(nsa);
        this.writeFile(element, file);
    }
}
