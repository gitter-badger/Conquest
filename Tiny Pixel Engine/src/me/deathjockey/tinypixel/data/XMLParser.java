package me.deathjockey.tinypixel.data;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An XML document reader/writer.
 *
 * @author Kevin Yang
 */
public class XMLParser {

	/** Reader/writer modes, name should be self explanatory. */
	public static final int TYPE_READ_FILE = 0, TYPE_WRITE = 1;

	private Path     filePath;
	private Document document;

	/**
	 * Creates an XML reader from a string of XML data.
	 *
	 * @param xmlData String of valid XML data
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public XMLParser(String xmlData) throws ParserConfigurationException, IOException, SAXException {
		filePath = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource input = new InputSource();
		input.setCharacterStream(new StringReader(xmlData));

		document = dBuilder.parse(input);
		document.normalize();
	}

	/**
	 * Generic XML file representation
	 *
	 * @param filePath File location
	 * @param type Open type (READ/WRITE)
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public XMLParser(Path filePath, int type) throws ParserConfigurationException, IOException, SAXException {
		this.filePath = filePath;
		setupInstance(type);
	}

	/**
	 * Initializes the XMLParser for the desired operations.
	 * @param type Type of operation: READ/WRITE
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void setupInstance(int type) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		if (type == TYPE_READ_FILE) {
			document = dBuilder.parse(filePath.toString());
			document.normalize();
		}
		if (type == TYPE_WRITE) {
			document = dBuilder.newDocument();
		}
	}

	public Document getDocument() {
		return document;
	}

	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Writes the assigned data into a physical xml file.
	 *
	 * @param document Document root node for the XML file.
	 * @throws Exception
	 */
	public void write(Document document) throws Exception {

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer robotsInDisguise = tFactory.newTransformer();

		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(Files.newOutputStream(filePath));
		robotsInDisguise.transform(source, result);
	}
}
