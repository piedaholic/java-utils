package com.utilities.web;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

// TODO: Auto-generated Javadoc
/** The Class JavascriptUtils. */
public class JavascriptUtils {

  /**
   * Compress js.
   *
   * @param jsString the js string
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void compressJs(String jsString, Writer writer) throws IOException {
    StringReader reader = null;
    JavaScriptCompressor compressor = null;

    try {
      reader = new StringReader(jsString);
      compressor = new JavaScriptCompressor(reader, null);
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception var7) {
        }
      }

      compressor.compress(
          writer, new Integer(-1), Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException var6) {
        }
      }
    } catch (IOException var8) {

      throw var8;
    } catch (Exception var9) {

    } catch (IllegalAccessError var10) {
      writer.write(jsString);
    }
  }
}
