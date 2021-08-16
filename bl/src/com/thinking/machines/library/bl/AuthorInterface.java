package com.thinking.machines.library.bl;
public interface AuthorInterface extends java.io.Serializable,Comparable<AuthorInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
}