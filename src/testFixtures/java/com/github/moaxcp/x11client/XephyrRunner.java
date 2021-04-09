package com.github.moaxcp.x11client;

import lombok.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Builder
public class XephyrRunner {
  private static TreeSet<Integer> usedDisplays = new TreeSet<>();
  private final boolean br;
  private final boolean ac;
  private final boolean noreset;
  private final Boolean iglx;
  private final boolean glamor;
  private final boolean softCursor;
  private final String screen;
  @NonNull
  @Singular
  private final List<String> enableExtensions = new ArrayList<>();
  @Getter
  @Builder.Default
  private final String display = getOpenDisplay();

  private static String getOpenDisplay() {
    if(usedDisplays.isEmpty()) {
      usedDisplays.add(1);
      return ":1";
    }

    int last = usedDisplays.last() + 1;
    usedDisplays.add(last);
    return ":" + last;
  }

  private final int withXTerm;
  @NonNull
  @Builder.Default
  private List<Process> processes = new ArrayList<>();

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
