package org.openhab.binding.woehlke.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.eclipse.jetty.client.HttpClient;
import org.slf4j.LoggerFactory;

public class ElectricityOutletHandler {

    Logger logger = LoggerFactory.getLogger(ElectricityOutletHandler.class);
    String host;
    String port;
    HttpClient client;

    public ElectricityOutletHandler(String host, String port) {

        client = new HttpClient();
        client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        client.start();
        this.host = host;
        this.port = port;
    }

    public WoelkeWebSteckdose update() {
        WoelkeWebSteckdose web_steckdose = null;
        String request_string = "http://" + host_name + ":" + port + "/cgi-bin/status";
        ContentExchange exchange = new ContentExchange(true);
        exchange.setURL(request_string);

        client.send(exchange);

        // Waits until the exchange is terminated
        int exchangeState = exchange.waitForDone();
exchange.

//        exchange.web_steckdose = parseStatusResponse(entity1.getContent());



        return web_steckdose;
    }

    private WoelkeWebSteckdose parseStatusResponse(InputStream content) {
        WoelkeWebSteckdose woelke = new WoelkeWebSteckdose();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        try {
            String line = "";
            while (!(line = in.readLine()).equals("</html>")) {
                System.out.println(line);
                if (line.equals("<div>")) {
                    StringTokenizer data = new StringTokenizer(in.readLine(), ";");
                    while (data.hasMoreTokens()) {
                        try {
                            String data_splitter = data.nextToken();
                            System.out.println(data_splitter);
                            StringTokenizer splitter = new StringTokenizer(data_splitter, ":");
                            String p1 = splitter.nextToken();
                            String p2 = splitter.nextToken();
                            if (p1.equals("S1") || p1.equals("S2") || p1.equals("S3")) {
                                if (p1.equals("S1")) {
                                    woelke.s1 = p2.equals("AN");
                                } else if (p1.equals("S2")) {
                                    woelke.s2 = p2.equals("AN");
                                } else if (p1.equals("S3")) {
                                    woelke.s3 = p2.equals("AN");
                                }

                                else if (p1.equals("T")) {
                                    woelke.temp = Double.parseDouble(p2);
                                }

                            }
                        } catch (NoSuchElementException e) {
                        }

                    } // splitting while
                } // if <div>
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("w " + woelke);
        return woelke;
    }

    public double getTemperature() {
        return update().temp;
    }

    public boolean getSocket1() {
        WoelkeWebSteckdose whoelke = update();
        return whoelke.s1;
    }

    public boolean getSocket2() {
        return update().s2;
    }

    public boolean getSocket3() {
        return update().s3;
    }

    public void setSocket1(boolean setting) {

        sendSetRequest(1, setting);
    }

    public void setSocket2(boolean setting) {
        sendSetRequest(2, setting);

    }

    public void setSocket3(boolean setting) {
        sendSetRequest(3, setting);
    }

    private void sendSetRequest(int socket_number, boolean setting) {
        String request_string = createSetRequestString(socket_number, setting);
        handleRequest(request_string);
    }

    private void sendgetRequest(int socket_number, boolean setting) {
        String request_string = createGetRequestString(socket_number, setting);
        handleRequest(request_string);
    }

    public InputStream handleRequest(String request_string) {
        ContentExchange exchange = new ContentExchange(true);
        exchange.setURL(request_string);

        client.send(exchange);

        // Waits until the exchange is terminated
        int exchangeState = exchange.waitForDone();

    }

    private String createSetRequestString(int i, boolean setting) {
        int j;
        if (setting) {
            j = 1;
        } else {
            j = 0;
        }
        return "http://" + host_name + ":" + port + "/cgi-bin/schalten?steckdose_nr=" + i + "&steckdose_soll=" + j;

    }

    void readRequest(InputStream inputStream) {
        BufferedReader test_in = new BufferedReader(new InputStreamReader(inputStream));
        try {
            System.out.println(test_in.readLine());
            System.out.println(test_in.readLine());
            System.out.println(test_in.readLine());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setHost(String host) {
        this.host_name = host;

    }

    public void setPort(int port) {
        this.port = port;

    }

    public String getHost() {
        return this.host_name;
    }

    public int getPort() {
        return this.port;
    }
}