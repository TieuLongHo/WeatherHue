package org.WeatherLight.TinkerForge;
import com.tinkerforge.*;

public class TinkerForgeConnection{



   private TinkerForgeConnection(){

   }

    public static IPConnection connect(final String HOST, final int PORT) throws AlreadyConnectedException, NetworkException {
        IPConnection ipcon = new IPConnection(); // Create IP connection
        ipcon.connect(HOST, PORT);
        return ipcon;
    }




    public static void disconnect(IPConnection ipcon) throws NotConnectedException {
        ipcon.disconnect();
    }

}
