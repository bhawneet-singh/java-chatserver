package com.bhawneet;
import java.net.*;
import java.util.*;

public class Server
{
    public static List<Socket> clientList = new ArrayList<>();
    public static HashMap<String,String> userData = new HashMap<>();
    public static final int PORT = 8080;

    static
    {
        userData.put("bhawneet","bhawneet@123");
        userData.put("abhay","abhay@123");
        userData.put("jatin","jatin@123");
        userData.put("atul","atul@123");
    }
    public static void main(String [] args)
    {
        try(var server = new ServerSocket(PORT))
        {
            System.out.println("listening for connection on port " + PORT + " ...");
            while(true)
            {
                Socket socket = null;
                try
                {
                    socket = server.accept();
                    System.out.println("client connected : " + socket);
                    UserAuthentication uAuth = new UserAuthentication(socket);
                    if(!uAuth.isAuthentic())
                    {
                        socket.close(); 
                        continue;
                    }
                    clientList.add(socket);
                    var thread = new Thread(new ServerHandler(socket,uAuth.getUserName()));
                    thread.start();
                }
                catch(Exception e) { e.printStackTrace(); }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            for(Socket item : clientList)
                try{ item.close(); } catch (Exception e) { }
        }
    }
}