package com.thinking.machines.library.dl;
public interface AuthorInterface extends java.io.Serializable,Comparable<AuthorInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
}