package com.thinking.machines.library.dl;
import java.util.*;
import java.io.*;
public class AuthorDAO implements AuthorDAOInterface
{
private final String dataFileName="author.data";
private final String tmpDataFileName="tmp.data";
public void add(AuthorInterface author)throws DAOException
{
try
{
File file;
file=new File(dataFileName);
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
if(file.exists()==false)
{
throw new DAOException("Invalid code");
}
randomaccessfile=new RandomAccessFile(file,"rw");
String Name;
int Code=0;
String vName;
int vCode;
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(vCode>Code)Code=vCode;
if(vName.equalsIgnoreCase(author.getName()))
{
randomaccessfile.close();
throw new DAOException("name exists:"+author.getName());
}
Code++;
}
author.setCode(Code);
randomaccessfile.writeBytes(String.valueOf(Code));
randomaccessfile.writeBytes("\n");
randomaccessfile.writeBytes(author.getName());
randomaccessfile.writeBytes("\n");
randomaccessfile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(AuthorInterface author)throws DAOException
{
try
{
File file=new File(dataFileName);
if(file.exists()==false)
{
throw new DAOException("Invalid author code:" +author.getCode());
}
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
randomaccessfile.seek(0);
if(randomaccessfile.length()==0)
{
randomaccessfile.close();
throw new DAOException("Invalid author code:" +author.getCode());
}
int vCode;
String Name;
String vName;
boolean found=false;
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(vName.equalsIgnoreCase(author.getName()) && vCode!=author.getCode())
{
randomaccessfile.close();
throw new DAOException("Author :"+author.getName()+"exists");
}
if(!found && vCode==author.getCode())
{
found=true;
}
}
if(found==false)
{
randomaccessfile.close();
throw new DAOException("Invalid author code:" +author.getCode());
}
randomaccessfile.seek(0);
File tmpFile=new File(tmpDataFileName);
if(tmpFile.exists())
{
tmpFile.delete();
}
RandomAccessFile tmprandomaccessfile=new RandomAccessFile(tmpFile,"rw");
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(vCode==author.getCode())
{
tmprandomaccessfile.writeBytes(author.getCode()+"\n"+author.getName()+"\n");
}
else
{
tmprandomaccessfile.writeBytes(vCode+"\n"+vName+"\n");
}
}
randomaccessfile.seek(0);
tmprandomaccessfile.seek(0);
while(tmprandomaccessfile.getFilePointer()<tmprandomaccessfile.length())
{
randomaccessfile.writeBytes(tmprandomaccessfile.readLine()+"\n");
}
randomaccessfile.setLength(tmprandomaccessfile.length());
tmprandomaccessfile.setLength(0);
randomaccessfile.close();
tmprandomaccessfile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void remove(int code)throws DAOException
{
try
{
File file;
file=new File(dataFileName);
if(file.exists()==false)
{
throw new DAOException("Invalid author code:"+code);
}
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
if(randomaccessfile.length()==0)
{
randomaccessfile.close();
throw new DAOException("Invalid code:"+code);
}
int vCode;
String vName;
boolean found=false;
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(vCode==code)
{
found=true;
break;
}
}
if(found==false)
{
randomaccessfile.close();
throw new DAOException("invalid author code: "+code);
}
randomaccessfile.seek(0);
File tmpFile=new File(tmpDataFileName);
if(tmpFile.exists())
{
tmpFile.delete();
}
RandomAccessFile tmprandomaccessfile=new RandomAccessFile(tmpFile,"rw");
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(code!=vCode)
{
tmprandomaccessfile.writeBytes(vCode+"\n"+vName+"\n");
}
}
randomaccessfile.seek(0);
tmprandomaccessfile.seek(0);
while(tmprandomaccessfile.getFilePointer()<tmprandomaccessfile.length())
{
randomaccessfile.writeBytes(tmprandomaccessfile.readLine()+"\n");
}
randomaccessfile.setLength(tmprandomaccessfile.length());
tmprandomaccessfile.setLength(0);
randomaccessfile.close();
tmprandomaccessfile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public AuthorInterface getByCode(int code)throws DAOException
{
try
{
File file;
file=new File(dataFileName);
if(file.exists()==false)
{
throw new DAOException("Invalid author code");
}
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
if(randomaccessfile.length()==0)
{
randomaccessfile.close();
throw new DAOException("Invalid code");
}
int vCode;
String vName;
AuthorInterface author;
author=new Author();
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(vCode==code)
{
randomaccessfile.close();
System.out.println("author is:" +vName);
return author;
}
}
randomaccessfile.close();
throw new DAOException("Invalid Code");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public AuthorInterface getByName(String name)throws DAOException
{
try
{
File file;
file=new File(dataFileName);
if(file.exists()==false)
{
throw new DAOException("Invalid author code");
}
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
if(randomaccessfile.length()==0)
{
randomaccessfile.close();
throw new DAOException("Invalid code");
}
int vCode;
String vName;
AuthorInterface author;
author=new Author();
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
vCode=Integer.parseInt(randomaccessfile.readLine());
vName=randomaccessfile.readLine();
if(vName.equalsIgnoreCase(name))
{
randomaccessfile.close();
System.out.println("author code is:" +vCode);
return author;
}
}
randomaccessfile.close();
throw new DAOException("Invalid Code");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public LinkedList<AuthorInterface>getAll()throws DAOException
{
try
{
File file;
file=new File(dataFileName);
if(file.exists()==false)
{
throw new DAOException("Invalid Code");
}
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
if(randomaccessfile.length()==0)
{
randomaccessfile.close();
throw new DAOException("Invalid Code");
}
int vCode;
String vName;
Author author;
LinkedList<AuthorInterface>authors;
authors=new LinkedList<AuthorInterface>();
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
author=new Author();
author.setCode(Integer.parseInt(randomaccessfile.readLine()));
author.setName(randomaccessfile.readLine());
authors.add(author);
}
randomaccessfile.close();
Collections.sort(authors);
return authors;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public long getCount()throws DAOException 
{
try
{
File file=new File(dataFileName);
if(file.exists()==false)
{
throw new DAOException("NO Records");
}
RandomAccessFile randomaccessfile;
randomaccessfile=new RandomAccessFile(file,"rw");
if(randomaccessfile.length()==0)
{
randomaccessfile.close();
throw new DAOException("NO RECORDS");
}
long count=0;
while(randomaccessfile.getFilePointer()<randomaccessfile.length())
{
randomaccessfile.readLine();
randomaccessfile.readLine();
count++;
}
randomaccessfile.close();
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}


