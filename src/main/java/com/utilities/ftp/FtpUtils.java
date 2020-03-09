package com.utilities.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import com.utilities.files.FileUtilities;

public class FtpUtils {
    public boolean ftpToSwiftSrvr(String outDir, String fileName, String unixOutDir, String oFtpServer, String oFtpId,
	    String oFtpPwd) {
	boolean ftpFlag = false;
	FileInputStream fileStream = null;

	try {
	    String filePathWip = outDir + "/wip";
	    FTPClient ftp = new FTPClient();
	    ftp.connect(oFtpServer, 21);
	    int reply = ftp.getReplyCode();
	    if (!FTPReply.isPositiveCompletion(reply)) {
		try {
		    ftp.disconnect();
		} catch (Exception var34) {

		}

		throw new Exception("FTP server refused connection.");
	    }

	    if (!ftp.login(oFtpId, oFtpPwd)) {

		throw new Exception(
			"Unable to login to FTP server using username " + oFtpId + " and password " + oFtpPwd);
	    }

	    ftp.setFileType(2);
	    if (unixOutDir != null && unixOutDir.trim().length() > 0) {
		ftp.changeWorkingDirectory(unixOutDir);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
		    throw new Exception("Unable to change working directory to:" + unixOutDir);
		}
	    }

	    File f = new File(filePathWip + "/" + FileUtilities.validateFilePath(fileName));
	    fileStream = new FileInputStream(f);
	    boolean retValue = ftp.storeFile(f.getName(), fileStream);
	    if (!retValue) {

		if (fileStream != null) {
		    fileStream.close();
		}

		throw new Exception("Storing of remote file failed. ftp.storeFile() returned false.");
	    }

	    try {
		ftp.disconnect();
	    } catch (Exception var35) {

	    } finally {
		if (fileStream != null) {
		    fileStream.close();
		}

	    }
	} catch (Exception var37) {

	    ftpFlag = false;
	} finally {
	    try {
		if (fileStream != null) {
		    fileStream.close();
		}
	    } catch (IOException var33) {
	    }

	}

	return ftpFlag;
    }

    public boolean ftpsToSwiftSrvr(String outDir, String fileName, String unixOutDir, String oFtpServer, String oFtpId,
	    String oFtpPwd, int oFtpPort) {
	String protocol = "SSL";
	boolean ftpFlag = true;
	FileInputStream fileStream = null;

	try {
	    String filePathWip = outDir + "/wip";
	    FTPSClient ftps = new FTPSClient(protocol, true);
	    ftps.connect(oFtpServer, oFtpPort);
	    int reply = ftps.getReplyCode();
	    if (!FTPReply.isPositiveCompletion(reply)) {
		try {
		    ftps.disconnect();
		} catch (Exception var27) {
		    System.err.println("Unable to disconnect from FTP server after server refused connection. "
			    + var27.toString());
		}

		throw new Exception("FTP server refused connection.");
	    }

	    if (!ftps.login(oFtpId, oFtpPwd)) {
		throw new Exception(
			"Unable to login to FTP server using username " + oFtpId + " and password " + oFtpPwd);
	    }

	    ftps.enterLocalPassiveMode();
	    ftps.execPBSZ(0L);
	    ftps.execPROT("P");
	    ftps.setFileType(2);
	    if (unixOutDir != null && unixOutDir.trim().length() > 0) {
		ftps.changeWorkingDirectory(unixOutDir);
		reply = ftps.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
		    throw new Exception("Unable to change working directory to:" + unixOutDir);
		}
	    }

	    fileStream = new FileInputStream(filePathWip + File.separator + FileUtilities.validateFilePath(fileName));
	    boolean retValue = ftps.storeFile(fileName, fileStream);
	    if (!retValue) {
		throw new Exception("Storing of remote file failed. ftp.storeFile() returned false.");
	    }

	    try {
		ftps.disconnect();
	    } catch (Exception var28) {
		System.err.println("Unable to disconnect from FTP server. " + var28.toString());
	    }
	} catch (Exception var29) {
	    System.err.println("Unable to disconnect from FTP server. " + var29.toString());
	    ftpFlag = false;
	} finally {
	    try {
		if (fileStream != null) {
		    fileStream.close();
		}
	    } catch (IOException var26) {
	    }

	}

	return ftpFlag;
    }

    public static boolean moveFTPFile(String filename, String Source, String Destination, String oFtpServer,
	    String oFtpId, String oFtpPwd) {
	boolean status = false;
	boolean store = false;
	boolean delete = false;
	FTPClient ftp = loginFTP(oFtpServer, oFtpId, oFtpPwd);
	if (!ftp.isConnected()) {
	    return false;
	} else {
	    try {
		ftp.changeWorkingDirectory(Source);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
		    try {
			ftp.disconnect();
		    } catch (Exception var17) {
			System.err.println("Unable to disconnect from FTP server after server refused connection. "
				+ var17.toString());
		    }

		    throw new Exception("FTP server refused connection.");
		} else {
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    ftp.retrieveFile(filename, outputStream);
		    InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
		    ftp.setFileType(2);
		    ftp.changeWorkingDirectory(Destination);
		    ftp.enterLocalPassiveMode();
		    ftp.getReplyCode();
		    store = ftp.storeFile(filename, is);
		    ftp.getReplyCode();
		    ftp.getReplyString();
		    is.close();
		    if (store) {
			ftp.changeWorkingDirectory(Source);
			delete = ftp.deleteFile(filename);
		    }

		    if (store && delete) {
			status = true;
		    }

		    ftp.logout();
		    ftp.disconnect();
		    return status;
		}
	    } catch (Exception var18) {
		System.err.println("Unable to disconnect from FTP server. " + var18.toString());
		return false;
	    }
	}
    }

    public static FTPClient loginFTP(String oFtpServer, String oFtpId, String oFtpPwd) {
	FTPClient ftp = null;

	try {
	    ftp = new FTPClient();
	    ftp.connect(oFtpServer, 21);
	    int reply = ftp.getReplyCode();
	    if (!FTPReply.isPositiveCompletion(reply)) {
		try {
		    ftp.disconnect();
		} catch (Exception var6) {
		    System.err.println(
			    "Unable to disconnect from FTP server after server refused connection. " + var6.toString());
		}

		throw new Exception("FTP server refused connection.");
	    }

	    if (!ftp.login(oFtpId, oFtpPwd)) {
		throw new Exception(
			"Unable to login to FTP server using username " + oFtpId + " and password " + oFtpPwd);
	    }
	} catch (Exception var7) {
	    System.err.println("Unable to disconnect from FTP server. " + var7.toString());
	}

	return ftp;
    }

    public void downloadRemoteFile(String filePath, String saveAs) throws IOException {
	String ftpUrl = String.format("ftp://user:password@localhost:%d" + filePath);
	URLConnection urlConnection = new URL(ftpUrl).openConnection();
	InputStream inputStream = urlConnection.getInputStream();
	if (saveAs.equals(""))
	    saveAs = new File(filePath).getName();
	Files.copy(inputStream, new File(saveAs).toPath());
	inputStream.close();
    }

    public Collection<String> listFiles(FTPClient ftpClient, String path) throws IOException {
	FTPFile[] files = ftpClient.listFiles(path);
	return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
    }

    void downloadFile(FTPClient ftp, String source, String destination) throws IOException {
	FileOutputStream out = new FileOutputStream(destination);
	ftp.retrieveFile(source, out);
    }

    void putFileToPath(FTPClient ftp, File file, String path) throws IOException {
	ftp.storeFile(path, new FileInputStream(file));
    }

}
