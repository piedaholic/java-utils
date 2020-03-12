package com.utilities.ftp;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

// TODO: Auto-generated Javadoc
/** The Class FtpClient. */
class FtpClient {

  /** The server. */
  private String server;

  /** The port. */
  private int port;

  /** The user. */
  private String user;

  /** The password. */
  private String password;

  /** The ftp. */
  private FTPClient ftp;

  // constructor

  /**
   * Open.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  void open() throws IOException {
    ftp = new FTPClient();

    ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

    ftp.connect(server, port);
    int reply = ftp.getReplyCode();
    if (!FTPReply.isPositiveCompletion(reply)) {
      ftp.disconnect();
      throw new IOException("Exception in connecting to FTP Server");
    }

    ftp.login(user, password);
  }

  /**
   * Close.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  void close() throws IOException {
    ftp.disconnect();
  }
}
