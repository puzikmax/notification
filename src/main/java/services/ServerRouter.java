package services;

import com.example.generate.ListOfString;
import com.example.generate.RouterSenderServiceImplService;

import java.net.URL;
import java.util.List;

public class ServerRouter implements Router {

    URL url;

    public ServerRouter() {
        try {
            url = new URL("http://localhost:8085/ws/router?wsdl");
        } catch (Exception e) {
            System.out.println("URL not found");
        }
    }

    @Override
    public void notifyUser(String user, String message) {
        getService().getRouterSenderServiceImplPort().notifyUser(user, message);
    }

    @Override
    public List<String> getAdmins() {
        ListOfString list = getService().getRouterSenderServiceImplPort().getAdmins();
        return list.getItem();
    }

    @Override
    public List<String> getLecturers() {
        ListOfString list = getService().getRouterSenderServiceImplPort().getLecturers();
        return list.getItem();
    }

    @Override
    public List<String> getThreeDaysNotTrackingUsers() {
        ListOfString list = getService().getRouterSenderServiceImplPort().getThreeDaysNotTrackingUsers();
        if (getService() == null) {
            throw new RuntimeException("Нет данных");
        }
        return list.getItem();
    }

    @Override
    public List<String> getOneDaysNotTrackingUsers() {
        ListOfString list = getService().getRouterSenderServiceImplPort().getOneDaysNotTrackingUsers();
        return list.getItem();
    }

    private RouterSenderServiceImplService getService() {
        RouterSenderServiceImplService service = new RouterSenderServiceImplService(url);
        return service;
    }
}
