package com.utilities.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.transaction.file.FileResourceManager;
import org.apache.commons.transaction.file.ResourceManagerException;
import org.apache.commons.transaction.util.LoggerFacade;

import com.utilities.logging.LoggerFacadeImpl;

public class FileUtilities {

    /**
     * 1. PrintWriter is used to write formatted text; 2. FileOutputStream to write
     * binary data; 3. DataOutputStream to write primitive data types; 4.
     * RandomAccessFile to write to a specific position; 5. FileChannel to write
     * faster in larger files
     */

    public boolean createFileJdk6(String filePath) throws IOException {
	boolean success = false;
	filePath = FileUtilities.transformPath(filePath);
	File newFile = new File(filePath);
	if (newFile.exists()) {
	    success = true;
	} else {
	    success = newFile.createNewFile();
	}
	return success;
    }

    public void createFileJdk7(String filePath) throws IOException {
	Path newFilePath = Paths.get(filePath);
	try {
	    Files.createFile(newFilePath);
	} catch (FileAlreadyExistsException e) {
	}
    }

    public static String transformPath(String filePath) {
	// ^(.*[^/|^\\])([/\\]+)$
	String patternString = "^(.*[^/|^\\\\])([/\\\\]+)$";
	Pattern pattern = Pattern.compile(patternString);
	Matcher matcher = pattern.matcher(filePath);
	boolean matches = matcher.matches();
	if (matches) {
	    filePath = matcher.group(1);
	}
	patternString = "([/\\\\]+)";
	filePath = filePath.replaceAll(patternString, File.separator.replace("\\", "\\\\"));
	return filePath;
    }

