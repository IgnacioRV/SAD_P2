import java.io.*;
import java.net.*;
 
public class Client {
    public static void main(String[] args) throws IOException {
         
        if (args.length != 2) {
            System.err.println(
                "Usage: java Client <host name> <port number>");
            System.exit(1);
        }
 
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket s = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
            ) {
            String userInput;
            new ReadHandler(s).start();
            new WriteHandler(s).start();  
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server said: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }

    private static class ReadHandler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        public ReadHandler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            System.out.println("Reader Handler Thread");
        }
    }

    private static class WriteHandler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        public WriteHandler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            System.out.println("Writer Handler Thread");
        }
    }




}