package com.utilities.files;

import java.io.*;
public class ReadingFromFile
{
  public static void main(String[] args) throws Exception
  {
    // pass the path to the file as a parameter
    FileReader fr =
      new FileReader("C:\\Users\\hpsingh\\Desktop\\PCT\\IM181720581,00001,00012,LCDPRMNT,MSDMSTYM,0001,LC_INSTRUMENT,LC,.sql");
 
    int i;
    while ((i=fr.read()) != -1)
      System.out.print((char) i);
    fr.close();
  }
}