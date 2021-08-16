package com.thinking.machines.library.bl;
import java.util.*;
public class BLException extends Exception
{
private HashMap<String,String>exceptions;
public BLException(String message)
{
exceptions=new HashMap<String,String>();
}
public BLException()
{
exceptions=new HashMap<String,String>();
}
public void addException(String key,String value)
{
exceptions.put(key,value);
}
public String getException(String key)
{
return exceptions.get(key);
}
public String toString()
{
return "BLException";
}
public String getMessage()
{
return "BLException";
}
public int size()
{
return exceptions.size();
}
public ArrayList<String> getProperties()
{
ArrayList<String> properties= new ArrayList<String>(exceptions.keySet());
return properties;
}
}

