package com.myapp.utilities;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/** {@link Service} with hard-coded input data. */
@Component
public class ExampleService implements Service {

  /**
   * Reads next record from input.
   *
   * @return the message
   */
  public String getMessage() {
    return "Hello world!";
  }
}
