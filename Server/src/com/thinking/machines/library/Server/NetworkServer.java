package com.thinking.machines.library.server;
import java.io.*;
import java.net.*;
public class NetworkServer 
{
public static void main(String gg[])
{
int portNumber=5000; // if possible load it from server_side.cfg
try
{
ServerSocket sk=new ServerSocket(portNumber);
Socket ck;
System.out.print("Server is ready and listening on port "+portNumber);
while(true)
{
ck=sk.accept();
InputStream is=ck.getInputStream();
InputStreamReader isr=new InputStreamReader(is);
StringBuffer sb=new StringBuffer();
int x;
while(true)
{
x=isr.read();
if(x=='~') break;
sb.append((char)x);
}
String request=sb.toString();
String response=ApplicationProcessor.process(request);
response=response+"~";
OutputStream os=ck.getOutputStream();
OutputStreamWriter osw=new OutputStreamWriter(os);
osw.write(response);
osw.flush(); // VVVVVVVVVVVVVVVVVVVVVV important
ck.close();
System.out.println("Response sent : "+response);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}