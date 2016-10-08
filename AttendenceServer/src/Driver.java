import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws InterruptedException {        
        try {
        	Core.loadData();
            // initializing the Socket Server
        	Server.start();
            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
