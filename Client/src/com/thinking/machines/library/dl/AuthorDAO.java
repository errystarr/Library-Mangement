package com.thinking.machines.library.dl;
import java.util.*;
public class AuthorDAO implements AuthorDAOInterface
{
private String server="localhost";
private int portNumber=5000;
public void add(AuthorInterface authorInterface)throws DAOException
{
try
{
String request="AUTHOR:ADD:"+authorInterface.getName()+"~";
String response=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=response.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(success)
{
authorInterface.setCode(Integer.parseInt(splits[1]));
}
else
{
throw new DAOException(splits[1]);
}
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,cotact administrator");
}
}
public void update(AuthorInterface authorInterface)throws DAOException
{
try
{
String request="AUTHOR:UPDATE:"+authorInterface.getCode()+":"+authorInterface.getName()+"~";
String response=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=response.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(!success)
{
throw new DAOException(splits[1]);
}
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,contact administrator");
}
}
public void remove(int code) throws DAOException
{
try
{
String request="AUTHOR:DELETE:"+code+"~";
String response=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=response.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(!success)
{
throw new DAOException(splits[1]);
}
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,contact administrator");
}
}
public LinkedList<AuthorInterface>getAll() throws DAOException
{
try
{
String request="AUTHOR:GET_ALL~";
String response=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=response.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(success)
{
LinkedList<AuthorInterface>authors=new LinkedList<>();
int i=1;
AuthorInterface authorInterface;
while(i<splits.length)
{
authorInterface=new Author();
authorInterface.setCode(Integer.parseInt(splits[i]));
authorInterface.setName(splits[i+1]);
authors.add(authorInterface);
i=i+2;
}
return authors;
}
throw new DAOException(splits[1]);
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,contact administrator");
}
}
public AuthorInterface getByCode(int code) throws DAOException
{
try
{
String request="AUTHOR:GET_BY_CODE:"+code+"~";
String response=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=response.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(success)
{
AuthorInterface authorInterface=new Author();
authorInterface.setCode(Integer.parseInt(splits[1]));
authorInterface.setName(splits[2]);
return authorInterface;
}
throw new DAOException(splits[1]);
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,contact administrator");
}
}
public AuthorInterface getByName(String name) throws DAOException
{
try
{
String request="AUTHOR:GET_BY_NAME:"+name+"~";
String response=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=response.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(success)
{
AuthorInterface authorInterface=new Author();
authorInterface.setCode(Integer.parseInt(splits[1]));
authorInterface.setName(splits[2]);
return authorInterface;
}
throw new DAOException(splits[1]);
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,contact administrator");
}
}
public long getCount() throws DAOException
{
try
{
String request="AUTHOR:GET_COUNT ";
String reponse=NetworkClient.sendRequest(server,portNumber,request);
String splits[]=reponse.split(":");
boolean success=Boolean.parseBoolean(splits[0]);
if(success)
{
return Long.parseLong(splits[1]);
}
throw new DAOException(splits[1]);
}catch(ServerException serverException)
{
System.out.println(serverException);
throw new DAOException("Server not responding or not ready,contact administrator");
}
}
}
