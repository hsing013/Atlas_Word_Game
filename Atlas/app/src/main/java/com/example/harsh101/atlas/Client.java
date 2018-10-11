package com.example.harsh101.atlas;




/* This class interacts with the server */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private String userName;
    private String pass;
    private boolean connectedToHost;
    private PrintWriter pw;
    private BufferedReader br;
    private String messageBuffer = null;

    public Client(){
        socket = null;
        userName = null;
        pass = null;
        pw = null;
        br = null;
    }

    public boolean isConnectedToHost() {
        return connectedToHost;
    }

    public String getPass() {
        return pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setConnectedToHost(boolean connectedToHost) {
        this.connectedToHost = connectedToHost;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessageBuffer(String messageBuffer) {
        this.messageBuffer = messageBuffer;
    }

    public String getMessageBuffer() {
        return messageBuffer;
    }

    public boolean connectToHost(){ //this is where the client is going to attempt to connect to the server
        //System.out.println("I enter cth");
        if (socket != null){
            try{
                socket.close();
            }
            catch (IOException e){
                e.printStackTrace();
                connectedToHost = false;
                return false;
            }
            socket = null;
        }

        socket = new Socket();
        InetSocketAddress isa = new InetSocketAddress("138.68.48.204", 12345); // this is the address of the server
        pw = null;
        br = null;

        try{
            socket.connect(isa, 1000); //tries to connect to the server, waits until 1 second
            socket.setSoTimeout(1000); //if connected, this sets the timeout for the reading of the socket
            pw = new PrintWriter(socket.getOutputStream(), true); //allows us to write to the socket
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8")); //allows us to read the socket
        }
        catch (IOException e){
            e.printStackTrace();
            connectedToHost = false;
            return false;
        }

        if (socket.isConnected()){
            connectedToHost = true;
        }

        return true;

    }



    public boolean sendMessage(String message){
        System.out.println("Size of message before: " + message.length());
        if (socket == null || !connectedToHost){
            return false;
        }

        if (message.charAt(message.length() - 1) != '\n'){
            message = message + '\n';
        }
        System.out.println("Sending: " + message + " " + "size: " + message.length());
        pw.write(message);

        pw.flush();

        return true;

    }

    /*The message encoding methology
      end of every message exists a newline
     */

    public ArrayList<String> readSocket(){
        //System.out.println("I enter");
        if (socket == null || !connectedToHost){
            return null;
        }

        if (messageBuffer == null){
            messageBuffer = "";
        }

        ArrayList<String> messages = null;

        int read = 0;

        while (read != -1){  //this reads character by character

            try{
                read = br.read();
                if (read != -1){
                    char character = (char) read;

                    messageBuffer = messageBuffer + character;
                }
                else{  //this means the socket has been disconnected
                    connectedToHost = false;
                    socket.close();
                    socket = null;
                }

            }
            catch (SocketTimeoutException e){
                break;
            }
            catch (SocketException e){
                socket = null;
                connectedToHost = false;
                break;
            }
            catch (IOException e){
                break;
            }
        }

        /*This block of code breaks the code at the newline
           If the newline doesnt exist then the message stays the message buffer
         */

        int index = messageBuffer.indexOf('\n');

        while (index != -1){
            if (messages == null){
                messages = new ArrayList<>();
            }
            String sub = messageBuffer.substring(0, index);
            messages.add(sub);
            messageBuffer = messageBuffer.substring(index + 1, messageBuffer.length());
            index = messageBuffer.indexOf('\n');
        }
        //System.out.println("I leave");
        return messages;

    }

}
