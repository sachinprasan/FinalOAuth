package com.full.oauth;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
@PersistenceCapable
public class Login
{
	@Persistent
	protected String Line;
	public String getLine() 
	{
		return Line;
	}

	public void setLine(String Line) 
	{
		this.Line = Line;
	}
}
