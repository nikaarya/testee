package niyo;

import niyo.fileutils.FileReader;
import niyo.repository.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {



    private static Service service;

    public static void main(String[] args) {

        UserDAO userdao = new UserDAOWithJPA();


        userdao.removeUser("941119-0000");

        var dataBase = new UserDAOWithJPA();
        service = new Service(dataBase);

        /*File file = new File("src" + File.separator + "resources" + File.separator + "index.html");
        new Server().readFromFile(file);*/

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(3030);
            System.out.println(Thread.currentThread());

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {

        System.out.println(Thread.currentThread());
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String url = readHeaders(input);

            if (url.equals("/users"))
                service.handle(url);
            else {

            /*Map<String, URLHandler> routes = new HashMap<>();

            routes.put("/products", Server::handleProductsURL);
            routes.put("/todos", new TodosHandler());

            var handler = routes.get(url);
            if (handler != null)
                handler.handleURL();*/


                var output = new PrintWriter(socket.getOutputStream());
            /*String page = """
                    <html>
                    <head>
                        <title>Hello World!</title>
                    </head>
                    <body>
                    <h1>Hellow There</h1>
                    <div>First Page</div>
                    </body>
                    </html>
                    """;*/
                File file = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + url);
                byte[] page = FileReader.readFromFile(file);

                String contentType = Files.probeContentType(file.toPath());

                if (file.exists()) {
                    output.println("HTTP/1.1 200 OK");
                    output.println("Content-Length: " + page.length);
                    output.println("Content-Type: " + contentType);
                    output.println("");
                    output.flush();
                } else {
                    output.println("HTTP/1.1 404 Not found");
                    output.println("Content-Length: " + page.length);
                    output.println("Content-Type: " + contentType);
                    output.println("");
                    output.flush();
                }

                var dataOut = new BufferedOutputStream(socket.getOutputStream());
                dataOut.write(page);
                dataOut.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readHeaders(BufferedReader input) throws IOException {
        String requestUrl = "";
        while (true) {
            String headerLine = input.readLine();
            if (headerLine.startsWith("GET")) {
                requestUrl = headerLine.split(" ") [1];
            } else if (headerLine.startsWith("POST")) {
                requestUrl = headerLine.split(" ") [2];
            }
            System.out.println(headerLine);
            if (headerLine.isEmpty())
                break;
        }
        return requestUrl;
    }

    private static String handleProductsURL() {
        return "";
    }

}