package com.bhawneet;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client 
{
    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null; 
    private static Socket socket = null;
    public static void main(String [] args)    
    {
        if(args.length < 2)
        {
            System.out.println("<hostname> <port> are required ! ");
            System.exit(1);
        }    

        try
        {
            socket = new Socket(args[0],Integer.valueOf(args[1]));
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            var threadSend = new Thread(Client::send);
            var threadRecv = new Thread(Client::recv);
            threadRecv.start(); threadSend.start();
            threadSend.join(); threadRecv.join();
        }
        catch (IOException e) { }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(!socket.isClosed())
                try{ socket.close(); } catch (Exception e) { }
        }
    }

    private static void recv()
    {
        while(true)
        {
            try
            {
                byte [] buff = new byte[1024];
                int count = dataInputStream.read(buff,0,buff.length);
                if(count == -1)
                    break;
                System.out.write(buff,0,count);
            }
            catch(Exception e) { e.printStackTrace(); }
        }
    } 

    private static void send()
    {
        while(true)
        {
            try
            {
		var scn = new Scanner(System.in);
		var line = scn.nextLine();
		if(line.trim().isEmpty())
			System.exit(1);
		line = line + "\n";
                dataOutputStream.write(line.getBytes());
            }
            catch(Exception e) { e.printStackTrace(); }
        }
    }
}
