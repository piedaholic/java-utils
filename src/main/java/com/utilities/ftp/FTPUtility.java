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

// TODO: Auto-generated Javadoc
/** The Class FTPUtility. */
public class FTPUtility {

  /** The print debug info. */
  private static boolean PRINT_DEBUG_INFO = true;

  /** The connection socket. */
  private Socket connectionSocket = null;

  /** The output stream. */
  private PrintStream outputStream = null;

  /** The input stream. */
  private BufferedReader inputStream = null;

  /** The restart point. */
  private long restartPoint = 0L;

  /** The logged in. */
  private boolean loggedIn = true;

  /** The line term. */
  public String lineTerm = "\n";

  /** The block size. */
  private static int BLOCK_SIZE = 4096;

  /** Instantiates a new FTP utility. */
  public FTPUtility() {}

  /**
   * Instantiates a new FTP utility.
   *
   * @param debugOut the debug out
   */
  public FTPUtility(boolean debugOut) {
    PRINT_DEBUG_INFO = debugOut;
  }

  /**
   * Debug print.
   *
   * @param message the message
   */
  private void debugPrint(String message) {
    if (PRINT_DEBUG_INFO) {
      System.err.println(message);
    }
  }

  /**
   * Connect.
   *
   * @param host the host
   * @return true, if successful
   * @throws UnknownHostException the unknown host exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public boolean connect(String host) throws UnknownHostException, IOException {
    return this.connect(host, 21);
  }

  /**
   * Connect.
   *
   * @param host the host
   * @param port the port
   * @return true, if successful
   * @throws UnknownHostException the unknown host exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public boolean connect(String host, int port) throws UnknownHostException, IOException {
    this.connectionSocket = new Socket(host, port);
    this.outputStream = new PrintStream(this.connectionSocket.getOutputStream());
    this.inputStream =
        new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
    if (!this.isPositiveCompleteResponse(this.getServerReply())) {
      this.disconnect();
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks if is positive complete response.
   *
   * @param response the response
   * @return true, if is positive complete response
   */
  private boolean isPositiveCompleteResponse(int response) {
    return response >= 200 && response < 300;
  }

  /** Disconnect. */
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

  /**
   * Login.
   *
   * @param username the username
   * @param pwd the pwd
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Logout.
   *
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public boolean logout() throws IOException {
    int response = this.executeCommand("quit");
    this.loggedIn = !this.isPositiveCompleteResponse(response);
    return !this.loggedIn;
  }

  /**
   * Upload file.
   *
   * @param serverPath the server path
   * @param localPath the local path
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public boolean uploadFile(String serverPath, String localPath) throws IOException {
    return this.writeDataFromFile("stor " + serverPath, localPath);
  }

  /**
   * Sets the restart point.
   *
   * @param point the new restart point
   */
  public void setRestartPoint(int point) {
    this.restartPoint = (long) point;
    this.debugPrint("Restart noted");
  }

  /**
   * Gets the server reply.
   *
   * @return the server reply
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private int getServerReply() throws IOException {
    return Integer.parseInt(this.getFullServerReply().substring(0, 3));
  }

  /**
   * Gets the full server reply.
   *
   * @return the full server reply
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String getFullServerReply() throws IOException {
    String reply;
    do {
      reply = this.inputStream.readLine();
      this.debugPrint(reply);
    } while (!Character.isDigit(reply.charAt(0))
        || !Character.isDigit(reply.charAt(1))
        || !Character.isDigit(reply.charAt(2))
        || reply.charAt(3) != ' ');

    return reply;
  }

  /**
   * Gets the full server reply.
   *
   * @param fullReply the full reply
   * @return the full server reply
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public String getFullServerReply(StringBuffer fullReply) throws IOException {
    fullReply.setLength(0);

    String reply;
    do {
      do {
        reply = this.inputStream.readLine();
        this.debugPrint(reply);
        fullReply.append(reply + this.lineTerm);
      } while (!Character.isDigit(reply.charAt(0)));
    } while (!Character.isDigit(reply.charAt(1))
        || !Character.isDigit(reply.charAt(2))
        || reply.charAt(3) != ' ');

    if (fullReply.length() > 0) {
      fullReply.setLength(fullReply.length() - this.lineTerm.length());
    }

    return reply;
  }

  /**
   * Execute command.
   *
   * @param command the command
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public int executeCommand(String command) throws IOException {
    this.outputStream.println(command);
    return this.getServerReply();
  }

  /**
   * Gets the execution response.
   *
   * @param command the command
   * @return the execution response
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public String getExecutionResponse(String command) throws IOException {
    this.outputStream.println(command);
    return this.getFullServerReply();
  }

  /**
   * Write data from file.
   *
   * @param command the command
   * @param fileName the file name
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Execute data command.
   *
   * @param command the command
   * @param out the out
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Execute data command.
   *
   * @param command the command
   * @param in the in
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Execute data command.
   *
   * @param command the command
   * @param sb the sb
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Transfer data.
   *
   * @param in the in
   * @param out the out
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void transferData(InputStream in, OutputStream out) throws IOException {
    byte[] b = new byte[BLOCK_SIZE];

    int amount;
    while ((amount = in.read(b)) > 0) {
      out.write(b, 0, amount);
    }
  }

  /**
   * Transfer data.
   *
   * @param in the in
   * @param sb the sb
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void transferData(InputStream in, StringBuffer sb) throws IOException {
    byte[] b = new byte[BLOCK_SIZE];

    int amount;
    while ((amount = in.read(b)) > 0) {
      sb.append(new String(b, 0, amount));
    }
  }

  /**
   * Setup data port.
   *
   * @param command the command
   * @param serverSocket the server socket
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Open port.
   *
   * @param serverSocket the server socket
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

    this.outputStream.println(
        "port "
            + addrshorts[0]
            + ","
            + addrshorts[1]
            + ","
            + addrshorts[2]
            + ","
            + addrshorts[3]
            + ","
            + ((localport & '\uff00') >> 8)
            + ","
            + (localport & 255));
    return this.isPositiveCompleteResponse(this.getServerReply());
  }

  /**
   * Checks if is positive preliminary response.
   *
   * @param response the response
   * @return true, if is positive preliminary response
   */
  private boolean isPositivePreliminaryResponse(int response) {
    return response >= 100 && response < 200;
  }

  /**
   * Checks if is positive intermediate response.
   *
   * @param response the response
   * @return true, if is positive intermediate response
   */
  private boolean isPositiveIntermediateResponse(int response) {
    return response >= 300 && response < 400;
  }

  /**
   * Download file.
   *
   * @param serverPath the server path
   * @param localPath the local path
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public boolean downloadFile(String serverPath, String localPath) throws IOException {
    return this.readDataToFile("retr " + serverPath, localPath);
  }

  /**
   * Read data to file.
   *
   * @param command the command
   * @param fileName the file name
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
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
