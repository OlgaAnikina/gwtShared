package myTimeTable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


import java.util.ArrayList;

@RemoteServiceRelativePath("MyTimeTableService")
public interface MyTimeTableService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);
    String addBus(String add);
    String delRow(String del);
 //   ArrayList<Bus> sort(ArrayList<Bus> arrBus);
   String sorts(String str);
    String sortTime(String res);
    String sortPoint(String res);
    String sortEndPoint(String res);
    String nextPage(String res);
    String previousPage (String res);
    String nextPageSortNum(String res);
    String nextSortTime (String res);
    String nextSortPoint(String res);
    String nextSortEndPoint(String res);
    String nextAddBus(String res);
    String nextDel(String res);



    public static class App {
        private static MyTimeTableServiceAsync ourInstance = GWT.create(MyTimeTableService.class);

        public static synchronized MyTimeTableServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
