package at.uibk.dps.ee.guice.starter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ConfigCreator {

  public static void main(String[] args) {

    String inputData = args[0];
    String filePathAfCl = args[1];
    String filePathMappingFile = args[2];
    String schedulingType = args[3];
    String schedulingMode = args[4];
    List<String> failRates = new ArrayList<String>();
    for(int i = 5; i<args.length; i++) {
      failRates.add(args[i]);
    }
    
    String parForColl = filePathAfCl.contains("parFor") ? inputData.substring(12,inputData.length()-5).split("-")[1] : "";

    for(String failRate:failRates) {

      try {
        DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // root element
        Element configuration = doc.createElement("configuration");
        doc.appendChild(configuration);

        String className="at.uibk.dps.ee.control.modules.EnactmentAgentModule";
        Element module = createModule(doc,className,new String[]{"pauseOnStart"},new String[]{"false"});
        configuration.appendChild(module);

        className="at.uibk.dps.ee.enactables.local.calculation.FunctionFactoryModule";
        module = createModule(doc,className,new String[]{"useReliabilityWrapper","failRateLocal","failRateServerless"},new String[]{"true",failRate,failRate});
        configuration.appendChild(module);

        className="at.uibk.dps.ee.enactables.wrapper.EnactableFactoryModule";
        module = createModule(doc,className,new String[]{"useReliabilityWrapper","failRate"},new String[]{"false",failRate});
        configuration.appendChild(module);

        className="at.uibk.dps.ee.io.modules.InputReaderFileModule";
        module = createModule(doc,className,new String[]{"filePath"},new String[]{inputData});
        configuration.appendChild(module);

        className="at.uibk.dps.ee.io.modules.OutputPrinterModule";
        module = createModule(doc,className,new String[]{},new String[]{});
        configuration.appendChild(module);

        className="at.uibk.dps.ee.io.modules.SpecificationInputModule";
        module = createModule(doc,className,new String[]{"filePathAfcl","filePathMappingFile"},new String[]{filePathAfCl,filePathMappingFile});
        configuration.appendChild(module);

        className="at.uibk.dps.sc.core.modules.SchedulerModule";
        module = createModule(doc,className,new String[]{"schedulingType","schedulingMode"},new String[]{schedulingType,schedulingMode});
        configuration.appendChild(module);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(new File("generatedConfigs/" + filePathAfCl.substring(10,filePathAfCl.length()-5) + parForColl 
        + "-" + schedulingType + "-" + failRate));
        transformer.transform(source, result);

        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static Element createModule(Document doc, String className, String propertyNames[], String propertyValues[]) {
    Element module = doc.createElement("module");

    Attr attr = doc.createAttribute("class");
    attr.setValue(className);
    module.setAttributeNode(attr);

    for(int i = 0; i < propertyNames.length; i++) {
      Element propPause = doc.createElement("property");
      module.appendChild(propPause);

      Attr attr2 = doc.createAttribute("name");
      attr2.setValue(propertyNames[i]);
      propPause.setAttributeNode(attr2);

      propPause.appendChild(doc.createTextNode(propertyValues[i]));
    }
    return module;
  }
}