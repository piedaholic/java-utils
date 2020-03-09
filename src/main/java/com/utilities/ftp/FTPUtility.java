package com.utilities.ftp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FTPUtility {
    private static boolean PRINT_DEBUG_INFO = true;
    private Socket connectionSocket = null;
    private PrintStream outputStream = null;
    private BufferedReader inputStream = null;
    private long restartPoint = 0L;
    private boolean loggedIn = true;
    public String lineTerm = "\n";
    private static int BLOCK_SIZE = 4096;

    public FTPUtility() {
    }

    public FTPUtility(boolean debugOut) {
	PRINT_DEBUG_INFO = debugOut;
    }

    private void debugPrint(String message) {
	if (PRINT_DEBUG_INFO) {
	    System.err.println(message);
	}

    }

    public boolean connect(String host) throws UnknownHostException, IOException {
	return this.connect(host, 21);
    }

    public boolean connect(String host, int port) throws UnknownHostException, IOException {
	this.connectionSocket = new Socket(host, port);
	this.outputStream = new PrintStream(this.connectionSocket.getOutputStream());
	this.inputStream = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
	if (!this.isPositiveCompleteResponse(this.getServerReply())) {
	    this.disconnect();
	    return false;
	} else {
	    return true;
	}
    }

    private boolean isPositiveCompleteResponse(int response) {
	return response >= 200 && response < 300;
    }

    public void disconnect() {
	if (this.outputStream != null) {
	    try {
		if (this.loggedIn) {
		    this.logout();
		}

		this.outputStream.close();
		this.inputStream.close();
		this.connectionSocket.close();
	    } catch (IOException var2) {
	    }

	    this.outputStream = null;
	    this.inputStream = null;
	    this.connectionSocket = null;
	}

    }

    public boolean login(String username, String pwd) throws IOException {
	int response = this.executeCommand("user " + username);
	if (!this.isPositiveIntermediateResponse(response)) {
	    return false;
	} else {
	    response = this.executeCommand("pass " + pwd);
	    this.loggedIn = this.isPositiveCompleteResponse(response);
	    return this.loggedIn;
	}
    }

    public boolean logout() throws IOException {
	int response = this.executeCommand("quit");
	this.loggedIn = !this.isPositiveCompleteResponse(response);
	return !this.loggedIn;
    }

    public boolean uploadFile(String serverPath, String localPath) throws IOException {
	return this.writeDataFromFile("stor " + serverPath, localPath);
    }

    public void setRestartPoint(int point) {
	this.restartPoint = (long) point;
	this.debugPrint("Restart noted");
    }

    private int getServerReply() throws IOException {
	return Integer.parseInt(this.getFullServerReply().substring(0, 3));
    }

    private String getFullServerReply() throws IOException {
	String reply;
	do {
	    reply = this.inputStream.readLine();
	    this.debugPrint(reply);
	} while (!Character.isDigit(reply.charAt(0)) || !Character.isDigit(reply.charAt(1))
		|| !Character.isDigit(reply.charAt(2)) || reply.charAt(3) != ' ');

	return reply;
    }

    public String getFullServerReply(StringBuffer fullReply) throws IOException {
	fullReply.setLength(0);

	String reply;
	do {
	    do {
		reply = this.inputStream.readLine();
		this.debugPrint(reply);
		fullReply.append(reply + this.lineTerm);
	    } while (!Character.isDigit(reply.charAt(0)));
	} while (!Character.isDigit(reply.charAt(1)) || !Character.isDigit(reply.charAt(2)) || reply.charAt(3) != ' ');

	if (fullReply.length() > 0) {
	    fullReply.setLength(fullReply.length() - this.lineTerm.length());
	}

	return reply;
    }

    public int executeCommand(String command) throws IOException {
	this.outputStream.println(command);
	return this.getServerReply();
    }

    public String getExecutionResponse(String command) throws IOException {
	this.outputStream.println(command);
	return this.getFullServerReply();
    }

    public boolean writeDataFromFile(String command, String fileName) throws IOException {
	RandomAccessFile infile = null;
	FileInputStream fileStream = null;
	boolean success = false;

	try {
	    infile = new RandomAccessFile(fileName, "r");
	    if (this.restartPoint != 0L) {
		this.debugPrint("Seeking to " + this.restartPoint);
		infile.seek(this.restartPoint);
	    }

	    fileStream = new FileInputStream(infile.getFD());
	    success = this.executeDataCommand(command, (InputStream) fileStream);
	} catch (Exception var19) {
	} finally {
	    try {
		if (infile != null) {
		    infile.close();
		}
	    } catch (IOException var18) {
	    }

	    try {
		if (fileStream != null) {
		    fileStream.close();
		}
	    } catch (IOException var17) {
	    }

	}

	return success;
    }

    public boolean executeDataCommand(String command, OutputStream out) throws IOException {
	ServerSocket serverSocket = new ServerSocket(0);
	if (!this.setupDataPort(command, serverSocket)) {
	    return false;
	} else {
	    Socket clientSocket = serverSocket.accept();
	    InputStream in = clientSocket.getInputStream();
	    this.transferData(in, out);
	    in.close();
	    clientSocket.close();
	    serverSocket.close();
	    return this.isPositiveCompleteResponse(this.getServerReply());
	}
    }

    public boolean executeDataCommand(String command, InputStream in) throws IOException {
	ServerSocket serverSocket = new ServerSocket(0);
	if (!this.setupDataPort(command, serverSocket)) {
	    return false;
	} else {
	    Socket clientSocket = serverSocket.accept();
	    OutputStream out = clientSocket.getOutputStream();
	    this.transferData(in, out);
	    out.close();
	    clientSocket.close();
	    serverSocket.close();
	    return this.isPositiveCompleteResponse(this.getServerReply());
	}
    }

    public boolean executeDataCommand(String command, StringBuffer sb) throws IOException {
	ServerSocket serverSocket = new ServerSocket(0);
	if (!this.setupDataPort(command, serverSocket)) {
	    return false;
	} else {
	    Socket clientSocket = serverSocket.accept();
	    InputStream in = clientSocket.getInputStream();
	    this.transferData(in, sb);
	    in.close();
	    clientSocket.close();
	    serverSocket.close();
	    return this.isPositiveCompleteResponse(this.getServerReply());
	}
    }

    private void transferData(InputStream in, OutputStream out) throws IOException {
	byte[] b = new byte[BLOCK_SIZE];

	int amount;
	while ((amount = in.read(b)) > 0) {
	    out.write(b, 0, amount);
	}

    }

    private void transferData(InputStream in, StringBuffer sb) throws IOException {
	byte[] b = new byte[BLOCK_SIZE];

	int amount;
	while ((amount = in.read(b)) > 0) {
	    sb.append(new String(b, 0, amount));
	}

    }

    private boolean setupDataPort(String command, ServerSocket serverSocket) throws IOException {
	if (!this.openPort(serverSocket)) {
	    return false;
	} else {
	    this.outputStream.println("type i");
	    if (!this.isPositiveCompleteResponse(this.getServerReply())) {
		this.debugPrint("Could not set transfer type");
		return false;
	    } else {
		if (this.restartPoint != 0L) {
		    this.outputStream.println("rest " + this.restartPoint);
		    this.restartPoint = 0L;
		    this.getServerReply();
		}

		this.outputStream.println(command);
		return this.isPositivePreliminaryResponse(this.getServerReply());
	    }
	}
    }

    private boolean openPort(ServerSocket serverSocket) throws IOException {
	int localport = serverSocket.getLocalPort();

	InetAddress localip;
	try {
	    localip = InetAddress.getLocalHost();
	} catch (UnknownHostException var7) {
	    this.debugPrint("Can't get local host");
	    return false;
	}

	byte[] addrbytes = localip.getAddress();
	short[] addrshorts = new short[4];

	for (int i = 0; i <= 3; ++i) {
	    addrshorts[i] = (short) addrbytes[i];
	    if (addrshorts[i] < 0) {
		addrshorts[i] = (short) (addrshorts[i] + 256);
	    }
	}

	this.outputStream.println("port " + addrshorts[0] + "," + addrshorts[1] + "," + addrshorts[2] + ","
		+ addrshorts[3] + "," + ((localport & '\uff00') >> 8) + "," + (localport & 255));
	return this.isPositiveCompleteResponse(this.getServerReply());
    }

    private boolean isPositivePreliminaryResponse(int response) {
	return response >= 100 && response < 200;
    }

    private boolean isPositiveIntermediateResponse(int response) {
	return response >= 300 && response < 400;
    }

    public boolean downloadFile(String serverPath, String localPath) throws IOException {
	return this.readDataToFile("retr " + serverPath, localPath);
    }

    public boolean readDataToFile(String command, String fileName) throws IOException {
	FileOutputStream fileStream = null;
	RandomAccessFile outfile = null;
	boolean success = false;

	try {
	    outfile = new RandomAccessFile(fileName, "rw");
	    if (this.restartPoint != 0L) {
		this.debugPrint("Seeking to " + this.restartPoint);
		outfile.seek(this.restartPoint);
	    }

	    fileStream = new FileOutputStream(outfile.getFD());
	    success = this.executeDataCommand(command, (OutputStream) fileStream);
	} catch (Exception var19) {
	} finally {
	    try {
		if (outfile != null) {
		    outfile.close();
		}
	    } catch (IOException var18) {
	    }

	    try {
		if (fileStream != null) {
		    fileStream.close();
		}
	    } catch (IOException var17) {
	    }

	}

	return success;
    }
}
