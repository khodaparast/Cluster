package uc;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.Map;

import static uc.Util.tagQuestionsMap;


/**
 * Created by "P.Khodaparast" on 11/25/2016.
 */
public class XMLParser {
    public static void main(String[] args) throws Exception {
        Util util = new Util();

        //Parse XML
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream("D:/IR/Tamrin-01/dataset/Posts.xml");
//        InputStream in = new FileInputStream("C:/Users/pari/Desktop/Data-mining/final project/javaResources/src/sources/samplePosts2.xml");

        XMLStreamReader parser = inputFactory.createXMLStreamReader(in);
        parser.nextTag();

        int i = 0;

        while (parser.hasNext()) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (parser.getLocalName().equals("row")) {
                    String qId = parser.getAttributeValue(null, "Id");
                    String postTypeId = parser.getAttributeValue(null, "PostTypeId");

                    if (Long.parseLong(postTypeId) == 1) {
                        String Tags = parser.getAttributeValue(null, "Tags");
                        util.questionOFTags(Tags, qId);

                        i++;
                        System.out.println("i : " + i);
                    }
                }
            }
        }
        Map<String, Float> dissimMap = Util.getDissimilarityMap(tagQuestionsMap);
        util.makeDissimMatrix(dissimMap);


    }
}
