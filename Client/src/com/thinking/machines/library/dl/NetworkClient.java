package com.thinking.machines.library.dl;
import java.net.*;
import java.io.*;
class NetworkClient
{
private NetworkClient(){}
public static String sendRequest(String server,int portNumber,String request) throws ServerException
{
try
{
Socket socket=new Socket(server,portNumber);
OutputStream os=socket.getOutputStream();
OutputStreamWriter osw=new OutputStreamWriter(os);
osw.write(request);
osw.flush();//VVVVVVVVVVVV important
InputStream is=socket.getInputStream();
InputStreamReader isr=new InputStreamReader(is);
StringBuffer sb=new StringBuffer();
int x;
while(true)
{
x=isr.read();
if(x=='~')break;
sb.append((char)x);
}
String response=sb.toString();
socket.close();
return response;
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
throw new ServerException(ioException.getMessage());
}
}
}