package xmlupdater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XpathUpdater{
    public static void updateXmlValueInFile(String fileInputPath, String fileOutputPath,
            String name, String value) throws IOException {
    	
    	if (name==null || name.trim().isEmpty())
    		return;

        final DocumentBuilderFactory documentFactory = 
            DocumentBuilderFactory.newInstance();

        	String xpathExp="//"+name;
        	
            DocumentBuilder docBuilder=null;
			try {
				docBuilder = documentFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Document doc=null;
			try {
				doc = docBuilder.parse(fileInputPath);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            final XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodes=null;
			try {
				nodes = (NodeList) xpath.evaluate(xpathExp, doc,
				        XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//using a predictive memory model
            for (int i = 0, len = nodes.getLength(); i < len; i++) {
                Node node = nodes.item(i);
                node.setTextContent(value);
            }

            Transformer transformer=null;
			try {
				transformer = TransformerFactory.newInstance().newTransformer();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       

            StreamResult result = new StreamResult(new FileWriter(fileOutputPath));
            try {
				transformer.transform(new DOMSource(doc), result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            result.getWriter().flush();
            result = null;

        
    }
    
   static String[] getArrayFromCommaSeperatedValues(String value){
    	
    	return value.split(",");
    	
    }
    
    public static void main(String[] args) throws IOException{
    
    	Props props=new Props();
    	
    	Properties property=props.getPropertiesFromFile("test.properties");
    	
    	
    	String basePath=System.getProperty("user.dir");

    	String inputFolderPath=basePath+"/src/test/resources/input/";
    	
    	String ouputFolderPath=basePath+"/src/test/resources/output/";

    	
    	for (Object key:property.keySet()){
    		
    		Object value=property.get(key);
    		
    		String[] arr=getArrayFromCommaSeperatedValues((String)value);
    		
    		String pathSource=inputFolderPath+arr[0];
    		
    		String pathDestination=ouputFolderPath+arr[1];
    		
    		String elementName=arr[2];
    		
    		String elementValue=arr[3];

    		updateXmlValueInFile(pathSource,pathDestination,elementName,elementValue);
    		
    	}
    	
    	
    }
    
    
}