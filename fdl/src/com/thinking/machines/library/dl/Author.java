package com.thinking.machines.library.dl;
public class Author implements AuthorInterface
{
private int code;
public String name;
public Author()
{
this.code=0;
this.name=" ";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public boolean equals(Object object)
{
if(!(object instanceof Author)) return false;
Author otherAuthor=(Author)object;
return this.code==otherAuthor.code;
}
public int compareTo(AuthorInterface otherAuthor)
{
return this.code=otherAuthor.getCode();
}
public int hashCode()
{
return this.code;
}
}