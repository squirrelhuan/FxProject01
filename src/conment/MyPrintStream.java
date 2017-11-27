package conment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import util.MySystem;

public class MyPrintStream extends PrintStream{

	public MyPrintStream(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	

	public MyPrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		// TODO Auto-generated constructor stub
	}



	public MyPrintStream(OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
		// TODO Auto-generated constructor stub
	}



	public MyPrintStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		// TODO Auto-generated constructor stub
	}



	public MyPrintStream(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}



	public MyPrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		// TODO Auto-generated constructor stub
	}



	public MyPrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void print(boolean b) {
		// TODO Auto-generated method stub
		super.print(b);
		MySystem.out.println(b);
	}

	@Override
	public void print(char c) {
		// TODO Auto-generated method stub
		super.print(c);
		MySystem.out.println(c);
	}

	@Override
	public void print(char[] s) {
		// TODO Auto-generated method stub
		super.print(s);
		MySystem.out.println(s);
	}

	@Override
	public void print(double d) {
		// TODO Auto-generated method stub
		super.print(d);
		MySystem.out.println(d);
	}

	@Override
	public void print(float f) {
		// TODO Auto-generated method stub
		super.print(f);
		MySystem.out.println(f);
	}

	@Override
	public void print(int i) {
		// TODO Auto-generated method stub
		super.print(i);
		MySystem.out.println(i);
	}

	@Override
	public void print(long l) {
		// TODO Auto-generated method stub
		super.print(l);
		MySystem.out.println(l);
	}

	@Override
	public void print(Object obj) {
		// TODO Auto-generated method stub
		super.print(obj);
		MySystem.out.println(obj);
	}

	@Override
	public void print(String s) {
		// TODO Auto-generated method stub
		super.print(s);
		MySystem.out.println(s);
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		// TODO Auto-generated method stub
		return super.printf(l, format, args);
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		// TODO Auto-generated method stub
		return super.printf(format, args);
	}

	@Override
	public void println() {
		// TODO Auto-generated method stub
		super.println();
	}

	@Override
	public void println(boolean x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(char x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(char[] x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(double x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(float x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(int x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(long x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(Object x) {
		// TODO Auto-generated method stub
		super.println(x);
		MySystem.out.println(x);
	}

	@Override
	public void println(String x) {
		// TODO Auto-generated method stub
		super.println(x);
		//开启会重复消息
		//MySystem.out.println(x);
	}

}
