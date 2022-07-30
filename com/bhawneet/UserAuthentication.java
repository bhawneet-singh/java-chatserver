package com.bhawneet;
import java.net.*;
import java.io.*;

public class UserAuthentication 
{
    private final String US_ASCII = "ASCII";
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    private byte [] buff = new byte[1024];
    private boolean isAuthentic = false;
    private String userName = "";

    public UserAuthentication(Socket socket)
    {
        this.socket = socket;
        this.uAuthenticate();
    }    

    private void uAuthenticate()
    {
        try
        {
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.dataOutputStream.write("enter user name : ".getBytes());
            int count = this.dataInputStream.read( this.buff , 0 , this.buff.length );
            this.userName = new String( this.buff , 0 , count , this.US_ASCII ).trim(); 
            String passward = Server.userData.get(this.userName);
            if(passward == null)
                return;
            this.dataOutputStream.write(("enter passward for @" + this.userName + " : ").getBytes());
            count = this.dataInputStream.read( this.buff , 0 , this.buff.length );
            String uPass = new String( this.buff , 0 , count , this.US_ASCII ).trim();
            if(uPass.equals(passward))
            {
                this.isAuthentic = !this.isAuthentic;
                this.dataOutputStream.write("\033\143".getBytes()); // clear linux console
                this.dataOutputStream.write(("++++++++++ welcome " + this.userName + " ! ++++++++++\n>>> ").getBytes());
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    public boolean isAuthentic()
    {
        if(!this.isAuthentic)
            try{ this.dataOutputStream.write("either username or passward may incarrect !".getBytes()); } catch (IOException e) {}
        return this.isAuthentic;
    }

    public String getUserName()
    {
        return this.userName;
    }
}
