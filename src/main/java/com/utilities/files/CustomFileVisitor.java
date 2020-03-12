/** */
package com.utilities.files;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomFileVisitor.
 *
 * @author hpsingh
 */
public class CustomFileVisitor extends SimpleFileVisitor<Path> {

  /** The source. */
  final Path source;

  /** The target. */
  final Path target;

  /**
   * Instantiates a new custom file visitor.
   *
   * @param source the source
   * @param target the target
   */
  public CustomFileVisitor(Path source, Path target) {
    this.source = source;
    this.target = target;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object,
   * java.nio.file.attribute.BasicFileAttributes)
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

    Path newDirectory = target.resolve(source.relativize(dir));
    try {
      Files.copy(dir, newDirectory);
    } catch (FileAlreadyExistsException ioException) {
      // log it and move
      return SKIP_SUBTREE; // skip processing
    }

    return FileVisitResult.CONTINUE;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object,
   * java.nio.file.attribute.BasicFileAttributes)
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

    Path newFile = target.resolve(source.relativize(file));

    try {
      Files.copy(file, newFile);
    } catch (IOException ioException) {
      // log it and move
    }

    return FileVisitResult.CONTINUE;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

    return FileVisitResult.CONTINUE;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    if (exc instanceof FileSystemLoopException) {
      // log error
    } else {
      // log error
    }
    return CONTINUE;
  }
}
