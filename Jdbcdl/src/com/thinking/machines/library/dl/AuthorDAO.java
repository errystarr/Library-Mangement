package com.thinking.machines.library.dl;
import java.util.*;
import java.io.*;
import java.sql.*;
public class AuthorDAO implements AuthorDAOInterface
{
private final String dataFileName="author.data";
private final String tmpDataFileName="tmp.data";
public void add(AuthorInterface author)throws DAOException
{
try
{
Connection c;
String name=author.getName();
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
PreparedStatement ps;
ResultSet resultSet;
ps=c.prepareStatement("select 1 as xyz from author where name=?");
ps.setString(1,name);
resultSet=ps.executeQuery();
if(resultSet.next())
{
resultSet.close();
ps.close();
throw new DAOException("Author "+name+" exists ");
}
ps=c.prepareStatement("insert into author(name)values(?)",Statement.RETURN_GENERATED_KEYS);
ps.setString(1,name);
ps.executeUpdate();
resultSet=ps.getGeneratedKeys();
resultSet.next();
author.setCode(resultSet.getInt(1));
resultSet.close();
ps.close();
c.close();
}catch(SQLException sqlexception)
{
throw new DAOException(sqlexception.getMessage());
}
}
public void update(AuthorInterface author)throws DAOException
{
try
{
Connection c;
int code=author.getCode();
String name=author.getName();
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
PreparedStatement ps;
ps=c.prepareStatement("select 1 as xyz from author where code=?");
ps.setInt(1,code);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
c.close();
throw new DAOException("Invalid author code : "+code);
}
rs.close();
ps.close();
ps=c.prepareStatement("select 1 as xyz where name=? and code<> ?");
ps.setString(1,name);
ps.setInt(2,code);
rs=ps.executeQuery();
if(rs.next())
{
rs.close();
ps.close();
c.close();
throw new DAOException("Author: "+name+"exists");
}
rs.close();
ps.close();
ps=c.prepareStatement("update author set name=? where code=?");
ps.setString(1,name);
ps.setInt(2,code);
ps.executeUpdate();
ps.close();
c.close();
}catch(SQLException sqlexception)
{
throw new DAOException(sqlexception.getMessage());
}
}
public void remove(int code)throws DAOException
{
try
{
Connection c;
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
PreparedStatement ps;
ps=c.prepareStatement("select 1 as xyz from author where code=?");
ps.setInt(1,code);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
c.close();
throw new DAOException("Invalid author code:"+code);
} 
rs.close();
ps.close();
ps=c.prepareStatement("delete from author where code=?");
ps.setInt(1,code);
ps.executeUpdate();
ps.close();
c.close();
}catch(SQLException sqlexception)
{
throw new DAOException(sqlexception.getMessage());
}
}
public AuthorInterface getByCode(int code)throws DAOException
{
try
{
Connection c;
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
PreparedStatement ps;
ps=c.prepareStatement("select name from author where code=?");
ps.setInt(1,code);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
c.close();
throw new DAOException("Invalid author code:"+code);
}
AuthorInterface author=new Author();
author.setCode(code);
author.setName(rs.getString("name").trim());
ps.close();
rs.close();
c.close();
return author;
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage());
}
}
public AuthorInterface getByName(String name)throws DAOException
{
try
{
Connection c;
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
PreparedStatement ps;
ps=c.prepareStatement("select code from author where name=?");
ps.setString(1,name);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
rs.close();
ps.close();
c.close();
throw new DAOException("Invalid Author name:"+name);
}
AuthorInterface author=new Author();
author.setName(name);
author.setCode(rs.getInt("code"));
rs.close();
ps.close();
c.close();
return author;
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage());
}
}
public LinkedList<AuthorInterface>getAll()throws DAOException
{
try
{
Connection c;
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
Statement s;
s=c.createStatement();
ResultSet rs=s.executeQuery("select * from author order by name");
AuthorInterface author;
LinkedList<AuthorInterface> authors=new LinkedList<AuthorInterface>();
while(rs.next())
{
author=new Author();
author.setCode(rs.getInt("code"));
author.setName(rs.getString("name").trim());
authors.add(author);
}
rs.close();
s.close();
c.close();
if(authors.size()==0)
{
throw new DAOException("No Authors");
}
return authors;
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage());
}
}
public long getCount()throws DAOException 
{
try
{
Connection c;
c=DriverManager.getConnection("jdbc:derby:/LibraryApp/Javadb/librarydb");
Statement s;
s=c.createStatement();
ResultSet rs=s.executeQuery("select count(*) as cnt from author");
rs.next();
long count=rs.getLong("cnt");
rs.close();
s.close();
c.close();
return count;
}catch(SQLException exception)
{
throw new DAOException(exception.getMessage());
}
}
}


