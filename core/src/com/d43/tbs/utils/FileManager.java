package com.d43.tbs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.d43.tbs.model.unit.Unit;

public class FileManager {
	
	public FileManager() {
		
	}
	
	public void deleteFile(String name) {
		File file = new File("saves//" + name);
		file.delete();
	}
	
	public ArrayList<String> getSaves() {
		ArrayList<String> names = new ArrayList<String>();
		
		File folder = new File("saves//");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile())
			  names.add(listOfFiles[i].getName());
//		  {
//		    System.out.println("File " + listOfFiles[i].getName());
//		  } else if (listOfFiles[i].isDirectory()) {
//		    System.out.println("Directory " + listOfFiles[i].getName());
//		  }
		}
		
		Collections.sort(names, new Comparator<String>() {
	        @Override
	        public int compare(String name1, String name2)
	        {
//	            return  name1.compareTo(name2);
	            int n1 = Integer.valueOf(name1.split("\\.")[0]);
	            int n2 = Integer.valueOf(name2.split("\\.")[0]);
	            return n1 > n2 ? 1 : n1 < n2 ? -1 : 0;
	        }
	    });
		
		return names;
	}
	
	public ArrayList<Unit> read(String name) {
		try {
			ArrayList<Unit> units = new ArrayList<Unit>();
			FileInputStream fis = new FileInputStream("saves//" + name);
			ObjectInputStream oin = new ObjectInputStream(fis);
			units = (ArrayList<Unit>)oin.readObject();
			oin.close();
			return units;
		} catch(Exception ex) {
			Gdx.app.log("file read", ex.toString());
			return null;
		}
	}
	
	public void save(ArrayList<Unit> units) {
		try {
			increaseSaveCount();
			String name = Integer.toString(getSaveCount());
			FileOutputStream fos = new FileOutputStream("saves//" + name + ".out");
			ObjectOutputStream os = new ObjectOutputStream(fos);
		  	os.writeObject(units);
			os.flush();
			os.close();
		} catch(Exception ex) {
			Gdx.app.log("file read", ex.toString());
			
			return;
		}
	}
	
	private int getSaveCount() {
		try {
			FileInputStream fis = new FileInputStream("saveCount.out");
			ObjectInputStream oin = new ObjectInputStream(fis);
			int count = (Integer)oin.readObject();
			oin.close();
			return count;
		} catch(Exception ex) {
			Gdx.app.log("file read", ex.toString());
			return 0;
		}
	}
	
	private void increaseSaveCount() {
		if(getSaveCount() >= 53)
			setToZeroSaveCount();
		try {
			int count = getSaveCount() + 1;
			FileOutputStream fos = new FileOutputStream("saveCount.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		  	oos.writeObject(count);
			oos.flush();
			oos.close();
		} catch(Exception ex) {
			Gdx.app.log("file write", ex.toString());
			ex.printStackTrace();
			return;
		}
	}
	
	private void setToZeroSaveCount() {
		try {
			int count = 0;
			FileOutputStream fos = new FileOutputStream("saveCount.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		  	oos.writeObject(count);
			oos.flush();
			oos.close();
		} catch(Exception ex) {
			Gdx.app.log("file write", ex.toString());
			ex.printStackTrace();
			return;
		}
	}
}
