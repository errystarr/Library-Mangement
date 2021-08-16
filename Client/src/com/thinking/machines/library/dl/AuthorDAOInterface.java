package com.thinking.machines.library.dl;
import java.util.*;
public interface AuthorDAOInterface
{
public void add(AuthorInterface author)throws DAOException;
public void update(AuthorInterface author)throws DAOException;
public void remove(int code)throws DAOException;
public AuthorInterface getByCode(int code)throws DAOException;
public AuthorInterface getByName(String name)throws DAOException;
public LinkedList<AuthorInterface>getAll() throws DAOException;
public long getCount() throws DAOException;
}