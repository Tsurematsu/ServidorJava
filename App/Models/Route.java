package App.Models;

public class Route {
    public Data_Client dataPacket; // El paquete de datos, nuestra esperanza en este trágico camino
    public String route; // La ruta que recorreremos, llena de promesas y desilusiones
    public String GET; // Datos obtenidos por GET, nuestras ilusiones efímeras
    public String POST; // Datos obtenidos por POST, nuestros sueños rotos
    public String type; // El tipo de ruta, ¿una comedia o una tragedia?
    public String extension; // La extensión, una esperanza que se desvanece en el viento
    public String root; // La raíz, el lugar desde donde partimos, pero a veces nos olvidamos de volver
    
    public Integer Item; // Un elemento, un recordatorio constante de lo efímero de la vida
    public String URL; // La URL, una ruta de sueños y desesperaciones

    public Route(Data_Client dataPacket, String route, String GET, String POST, String root) {
        this.dataPacket = dataPacket; // Nuestro querido paquete de datos, ¿qué aventuras nos deparará?
        this.route = route; // La ruta elegida, llena de esperanzas y desafíos
        this.GET = GET; // Datos obtenidos por GET, pequeñas dosis de alegría en medio del caos
        this.POST = POST; // Datos obtenidos por POST, un recordatorio amargo de nuestras limitaciones
        this.root = root; // La raíz, nuestro punto de partida y regreso, pero a veces nos perdemos en el camino
        this.URL = dataPacket.URL; // La URL que nos guía, una guía incierta en este mundo incierto
    }

    public Route(Data_Client dataPacket, String route, String GET, String POST) {
        this.dataPacket = dataPacket; // Nuestro fiel paquete de datos, ¿nos conducirá a la victoria o a la tragedia?
        this.route = route; // La ruta elegida, una elección que puede llevarnos a la gloria o al fracaso
        this.GET = GET; // Datos obtenidos por GET, pequeños destellos de esperanza en medio de la oscuridad
        this.POST = POST; // Datos obtenidos por POST, un recordatorio cruel de nuestras limitaciones y errores
        this.URL = dataPacket.URL; // La URL que nos guía, una brújula confusa en este mar tempestuoso de la programación
    }

    public Route() {
        // Un camino vacío, un vacío existencial que nos consume lentamente
    }
}
