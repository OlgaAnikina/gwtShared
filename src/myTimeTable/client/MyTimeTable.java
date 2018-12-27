package myTimeTable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import myTimeTable.server.MyTimeTableServiceImpl;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.cellview.client.SimplePager;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.System.out;


public class MyTimeTable extends DialogBox implements EntryPoint {
    private int flag = 0;
    private Bus tmp = new Bus();
    private int count = 0;
    private ArrayList<Bus> arrBus = new ArrayList<Bus>();
    private ListDataProvider<Bus> dataProvider = new ListDataProvider<Bus>(arrBus);

    public MyTimeTable(){
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
        TextColumn<Bus> busNumber =
                new TextColumn<Bus>() {
                    @Override
                    public String getValue(Bus object) {
                        return object.getBusNumber();
                    }
                };
        table.addColumn(busNumber, "BusNumber");
        TextColumn<Bus> firstPoint
                = new TextColumn<Bus>() {
            @Override
            public String getValue(Bus object) {
                return object.getFirstPoint();
            }
        };
        table.addColumn(firstPoint, "FirstPoint");
        TextColumn<Bus> endPoint
                = new TextColumn<Bus>() {
            @Override
            public String getValue(Bus object) {
                return object.getEndPoint();
            }
        };
        table.addColumn(endPoint, "EndPoint");
        TextColumn<Bus> timeInWay
                = new TextColumn<Bus>() {
            @Override
            public String getValue(Bus object) {
                return object.getStart();
            }
        };
        table.addColumn(timeInWay, "Time");
        final SingleSelectionModel<Bus> selectionModel
                = new SingleSelectionModel<Bus>();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(
                new SelectionChangeEvent.Handler() {
                    public void onSelectionChange(SelectionChangeEvent event) {
                        Bus selected = selectionModel.getSelectedObject();
                        if (selected != null) {
                            Window.alert("You want to delete: " + selected.getBusNumber());
                            String res = count + "/" + selected.getBusNumber();
                           // Window.alert(res);
                            MyTimeTableService.App.getInstance().nextDel(res, new MyAsyncCallback(table/*,selected*/,arrBus, dataProvider));

                        }
                    }
                });

        dataProvider.addDataDisplay(table);

        table.getElement().getStyle().setMarginLeft(330,Style.Unit.PX);

        final Button add = new Button ("Add a bus");
        final Label label = new Label();
        final Button sort = new Button("Sort of bus number");
        final Button sortTime = new Button("Sort of time");
        final Button sortDeparture = new Button("Sort of departure ");
        final Button sortArrival = new Button("Sort of arrival ");
        final Button next = new Button ("Next");
        final Button back = new Button ("Back");
        final Label sortText = new Label("Choose one of sorts method:");
        DOM.setElementAttribute(add.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(next.getElement(), "id", "my-button-id1");
        DOM.setElementAttribute(sortDeparture.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(sortTime.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(sortArrival.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(sort.getElement(), "id", "my-button-id");
        DOM.setElementAttribute(back.getElement(), "id", "my-button-id1");

       HorizontalPanel  panel = new HorizontalPanel();


        panel.setWidth("300");
        panel.add(back);
        panel.getWidgetIndex(back);
        panel.insert(next,panel.getWidgetIndex(back));
        panel.getElement().getStyle().setMarginLeft(660, Style.Unit.PX);
        VerticalPanel panelWithTable = new VerticalPanel();
        HorizontalPanel  panel2 = new HorizontalPanel();
        VerticalPanel  panel3 = new VerticalPanel();
        panel3.add(sortText);
        panel3.add(add);
        panel3.add(sort);
        panel3.add(sortTime);
        panel3.add(sortDeparture);
        panel3.add(sortArrival);
        RootPanel.get().getElement().getStyle().setMarginLeft(100, Style.Unit.PX);
        panel2.add(panel3);
        panel2.add(table);
        panelWithTable.add(panel2);
        panelWithTable.add(panel);
        RootPanel.get().add(panelWithTable);


        next.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                count++;

                String countStr ="" + count ;
                if (flag ==0){
                  //  Window.alert("Flag = " + flag);
                    MyTimeTableService.App.getInstance().nextPage(countStr, new MyAsyncCallback(table, arrBus, dataProvider));}
                if (flag == 1){
                  //  Window.alert("Flag = " + flag);
                    MyTimeTableService.App.getInstance().nextPageSortNum(countStr, new MyAsyncCallback(table, arrBus, dataProvider));}
                if (flag == 2 ){ MyTimeTableService.App.getInstance().nextSortTime(countStr, new MyAsyncCallback(table, arrBus,  dataProvider));}
                if (flag == 3){MyTimeTableService.App.getInstance().nextSortPoint(countStr, new MyAsyncCallback(table, arrBus,  dataProvider));}
                if (flag == 4){MyTimeTableService.App.getInstance().nextSortEndPoint(countStr, new MyAsyncCallback(table, arrBus,  dataProvider));}

            }


        });
        back.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                count--;
                String countStr ="" + count;
                if (count <= 0) {Window.alert("It is your first page!" );count ++;
                }
                else {
                    if (flag ==0){
                    MyTimeTableService.App.getInstance().previousPage(countStr, new MyAsyncCallback(table, arrBus, dataProvider));}
                    if (flag == 1){MyTimeTableService.App.getInstance().nextPageSortNum(countStr, new MyAsyncCallback(table, arrBus, dataProvider));}
                    if (flag == 2 ){ MyTimeTableService.App.getInstance().nextSortTime(countStr, new MyAsyncCallback(table, arrBus,  dataProvider));}
                    if (flag == 3){MyTimeTableService.App.getInstance().nextSortPoint(countStr, new MyAsyncCallback(table, arrBus,  dataProvider));}
                    if (flag == 4){MyTimeTableService.App.getInstance().nextSortEndPoint(countStr, new MyAsyncCallback(table, arrBus,  dataProvider));}

                }
            }
        });
        sortDeparture.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                flag = 3;
                String str ="" + count ;// +  tmp.listParseToStr(arrBus);
                MyTimeTableService.App.getInstance().nextSortPoint(str, new MyAsyncCallback(table, arrBus,  dataProvider));
            }
        });
        sortArrival.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 4;
                String str ="" + count ;// +  tmp.listParseToStr(arrBus);
                MyTimeTableService.App.getInstance().nextSortEndPoint(str, new MyAsyncCallback(table, arrBus,  dataProvider));
