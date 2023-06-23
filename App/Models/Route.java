package App.Models;

public class Route {
    public Data_Client dataPacket;
    public String route;
    public String GET;
    public String POST;
    public String type;
    public String extension;
    public String root;
    
    public Integer Item;
    public String URL;
    public Route(Data_Client dataPacket, String route, String GET, String POST, String root) {
        this.dataPacket = dataPacket;
        this.route = route;
        this.GET = GET;
        this.POST = POST;
        this.root = root;
        this.URL = dataPacket.URL;
    }
    public Route(Data_Client dataPacket, String route, String GET, String POST) {
        this.dataPacket = dataPacket;
        this.route = route;
        this.GET = GET;
        this.POST = POST;
        this.URL = dataPacket.URL;
    }
    public Route() {}
}
