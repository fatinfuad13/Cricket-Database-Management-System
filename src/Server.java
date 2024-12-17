import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    private static final int PORT = 1234; // Port to listen on
    private static HashMap<String, String> userCredentials = new HashMap<>();
    // Map manager id to password

    static {
        userCredentials.put("KolkataKnightRiders", "KKR");
        userCredentials.put("RoyalChallengersBangalore", "RCB");
        userCredentials.put("MumbaiIndians","MI");
        userCredentials.put("ChennaiSuperKings","CSK");
        userCredentials.put("DelhiCapitals","DC");
        userCredentials.put("RajasthanRoyals","RR");
        userCredentials.put("LucknowSuperGiants","LSG");
        userCredentials.put("GujaratTitans","GT");
        userCredentials.put("PunjabKings","PK");
        userCredentials.put("SunrisersHyderabad","SRH");
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept client connection
                System.out.println("Connected to client: " + clientSocket.getInetAddress());

                // Create a thread to handle the client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                String request = in.readLine(); // Read the request from the client

                if (request.startsWith("LOGIN")) {
                    String[] parts = request.split(" ");
                    String username = parts[1].trim();
                    String password = parts[2].trim();

                    if (Server.userCredentials.containsKey(username) && Server.userCredentials.get(username).equals(password)) {
                        out.println("SUCCESS"); // Send response to client
                    } else {
                        out.println("FAILURE");
                    }
                }

                // Add other requests like transfer market handling here

                socket.close(); // Close the connection
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}