/*
                String str = tmp.listParseToStr(arrBus);
                MyTimeTableService.App.getInstance().sortEndPoint(str, new MyAsyncCallback(table, arrBus,  dataProvider));
*/
            }
        });
        sort.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 1;
                String str ="" + count ;// +  tmp.listParseToStr(arrBus);
                MyTimeTableService.App.getInstance().nextPageSortNum(str, new MyAsyncCallback(table, arrBus,  dataProvider));

            }
        });
        add.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                MyTimeTable myDialog =  MyTimeTable.getInstance();
                int left = Window.getClientWidth()/ 4;
                int top = Window.getClientHeight()/ 4;
                myDialog.setPopupPosition(left, top);
                myDialog.show();

            }

        });
        sortTime.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                flag = 2;
                //String str = tmp.listParseToStr(arrBus);
                String str ="" + count ;
                MyTimeTableService.App.getInstance().nextSortTime(str, new MyAsyncCallback(table, arrBus,  dataProvider));
            }
        });



        RootPanel.get("slot2").add(label);

        setText("My First Dialog");
        TextBox busNumbers = new TextBox();
        TextBox firstPoints = new TextBox();
        TextBox endPoints = new TextBox();
        TextBox time = new TextBox();
        setAnimationEnabled(true);

        setGlassEnabled(true);

        Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String res;
                res = busNumbers.getText()+"-"+firstPoints.getText()+"-"+endPoints.getText()+"-"+time.getText();

                CellTable<Bus> buses = MyTimeTable.getInstance().getTable();
                Bus CONTACTS = new Bus(busNumbers.getText(), firstPoints.getText(),time.getText(), endPoints.getText());

                arrBus.add(CONTACTS);

                String parseRes = count + "/" + res ;
                MyTimeTableService.App.getInstance().nextAddBus(parseRes , new MyAsyncCallback(table, arrBus, dataProvider));

                MyTimeTable.this.hide();

            }
        });
        Label labels = new Label("Add information about your bus.");
        Label busNumb = new Label("Bus number.");
        Label firstpoint = new Label("First point");
        Label end = new Label("End Point");
        Label timeBus = new Label("Time");
        VerticalPanel panel1 = new VerticalPanel();
        panel1.setHeight("100");
        panel1.setWidth("300");
        panel1.setSpacing(10);
        panel1.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);

        panel1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel1.add(labels);
        panel1.add(busNumb);
        panel1.add(busNumbers);
        panel1.add(firstpoint);
        panel1.add(firstPoints);
        panel1.add(end);
        panel1.add(endPoints);
        panel1.add(timeBus);
        panel1.add(time);
        panel1.add(ok);
        setWidget(panel1);
        myTimeTable = this;
    }
    public CellTable<Bus> getTable(){return this.table;}
    private List<Bus> statet = new ArrayList<Bus>();
    private CellTable<Bus> table = new CellTable<Bus>();
    private static MyTimeTable myTimeTable;
    public  static MyTimeTable getInstance(){

        return myTimeTable;
    }

    public void onModuleLoad() {


    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private List<Bus> busList;
        private CellTable table;
        ListDataProvider<Bus> dataProvider;

        public MyAsyncCallback(CellTable table,List<Bus> statet,  ListDataProvider<Bus> dataProvider ) {
            this.table =table;
           this.busList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {


            if (result == null){Window.alert("You don't have anought page");
            }else{
            Bus ss = new Bus();
            busList.clear();
            busList.addAll(ss.parseIntoList(result));


            dataProvider.refresh();
            table.setRowCount(busList.size());
            table.redraw();}

        }

        public void onFailure(Throwable throwable) {


        }
    }

    private static class AddAsyncCallback implements AsyncCallback<String> {
        private List<Bus> busList;
        private CellTable table;
        private ListDataProvider<Bus> dataProvider;

        public AddAsyncCallback(CellTable table, List<Bus> statet ,ListDataProvider<Bus> dataProvider) {
            this.table =table;
            this.busList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {

            Bus ss = new Bus();
            Window.alert(result);

            dataProvider.refresh();
            table.setRowCount(busList.size());
            table.redraw();


        }

        public void onFailure(Throwable throwable) {


        }
    }
    private static class DelAsyncCallback implements AsyncCallback<String> {
        private List<Bus> busList;
        private CellTable table;
        private Bus busn;
        private ListDataProvider<Bus> dataProvider;

        public DelAsyncCallback(CellTable table, Bus busn, List<Bus> statet, ListDataProvider<Bus> dataProvider) {
            this.table =table;
            this.busn = busn;
            this.busList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {


            Bus ss = new Bus();

            busList.clear();

            busList.addAll(ss.parseIntoList(result));

            dataProvider.refresh();
            table.setRowCount(busList.size());
            table.redraw();



        }

        public void onFailure(Throwable throwable) {
            Window.alert("FAIL!!");

        }
    }
    private static class SortAsyncCallback implements AsyncCallback<String> {
        private ArrayList<Bus> busList;
        private CellTable table;
        private Bus busn;
        private ListDataProvider<Bus> dataProvider;

        public SortAsyncCallback(CellTable table, ArrayList<Bus> statet, ListDataProvider<Bus> dataProvider) {
            this.table =table;
            this.busList = statet;
            this.dataProvider = dataProvider;
        }

        public void onSuccess(String result) {

            Bus ss = new Bus();
         //   Window.alert(result);

            busList.clear();
         //   Collections.sort(busList, Bus.COMPARE_BY_COUNT);
            //busList.clear();
//            busList.addAll(ss.parseIntoList(result));
            dataProvider.refresh();
            table.setRowCount(busList.size());
            table.redraw();
        }
        public void onFailure(Throwable throwable) {
            Window.alert("Sorry!");

        }
    }

    private static class SortTimeAsyncCallback implements AsyncCallback<String> {
        private List<Bus> busList;
        private CellTable table;
        private Bus busn;
        private ListDataProvider<Bus> dataProvider;

        public SortTimeAsyncCallback(CellTable table, List<Bus> statet, ListDataProvider<Bus> dataProvider) {
            this.table =table;
            //  this.busn = busn;
            this.busList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {

            Bus ss = new Bus();
             busList.clear();
           // Collections.sort(busList, Bus.compareByTime);
            //busList.clear();
             busList.addAll(ss.parseIntoList(result));
            dataProvider.refresh();
            table.setRowCount(busList.size());
            table.redraw();
        }
        public void onFailure(Throwable throwable) {
            Window.alert("FAIL!!");

        }
    }
    private static class PageAsyncCallback implements AsyncCallback<String> {
        private List<Bus> busList;
        private CellTable table;
        private Bus busn;
        private ListDataProvider<Bus> dataProvider;

        public PageAsyncCallback(CellTable table, Bus busn, List<Bus> statet, ListDataProvider<Bus> dataProvider) {
            this.table =table;
            this.busn = busn;
            this.busList = statet;
            this.dataProvider = dataProvider;

        }

        public void onSuccess(String result) {
            Bus ss = new Bus();
            busList.clear();
            busList.addAll(ss.parseIntoList(result));
            dataProvider.refresh();
            table.setRowCount(busList.size());
            table.redraw();
        }

        public void onFailure(Throwable throwable) {
            Window.alert("FAIL!!");

        }
    }
}
