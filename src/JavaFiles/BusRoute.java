package JavaFiles;

public class BusRoute {
    private String routeName;
    private String startPoint;
    private String endPoint;
    private String routeDescription;
    private int busNumber;

    public BusRoute(String routeName, String startPoint, String endPoint, String routeDescription, int busNumber) {
        this.routeName = routeName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.routeDescription = routeDescription;
        this.busNumber = busNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }
}
