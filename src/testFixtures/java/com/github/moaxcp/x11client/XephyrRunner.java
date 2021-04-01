package com.github.moaxcp.x11client;

import lombok.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class XephyrRunner {
  private final boolean br;
  private final boolean ac;
  private final boolean noreset;
  private final Boolean iglx;
  private final boolean glamor;
  private final boolean softCursor;
  private final String screen;
  private final List<String> enableExtensions;
  private final String display;

  private final int withXTerm;
  private List<Process> processes = new ArrayList<>();

  @Builder
  public XephyrRunner(boolean br, boolean ac, boolean noreset, Boolean iglx, boolean glamor, boolean softCursor, String screen, @Singular List<String> enableExtensions, String display, int withXTerm) {
    this.br = br;
    this.ac = ac;
    this.noreset = noreset;
    this.iglx = iglx;
    this.glamor = glamor;
    this.softCursor = softCursor;
    this.screen = screen;
    this.enableExtensions = enableExtensions;
    this.display = display;
    this.withXTerm = withXTerm;
  }

  public void start() throws IOException {
    List<String> command = new ArrayList<>();
    command.add("Xephyr");
    if(br) {
      command.add("-br");
    }
    if(ac) {
      command.add("-ac");
    }
    if(noreset) {
      command.add("-noreset");
    }
    if(iglx != null) {
      if(iglx) {
        command.add("+iglx");
      } else {
        command.add("-iglx");
      }
    }
    if(glamor) {
      command.add("-glamor");
    }
    if(softCursor) {
      command.add("-softCursor");
    }
    if(screen != null) {
      command.add("-screen");
      command.add(screen);
    }
    for(String extension : enableExtensions) {
      command.add("+extension");
      command.add(extension);
    }

    command.add(display);

    processes.add(new ProcessBuilder(command.toArray(new String[0])).start());

    startXTerms();
  }

  private void startXTerms() throws IOException {
    for(int i = 0; i < withXTerm; i++) {
      processes.add(new ProcessBuilder("xterm", "-display", display).start());
    }
  }

  public void stop() throws InterruptedException {
    for(Process process : processes) {
      process.destroy();
      process.waitFor(10000, TimeUnit.SECONDS);
      process.destroyForcibly();
      process.waitFor();
    }
  }
}