    public static void renameFile(File oldFile, File newFile) {
	try {
	    copyFileUsingFileChannels(oldFile, newFile);
	    oldFile.delete();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public static void backUpFile(File oldFile, File newFile) {
	try {
	    copyFileUsingFileChannels(oldFile, newFile);
	    PrintWriter writer = new PrintWriter(oldFile);
	    writer.print("");
	    writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
	FileChannel inputChannel = null;
	FileChannel outputChannel = null;
	FileInputStream fileInputStream = new FileInputStream(source);
	FileOutputStream fileOutputStream = new FileOutputStream(dest);
	try {
	    inputChannel = fileInputStream.getChannel();
	    outputChannel = fileOutputStream.getChannel();
	    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    try {
		fileInputStream.close();
		fileOutputStream.close();
		inputChannel.close();
		outputChannel.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public static String readFile(String path, Charset encoding) throws IOException {
	byte[] encoded = Files.readAllBytes(Paths.get(path));
	return new String(encoded, encoding);
    }

    public static StringBuilder readUsingBufferedReader(String filePath) {
	StringBuilder builder = new StringBuilder();
	try {
	    Path path = Paths.get(filePath);
	    BufferedReader reader = Files.newBufferedReader(path);
	    String currentLine = reader.readLine();
	    while (currentLine != null) {
		builder.append(currentLine);
		builder.append("\n");
		currentLine = reader.readLine();
	    }
	} catch (Exception e) {
	    System.out.println(e);
	}
	return builder;
    }

    public static StringBuilder readUsingFileReader(String filePath) {
	StringBuilder builder = new StringBuilder();
	try {
	    FileReader fr = new FileReader(new File(filePath));
	    int i;
	    while ((i = fr.read()) != -1)
		builder.append((char) i);
	    fr.close();
	}

	catch (Exception e) {
	    System.out.println(e);
	} finally {
	}
	return builder;
    }

    public static String readUsingScanner(String filePath) {
	StringBuilder strb = new StringBuilder();
	try {
	    File f = new File(filePath);
	    Scanner sc = new Scanner(f);
	    while (sc.hasNextLine())
		strb.append(sc.nextLine());
	    sc.close();
	}

	catch (Exception e) {
	    System.out.println(e);
	} finally {
	}
	return strb.toString();
    }

    public static String readFileAsString(String filePath) throws Exception {
	String data = "";
	data = new String(Files.readAllBytes(Paths.get(filePath)));
	return data;
    }

    public static List<String> readFileInList(String fileName) {
	List<String> lines = Collections.emptyList();
	try {
	    lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	} catch (IOException e) {
	    // do something
	    e.printStackTrace();
	}
	return lines;
    }

    public void readUTFEncodedFile(String filePath) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
	String currentLine = reader.readLine();
	while (currentLine != null) {
	    // do something
	}
	reader.close();
    }

    public static byte[] readFileIntoBytes(String filePath) throws IOException {
	byte[] byteArray = null;
	try {
	    byteArray = Files.readAllBytes(Paths.get(filePath));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return byteArray;
    }

    public static byte[] readFileToByteArray(File file) {
	FileInputStream fis = null;
	// Creating a byte array using the length of the file
	// file.length returns long which is cast to int
	byte[] bArray = new byte[(int) file.length()];
	try {
	    fis = new FileInputStream(file);
	    fis.read(bArray);
	    fis.close();

	} catch (IOException ioExp) {
	    ioExp.printStackTrace();
	}
	return bArray;
    }

    public int readFromPosition(String filename, long position) throws IOException {
	int result = 0;
	RandomAccessFile reader = new RandomAccessFile(filename, "r");
	reader.seek(position);
	result = reader.readInt();
	reader.close();
	return result;
    }

    public static void writeUsingFileOutputStream(byte[] strToBytes, String filePath) throws IOException {
	FileOutputStream outputStream = new FileOutputStream(filePath);
	outputStream.write(strToBytes);
	outputStream.close();
    }

    public static void writeUsingDataOutputStream(String content, String filePath) {
	try {
	    FileOutputStream fos = new FileOutputStream(filePath);
	    DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
	    outStream.writeUTF(content);
	    outStream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void writeUsingFileChannel(String content, String filePath) throws IOException {
	RandomAccessFile stream = new RandomAccessFile(filePath, "rw");
	FileChannel channel = stream.getChannel();
	byte[] strBytes = content.getBytes();
	ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
	buffer.put(strBytes);
	buffer.flip();
	channel.write(buffer);
	stream.close();
	channel.close();
    }

    public static void writeUsingFileWriter(String content, String filePath) throws IOException {
	FileWriter fw = new FileWriter(new File(filePath));
	fw.write(content);
	fw.close();
    }

    public static void writeToPosition(String filename, byte[] bytes, long position) throws IOException {
	RandomAccessFile writer = new RandomAccessFile(filename, "rw");
	writer.seek(position);
	writer.write(bytes);
	writer.close();
    }

    public static void lockFile(String filePath) throws IOException {
	RandomAccessFile stream = new RandomAccessFile(filePath, "rw");
	FileChannel channel = stream.getChannel();

	FileLock lock = null;
	try {
	    lock = channel.tryLock();
	} catch (final OverlappingFileLockException e) {
	    stream.close();
	    channel.close();
	}
	lock.release();

	stream.close();
	channel.close();
    }

    public static void writeStringUsingBufferedWriter(String str, String filePath) throws IOException {
	BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
	writer.write(str);
	writer.close();
    }

    public static void writeToFile(String str, String filePath) throws IOException {
	if (Files.exists(Paths.get(filePath))) {
	    Files.write(Paths.get(filePath), str.getBytes(), StandardOpenOption.APPEND);
	} else {
	    writeUsingFileWriter(str, filePath);
	}
    }

    /** JDK 7+. */
    public byte[] readSmallBinaryFile(String fileName) throws IOException {
	Path path = Paths.get(fileName);
	return Files.readAllBytes(path);
    }

    /** JDK 7+. */
    public void writeSmallBinaryFile(byte[] bytes, String fileName) throws IOException {
	Path path = Paths.get(fileName);
	Files.write(path, bytes); // creates, overwrites
    }

    /** JDK < 7 */
    /** Read the given binary file, and return its contents as a byte array. */
    byte[] read(String inputFileName) {
	File file = new File(inputFileName);
	byte[] result = new byte[(int) file.length()];
	try {
	    InputStream input = null;
	    try {
		int totalBytesRead = 0;
		input = new BufferedInputStream(new FileInputStream(file));
		while (totalBytesRead < result.length) {
		    int bytesRemaining = result.length - totalBytesRead;
		    // input.read() returns -1, 0, or more :
		    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
		    if (bytesRead > 0) {
			totalBytesRead = totalBytesRead + bytesRead;
		    }
		}
		/*
		 * the above style is a bit tricky: it places bytes into the 'result' array;
		 * 'result' is an output parameter; the while loop usually has a single
		 * iteration only.
		 */
	    } finally {
		input.close();
	    }
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return result;
    }

    /** JDK < 7 */
    /**
     * Write a byte array to the given file. Writing binary data is significantly
     * simpler than reading it.
     */
    void write(byte[] input, String outputFileName) {
	try {
	    OutputStream output = null;
	    try {
		output = new BufferedOutputStream(new FileOutputStream(outputFileName));
		output.write(input);
	    } finally {
		output.close();
	    }
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    /** JDK < 7 */
    /** Read the given binary file, and return its contents as a byte array. */
    byte[] readAlternateImpl(String inputFileName) {
	File file = new File(inputFileName);
	byte[] result = null;
	try {
	    InputStream input = new BufferedInputStream(new FileInputStream(file));
	    result = readAndClose(input);
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	}
	return result;
    }

    /** JDK < 7 */
    /**
     * Read an input stream, and return it as a byte array. Sometimes the source of
     * bytes is an input stream instead of a file. This implementation closes aInput
     * after it's read.
     */
    byte[] readAndClose(InputStream input) {
	// carries the data from input to output :
	byte[] bucket = new byte[32 * 1024];
	ByteArrayOutputStream result = null;
	try {
	    try {
		// Use buffering? No. Buffering avoids costly access to disk or network;
		// buffering to an in-memory stream makes no sense.
		result = new ByteArrayOutputStream(bucket.length);
		int bytesRead = 0;
		while (bytesRead != -1) {
		    // aInput.read() returns -1, 0, or more :
		    bytesRead = input.read(bucket);
		    if (bytesRead > 0) {
			result.write(bucket, 0, bytesRead);
		    }
		}
	    } finally {
		input.close();
		// result.close(); this is a no-operation for ByteArrayOutputStream
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return result.toByteArray();
    }

    public byte[] getBytesFromFile(String fileName) throws IOException {
	InputStream is = null;
	byte[] bytes = new byte[0];

	Object var7;
	try {
	    File file = new File(fileName);
	    if (!file.isFile()) {
		Object var14 = null;
		return (byte[]) var14;
	    }

	    is = new FileInputStream(file);
	    long length = file.length();
	    if (length <= 2147483647L) {
		bytes = new byte[(int) length];
		int offset = 0;

		int numRead;
		for (; offset < bytes.length
			&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
		}

		if (offset < bytes.length) {
		    throw new IOException("Could not completely read file " + file.getName());
		}

		return bytes;
	    }

	    var7 = null;
	} catch (Exception var12) {
	    return bytes;
	} finally {
	    if (is != null) {
		is.close();
	    }

	}

	return (byte[]) var7;
    }

    public static String validateFilePath(String input) {
	if (input == null) {
	    return null;
	} else if ("".equalsIgnoreCase(input)) {
	    return "";
	} else if (!input.contains("..") && !input.contains("&") && !input.contains("%") && !input.contains("(")
		&& !input.contains(")")) {
	    StringWriter writer = new StringWriter((int) ((double) input.length() * 1.5D));
	    int length = input.length();

	    for (int ctr = 0; ctr < length; ++ctr) {
		char token = input.charAt(ctr);
		writer.write(token);
	    }

	    return writer.toString();
	} else {
	    return "";
	}
    }

    public static void moveFile(String filePathWip, String filePathPro, String WipFileName, String ProFileName) {
	File fProc = new File(filePathPro, validateFilePath(ProFileName));
	OutputStream f0 = null;
	BufferedReader br = null;
	PrintWriter pw = null;
	FileReader fileReader = null;
	FileWriter fileWriter = null;
	if (fProc.exists()) {
	    fProc.delete();
	}

	try {
	    f0 = new FileOutputStream(fProc);
	    File fWip = new File(filePathWip, validateFilePath(WipFileName));
	    fileReader = new FileReader(fWip);
	    br = new BufferedReader(fileReader);
	    fileWriter = new FileWriter(fProc);
	    pw = new PrintWriter(fileWriter);
	    int c;
	    while ((c = br.read()) != -1) {
		pw.print((char) c);
	    }

	    br.close();
	    pw.close();
	    f0.close();
	    fProc.setReadOnly();
	    fWip.delete();
	} catch (FileNotFoundException var46) {
	} catch (IOException var47) {
	} finally {
	    try {
		if (f0 != null) {
		    f0.close();
		}
	    } catch (Exception var45) {
	    }

	    try {
		if (br != null) {
		    br.close();
		}
	    } catch (Exception var44) {
	    }

	    try {
		if (pw != null) {
		    pw.close();
		}
	    } catch (Exception var43) {
	    }

	    try {
		if (fileReader != null) {
		    fileReader.close();
		}
	    } catch (Exception var42) {
	    }

	    try {
		if (fileWriter != null) {
		    fileWriter.close();
		}
	    } catch (Exception var41) {
	    }

	}

    }

    public boolean moveFileTransacted(String filePathWip, String filePathPro, String WipFileName, String ProFileName) {
	boolean retVal = true;
	FileResourceManager frm = null;
	FileResourceManager frm1 = null;
	String txId = null;
	String txId1 = null;
	LoggerFacade logger = (LoggerFacade) new LoggerFacadeImpl();
	frm = new FileResourceManager(filePathWip, filePathWip, false, logger);
	frm1 = new FileResourceManager(filePathPro, filePathPro, false, logger);

	try {
	    frm.start();
	    frm1.start();
	    txId = frm.generatedUniqueTxId();
	    txId1 = frm1.generatedUniqueTxId();
	    frm.startTransaction(txId);
	    frm1.startTransaction(txId1);

	    byte[] bt;
	    try {
		bt = this.getBytesFromFile(filePathWip + File.separator + WipFileName);
		if (bt == null) {
		    frm.rollbackTransaction(txId);
		    frm1.rollbackTransaction(txId1);
		    return false;
		}
	    } catch (Exception var13) {
		frm.rollbackTransaction(txId);
		frm1.rollbackTransaction(txId1);
		return false;
	    }

	    OutputStream os = frm1.writeResource(txId1, ProFileName);
	    os.write(bt);
	    frm.deleteResource(txId, WipFileName);
	    frm.prepareTransaction(txId);
	    frm1.prepareTransaction(txId1);
	    frm.commitTransaction(txId);
	    frm1.commitTransaction(txId1);
	} catch (ResourceManagerException var14) {
	    retVal = false;

	    try {
		frm.rollbackTransaction(txId);
		frm1.rollbackTransaction(txId1);
	    } catch (Exception var12) {
	    }
	} catch (Exception var15) {
	    retVal = false;
	}

	return retVal;
    }

    public static String readTextFile(String dir, String fileName) {
	StringBuffer out = new StringBuffer();
	String s = null;
	BufferedReader br = null;
	FileReader fr = null;

	try {
	    fr = new FileReader(dir + "/" + fileName);
	    br = new BufferedReader(fr);

	    while ((s = br.readLine()) != null) {
		out.append(s + '\n');
	    }

	    out.deleteCharAt(out.length() - 1);
	    fr.close();
	    br.close();
	} catch (Exception var15) {
	} finally {
	    try {
		if (br != null) {
		    br.close();
		}

		if (fr != null) {
		    fr.close();
		}
	    } catch (Exception var14) {
	    }

	}

	return out.toString();
    }

    public void copyFilesRecursively(String srcPath, String destPath) {

	Path sourceLocation = Paths.get(srcPath);
	Path targetLocation = Paths.get(destPath);

	CustomFileVisitor fileVisitor = new CustomFileVisitor(sourceLocation, targetLocation);
	// You can specify your own FileVisitOption
	try {
	    Files.walkFileTree(sourceLocation, fileVisitor);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
