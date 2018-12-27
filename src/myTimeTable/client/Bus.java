package myTimeTable.client;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bus implements Serializable {
    private  String firstPoint;
    private  String start;
    private  String busNumber;
    private String  endPoint;
    private ArrayList<Bus> statet;

  //  public Bus (){}
    public Bus(String busNumber, String start, String firstPoint, String endPoint) {
        this.busNumber = busNumber;
        this.start = start;
        this.firstPoint = firstPoint;
        this.endPoint = endPoint;
    }

    public Bus (){
        this.firstPoint = "";
        this.endPoint = "";
        this.busNumber = "";
        this.start = "";

    }
    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
    public void setFirstPoint(String firstPoint) {
        this.firstPoint = firstPoint;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getStart() {
        return start;
    }
    public String getBusNumber() {
        return busNumber;
    }
    public String getFirstPoint() {
        return firstPoint;
    }
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    public void setStatet(ArrayList<Bus> statet){
        this.statet = statet;
    }
    public String getEndPoint() {
        return endPoint;
    }
    public Bus parseToRow(String inRow)    {
        String[] tmp = inRow.split("-");
        this.setBusNumber(tmp[0]);
        this.setFirstPoint(tmp[1]);
        this.setEndPoint(tmp[2]);
        this.setStart(tmp[3]);
         return this;

    }
    public ArrayList<Bus> parseIntoList(String inRow)    {

        ArrayList<Bus> parseBus = new ArrayList<Bus>();
        String [] tmpRow;
        String [] tmp = inRow.split("!");
        for(int i = 0; i < tmp.length; i++)
        {   Bus tmpBus = new Bus();
            tmpRow = tmp[i].split("-");
            tmpBus.setBusNumber(tmpRow[0]);
            tmpBus.setFirstPoint(tmpRow[1]);
            tmpBus.setEndPoint(tmpRow[2]);
            tmpBus.setStart(tmpRow[3]);
            parseBus.add(tmpBus);


        }
        return parseBus;
    }
    public String listParseToStr(ArrayList<Bus> statet){
        Bus ss = new Bus();
        String result= "";
        String tmp = null;
        for(int i = 0; i < statet.size(); i++){
            tmp  =  statet.get(i).getBusNumber() + "-" + statet.get(i).getFirstPoint()  + "-"+ statet.get(i).getEndPoint() + "-" +statet.get(i).getStart();
            result = result + tmp +"!";
       /* Bus ss = new Bus();
        statet = ss.parseIntoList(res);
        return statet;*/

    }
    return result;}


}
