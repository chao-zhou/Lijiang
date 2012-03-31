package com.utopia.lijiang.contacts;


public interface ContactReader {
	public Boolean canRead();
	public Boolean hasNext();
	public Boolean moveToNext();
	public void close();
	public Contact getContact();	
}
