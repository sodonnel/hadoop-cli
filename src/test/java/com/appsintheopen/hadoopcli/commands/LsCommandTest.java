package com.appsintheopen.hadoopcli.commands;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class LsCommandTest {
  
  TestExecutionEnvironment env;
  
  @Before
  public void setup() throws IOException {
    env = new TestExecutionEnvironment();
  }
  
  @Test
  public void testSwitchesExtracted() {
    LsCommand cmd = new LsCommand("ls -l -t -abc /somepath other/path");
    assertTrue(cmd.switches.containsKey('l'));
    assertTrue(cmd.switches.containsKey('t'));
    assertTrue(cmd.switches.containsKey('a'));
    assertTrue(cmd.switches.containsKey('b'));
    assertTrue(cmd.switches.containsKey('c'));
  }

  @Test
  public void testPathsExtracted() {
    LsCommand cmd = new LsCommand("ls -l -t -abc /somepath other/path");
    assertEquals(cmd.paths.get(0).path, "/somepath");
    assertEquals(cmd.paths.get(0).startPosition, 14);
    assertEquals(cmd.paths.get(0).endPosition, 22);

    assertEquals(cmd.paths.get(1).path, "other/path");
    assertEquals(cmd.paths.get(1).startPosition, 24);
    assertEquals(cmd.paths.get(1).endPosition, 33);
    
  }
  
  @Test
  public void testPathPositionsCorrectWithLeadingWhiteSpace() {
    LsCommand cmd = new LsCommand("  ls -l -t -abc /somepath other/path");
    assertEquals(cmd.paths.get(0).path, "/somepath");
    assertEquals(cmd.paths.get(0).startPosition, 16);
    assertEquals(cmd.paths.get(0).endPosition, 24);

    assertEquals(cmd.paths.get(1).path, "other/path");
    assertEquals(cmd.paths.get(1).startPosition, 26);
    assertEquals(cmd.paths.get(1).endPosition, 35);
  }
    
  @Test
  public void testListingCurrentDirectory() throws IOException {
    LsCommand cmd = new LsCommand("ls -l -t");
    assertEquals(cmd.execute(env), 0);
  }
  
  @Test
  public void testListingFileCurrentDirectory() throws IOException {
    LsCommand cmd = new LsCommand("ls -l -t .classpath");
    assertEquals(cmd.execute(env), 0);
  }
  
  @Test
  public void testListingDirectoryThatDoesNotExist() throws IOException {
    LsCommand cmd = new LsCommand("ls -l -t /dirnotexist");
    assertEquals(cmd.execute(env), 1);
  }

  @Test
  public void testListingRelativeDirectoryThatDoesNotExist() throws IOException {
    LsCommand cmd = new LsCommand("ls -l -t dirnotexist");
    assertEquals(cmd.execute(env), 1);
  }
  
}
