package com.thinking.machines.library.bl;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import com.thinking.machines.library.dl.*;
public class AuthorModel extends AbstractTableModel
{
private ArrayList<AuthorInterface> authors;
private String[]title={"S.NO.","Author"};
public AuthorModel()
{
authors=new ArrayList<AuthorInterface>();
com.thinking.machines.library.dl.AuthorDAOInterface authorDAO=new com.thinking.machines.library.dl.AuthorDAO();
try
{
LinkedList<com.thinking.machines.library.dl.AuthorInterface>dlAuthors;
dlAuthors=authorDAO.getAll();
AuthorInterface author;
for(com.thinking.machines.library.dl.AuthorInterface dlAuthor:dlAuthors)
{
author=new Author();
author.setCode(dlAuthor.getCode());
author.setName(dlAuthor.getName());
authors.add(author);
}
}catch(DAOException daoException)
{
//Do nothing
}				
}
public int getColumnCount()
{
return this.title.length;
}
public String getColumnName(int i)
{
return this.title[i];
}
public int getRowCount()
{
return authors.size();
}
public boolean isCellEditable(int r,int c)
{
return false;
}
public Object getValueAt(int r,int c)
{
if(c==0)return r+1;
return authors.get(r).getName();
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
if(columnIndex==0)return Integer.class;
return String.class;
}
public void add(AuthorInterface authorInterface)throws BLException
{
BLException exception;
if(authorInterface==null)
{
exception=new BLException();
exception.addException("author","Author required");
throw exception;
}
if(authorInterface.getName()==null||authorInterface.getName().trim().length()==0)
{
exception=new BLException();
exception.addException("name","Name required");
throw exception;
}
for(AuthorInterface vAuthor:authors)
{
if(vAuthor.getName().equalsIgnoreCase(authorInterface.getName()))
{
exception=new BLException();
exception.addException("name","Name "+authorInterface.getName()+" exists.");
throw exception;
}
}
try
{
com.thinking.machines.library.dl.AuthorInterface dlAuthor;
dlAuthor=new com.thinking.machines.library.dl.Author();
dlAuthor.setName(authorInterface.getName().trim());
new com.thinking.machines.library.dl.AuthorDAO().add(dlAuthor);
authorInterface.setCode(dlAuthor.getCode());
int weight;
int index=0;
for(AuthorInterface vAuthorInterface:authors)
{
weight=vAuthorInterface.getName().toUpperCase().compareTo(authorInterface.getName().toUpperCase());
if(weight>0)
{
break;
}
index++;
}
authors.add(index,authorInterface);
fireTableDataChanged();
}catch(com.thinking.machines.library.dl.DAOException daoException)
{
throw new BLException(daoException.getMessage());
}
}
public void update(AuthorInterface authorInterface)throws BLException
{
boolean foundCode=false;
int authorIndex=0;
for(AuthorInterface vAuthor:authors)
{
if(vAuthor.getCode()==authorInterface.getCode())
{
foundCode=true;
break;
}
authorIndex++;
}
if(foundCode==false)
{
throw new BLException("Invalid author code :"+authorInterface.getCode());
}
boolean foundName=false;
for(AuthorInterface vAuthor:authors)
{
if(vAuthor.getCode()!=authorInterface.getCode() && vAuthor.getName().equalsIgnoreCase(authorInterface.getName()))
{
foundName=true;
break;
}
}
if(foundName)
{
throw new BLException("Author : "+authorInterface.getName()+"exists.");
}
com.thinking.machines.library.dl.AuthorInterface dlAuthorInterface;
dlAuthorInterface=new com.thinking.machines.library.dl.Author();
dlAuthorInterface.setCode(authorInterface.getCode());
dlAuthorInterface.setName(authorInterface.getName());
try
{
new com.thinking.machines.library.dl.AuthorDAO().update(dlAuthorInterface);
authors.remove(authorIndex);
int e=0;
while(e<authors.size())
{
if(authors.get(e).getName().toUpperCase().compareTo(authorInterface.getName().toUpperCase())>0)
{
break;
}
e++;
}
authors.add(e,authorInterface);
fireTableDataChanged();
}catch(com.thinking.machines.library.dl.DAOException daoException)
{
throw new BLException(daoException.getMessage());
}
}
public void remove(int code) throws BLException
{
boolean foundCode=false;
int authorIndex=0;
for(AuthorInterface vAuthor:authors)
{
if(vAuthor.getCode()==code)
{
foundCode=true;
break;
}
authorIndex++;
}
if(foundCode==false)
{
throw new BLException("Invalid author code : "+code);
}
try
{
new com.thinking.machines.library.dl.AuthorDAO().remove(code);
authors.remove(authorIndex);
fireTableDataChanged();
}catch(com.thinking.machines.library.dl.DAOException daoException)
{
throw new BLException(daoException.getMessage());
}
}
public int getIndexOf(AuthorInterface authorInterface) throws BLException
{
boolean foundCode=false;
int authorIndex=0;
for(AuthorInterface vAuthor:authors)
{
if(vAuthor.getCode()==authorInterface.getCode())
{
foundCode=true;
break;
}
authorIndex++;
}
if(foundCode==false)
{
throw new BLException("Invalid author code : "+authorInterface.getCode());
}
return authorIndex;
}
public AuthorInterface getAuthorAt(int index) throws BLException
{
if(index<0 || index>=authors.size()) throw new BLException("Index out of bounds : "+index);
return authors.get(index);
}
public AuthorInterface getAuthorByName(String name,boolean compareLeft,boolean compareInCaseSensitive) throws BLException
{
if(compareLeft && compareInCaseSensitive)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.toUpperCase().startsWith(name.toUpperCase())) return authorInterface;
}
}

if(compareLeft && compareInCaseSensitive==false)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.startsWith(name)) return authorInterface;
}
}

if(compareLeft==false && compareInCaseSensitive)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.equalsIgnoreCase(name)) return authorInterface;
}
}
if(compareLeft==false && compareInCaseSensitive==false)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.equals(name)) return authorInterface;
}
}
throw new BLException("Invalid name : "+name);
}
public int getAuthorIndexByName(String name,boolean compareLeft,boolean compareInCaseSensitive) throws BLException
{
if(compareLeft && compareInCaseSensitive)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.toUpperCase().startsWith(name.toUpperCase())) return i;
}
}

if(compareLeft && compareInCaseSensitive==false)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.startsWith(name)) return i;
}
}

if(compareLeft==false && compareInCaseSensitive)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.equalsIgnoreCase(name)) return i;
}
}
if(compareLeft==false && compareInCaseSensitive==false)
{
AuthorInterface authorInterface;
String vName;
for(int i=0;i<authors.size();i++)
{
authorInterface=authors.get(i);
vName=authorInterface.getName();
if(vName.equals(name)) return i;
}
}
throw new BLException("Not yet implemented");
}
}