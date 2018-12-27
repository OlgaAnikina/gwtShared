package myTimeTable.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import myTimeTable.client.Bus;
import myTimeTable.client.MyTimeTableService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class MyTimeTableServiceImpl extends RemoteServiceServlet implements MyTimeTableService {
    ClassLoader classLoader = getClass().getClassLoader();
    private static String path = "sourse.xml";
  //  private String path = classLoader.getResource("sourse.xml").getPath();


    //private String path = "C:\\Users\\mik\\Documents\\IdeaProject\\MyTimeTable\\resourse\\sourse.xml";
    private Document doc;

    public MyTimeTableServiceImpl() {

        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            System.out.println(inputFile);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            this.doc = doc;
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        System.out.println("doc  " +   doc);


    }

    public String addBuss() {
        String res = "";
        String number, start, end, time, result = "";
        Bus bus = new Bus();
        try {


            NodeList nList = doc.getElementsByTagName("bus");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                 /*  System.out.println("Bus number : " + eElement.getAttribute("id"));
                   System.out.println("firstPoint : " + eElement.getElementsByTagName("firstPoint").item(0).getTextContent());
                   System.out.println("endPoint  : "  + eElement.getElementsByTagName("endPoint").item(0).getTextContent());
                   System.out.println("Time in the way : " + eElement.getElementsByTagName("time").item(0).getTextContent());*/
                    number = eElement.getAttribute("id");
                    start = eElement.getElementsByTagName("firstPoint").item(0).getTextContent();
                    end = eElement.getElementsByTagName("endPoint").item(0).getTextContent();
                    time = eElement.getElementsByTagName("time").item(0).getTextContent();
                    bus.setBusNumber(number);
                    bus.setFirstPoint(start);
                    bus.setEndPoint(end);
                    bus.setStart(time);

                    res = number + "-" + start + "-" + end + "-" + time;
                    result = result + res + "!";


                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        // String results = pressNextPage("1" + "/" +result);
        //System.out.println(results);
        return result;
    }

    public String getMessage(String msg) {
        return addBuss();

    }

    public String nextAddBus(String str) {

        String[] list = str.split("/");


        System.out.println("in next " + list);
        String sort = addBus(list[1]);
        String number = list[0] + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);

    }

    public String addBus(String str) {
        System.out.println(str);
        String bus[] = str.split("-");
        System.out.println(path);
        String string = parse();

        String data[] = string.split("!");

        try {
            /*File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = dBuilder.parse(inputFile);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            Element newBus = doc.createElement("bus");

            newBus.setAttribute("id", String.valueOf(bus[0]));

            Element departure = doc.createElement("firstPoint");
            departure.setTextContent(bus[1]);
            Element destination = doc.createElement("endPoint");
            destination.setTextContent(bus[2]);
            Element travelTime = doc.createElement("time");
            travelTime.setTextContent(bus[3]);
            //System.out.println("In TRY!");

            newBus.appendChild(departure);
            newBus.appendChild(destination);
            newBus.appendChild(travelTime);

            doc.getElementsByTagName("class").item(0).appendChild(newBus);
            // System.out.println(doc);

            //Element root = doc.getDocumentElement();
            //root.appendChild(newBus);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            /*
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(doc);
            */
            // transformer.transform(input, output);
            transformer.transform(source, result);


            //TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Transformer transformer = transformerFactory.newTransformer();
            //DOMSource source = new DOMSource(doc);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return addBuss();

    }

    public String parse() {
        String result = "";
        try {

            NodeList nList = doc.getElementsByTagName("bus");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (temp == nList.getLength() - 1) {
                        result += separateLine(eElement);
                    } else {
                        result += separateLine(eElement) + "!";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String separateLine(Element eElement) {
        return eElement.getAttribute("id") + "-"
                + eElement.getElementsByTagName("firstPoint")
                .item(0)
                .getTextContent()
                + "-"
                + eElement.getElementsByTagName("endPoint")
                .item(0)
                .getTextContent()
                + "-" + eElement.getElementsByTagName("time")
                .item(0)
                .getTextContent();

    }

    public Comparator<Bus> COMPARE_BY_COUNT = new Comparator<Bus>() {
        @Override
        public int compare(Bus lhs, Bus rhs) {
            return Integer.valueOf(lhs.getBusNumber()) - Integer.valueOf(rhs.getBusNumber());
        }
    };

    public String nextDel(String str) {
        String[] list = str.split("/");
        String sort = delRow(list[1]);
        String number = list[0] + "/" + sort;
        return showPage(number);
    }

    public String delRow(String del) {
        // System.out.println("Servers string!" + del);
        try {


            XPath xpath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xpath.evaluate("//*[@id=" + del + "]", doc, XPathConstants.NODE);

            doc.getElementsByTagName("class").item(0).removeChild(node);


            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(path));
            Source input = new DOMSource(doc);

            transformer.transform(input, output);


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return addBuss();
    }

    public String sorts(String result) {
        String resStr;
        // int count  = Integer.parseInt(result);
        Bus ss = new Bus();
        Bus tmp = new Bus();
        ArrayList<Bus> res = new ArrayList<Bus>();
        res = tmp.parseIntoList(addBuss());
        //res = tmp.parseIntoList(result);
        tmp = res.get(0);
        //System.out.println(result);
        //System.out.println("Go to!!11111");
        for (int j = 0; j < res.size(); j++) {
            for (int i = 0; i < res.size() - 1; i++) {
                if (Integer.parseInt(res.get(i).getBusNumber()) > Integer.parseInt(res.get(i + 1).getBusNumber())) {
                    tmp = res.get(i);
                    // System.out.println("In for!!");
                    // System.out.println(result);
                    res.set(i, res.get(i + 1));
                    res.set(i + 1, tmp);
                } else {
                    System.out.println("It is else");
                }
            }
        }
        resStr = ss.listParseToStr(res);

        return resStr;
    }

    public String nextSortTime(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sortTime(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String nextSortPoint(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sortPoint(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String nextSortEndPoint(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sortEndPoint(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String sortTime(String result) {
        String resStr;

        Bus ss = new Bus();
        Bus tmp = new Bus();

        ArrayList<Bus> res = new ArrayList<Bus>();
        res = tmp.parseIntoList(result);
        tmp = res.get(0);
        System.out.println("Size of list" + res.size());

        System.out.println(result);

        for (int j = 0; j < res.size(); j++) {
            for (int i = 0; i < res.size() - 1; i++) {
                if (Double.parseDouble(res.get(i).getStart()) > Double.parseDouble(res.get(i + 1).getStart())) {
                    tmp = res.get(i);

                    System.out.println(result);
                    res.set(i, res.get(i + 1));
                    res.set(i + 1, tmp);
                } else {
                    System.out.println("It is else");
                }

            }
        }


        resStr = ss.listParseToStr(res);

        System.out.println(resStr);
        return resStr;
    }

    public String sortPoint(String result) {
        String resStr;

        Bus ss = new Bus();
        Bus tmp = new Bus();

        ArrayList<Bus> res = new ArrayList<Bus>();
        res = tmp.parseIntoList(result);
        tmp = res.get(0);
        System.out.println("Size of list" + res.size());

        System.out.println(result);

        for (int j = 0; j < res.size(); j++) {
            for (int i = 0; i < res.size() - 1; i++) {
                if (res.get(i).getFirstPoint().compareTo(res.get(i + 1).getFirstPoint()) > 0) {
                    tmp = res.get(i);
                    System.out.println(result);
                    res.set(i, res.get(i + 1));
                    res.set(i + 1, tmp);
                } else {

                }
            }
        }
        resStr = ss.listParseToStr(res);
        System.out.println("Result:");
        System.out.println(resStr);
        return resStr;
    }

    public String sortEndPoint(String result) {
        String resStr;
        System.out.println("In function");
        System.out.println(result);

        Bus ss = new Bus();
        Bus tmp = new Bus();

        ArrayList<Bus> res = new ArrayList<Bus>();
        res = tmp.parseIntoList(result);
        tmp = res.get(0);
        System.out.println("Size of list" + res.size());

        System.out.println(result);

        for (int j = 0; j < res.size(); j++) {
            for (int i = 0; i < res.size() - 1; i++) {
                if (res.get(i).getEndPoint().compareTo(res.get(i + 1).getEndPoint()) > 0) {
                    System.out.println(res.get(i).getEndPoint().compareTo(res.get(i + 1).getEndPoint()));
                    tmp = res.get(i);
                    System.out.println(result);
                    res.set(i, res.get(i + 1));
                    res.set(i + 1, tmp);
                } else {

                }
            }
        }
        resStr = ss.listParseToStr(res);
        System.out.println("Result:");
        System.out.println(resStr);
        return resStr;
    }

    public String nextPageSortNum(String number) {
        String list = addBuss();
        System.out.println("in next " + list);
        String sort = sorts(list);
        number = number + "/" + sort;
        System.out.println("number " + number);
        return showPage(number);
    }

    public String nextPage(String number) {
        String list = addBuss();
        number = number + "/" + list;
        return showPage(number);
    }

    public String previousPage(String number) {
        String list = addBuss();
        number = number + "/" + list;
        return showPage(number);
    }

    public String showPage(String numberStr) {
        // String list = addBuss();
        String[] tmp = numberStr.split("/");

        int number = Integer.parseInt(tmp[0]);

        String list = tmp[1];
        System.out.println("It is lisrt" + list);
        System.out.println(number);
        System.out.println(list.toString());


        String str = parse();
        String[] data = list.split("!");
        Bus ss = new Bus();
        String result = "";

        System.out.println(data.length);
        if (number * 5 <= data.length) {
            for (int i = (number - 1) * 5; i < number * 5; i++) {

                System.out.println(number);
                if (i != number * 5 - 1) {
                    result += data[i] + "!";
                } else {
                    result += data[i];

                }
            }
        } else {

            for (int i = (number - 1) * 5; i < data.length; i++) {
                if (i != data.length - 1) {
                    result += data[i] + "!";
                } else {

                    result += data[i];
                }
            }
        }
        System.out.println("Final result with i want to parse");
        System.out.println(result);
        if (number >= data.length / 5 + 2) {
            result = null;
        }
        return result;
    }
}