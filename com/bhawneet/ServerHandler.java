package com.bhawneet;
import java.net.*;
import java.io.*;

public class ServerHandler implements Runnable
{
    private Socket socket = null;
    private DataOutputStream dataOutputStream = null;
    private DataInputStream dataInputStream = null;
    private byte [] buff = new byte[1024];
    private String userName = "";

    public ServerHandler(Socket socket,String userName)
    {
        this.socket = socket;
        this.userName = userName;
    }

    @Override
    public void run()
    {
        try
        {
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            int count = this.dataInputStream.read( this.buff , 0 , this.buff.length );
            while(count > 1)
            {
                for(Socket client : Server.clientList)
                {
                    try
                    {
                        if(client == this.socket)
                        {
                            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
                            this.dataOutputStream.write(">>> ".getBytes());
                            continue;
                        }
                        this.dataOutputStream = new DataOutputStream(client.getOutputStream());
                        this.dataOutputStream.write(("\n\t@" + this.userName + " : ").getBytes());
                        this.dataOutputStream.write( this.buff , 0 , count );
                        this.dataOutputStream.write(">>>\n>>> ".getBytes());
                    }
                    catch(Exception e) {  }
                }
                count = this.dataInputStream.read(this.buff,0,this.buff.length);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        finally
        {
            Server.clientList.remove(this.socket);
            if(this.socket != null)
                try{ this.socket.close(); } catch (Exception e) { }
        }
    }
}
