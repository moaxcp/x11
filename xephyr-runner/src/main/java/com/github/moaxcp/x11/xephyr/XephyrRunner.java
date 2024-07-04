package com.github.moaxcp.x11.xephyr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class XephyrRunner {
  private static TreeSet<Integer> usedDisplays = new TreeSet<>();
  private final boolean br;
  private final boolean ac;
  private final boolean noreset;
  private final Boolean iglx;
  private final boolean glamor;
  private final boolean softCursor;
  private final String screen;
  private final int withXTerm;
  private final List<Process> processes = new ArrayList<>();
  private final List<String> enableExtensions = new ArrayList<>();
  private final String display;

  XephyrRunner(boolean br, boolean ac, boolean noreset, Boolean iglx, boolean glamor, boolean softCursor, String screen, String display, int withXTerm) {
    this.br = br;
    this.ac = ac;
    this.noreset = noreset;
    this.iglx = iglx;
    this.glamor = glamor;
    this.softCursor = softCursor;
    this.screen = screen;
    this.display = display;
    this.withXTerm = withXTerm;
  }

  private static String getOpenDisplay() {
    if (usedDisplays.isEmpty()) {
      usedDisplays.add(1);
      return ":1";
    }

    int last = usedDisplays.last() + 1;
    usedDisplays.add(last);
    return ":" + last;
  }

  public static XephyrRunnerBuilder builder() {
    return new XephyrRunnerBuilder();
  }

  public void start() throws IOException {
    List<String> command = new ArrayList<>();
    command.add("Xephyr");
    if (br) {
      command.add("-br");
    }
    if (ac) {
      command.add("-ac");
    }
    if (noreset) {
      command.add("-noreset");
    }
    if (iglx != null) {
      if (iglx) {
        command.add("+iglx");
      } else {
        command.add("-iglx");
      }
    }
    if (glamor) {
      command.add("-glamor");
    }
    if (softCursor) {
      command.add("-softCursor");
    }
    if (screen != null) {
      command.add("-screen");
      command.add(screen);
    }
    for (String extension : enableExtensions) {
      command.add("+extension");
      command.add(extension);
    }

    command.add(display);

    processes.add(new ProcessBuilder(command.toArray(new String[0])).start());

    startXTerms();
  }

  private void startXTerms() throws IOException {
    for (int i = 0; i < withXTerm; i++) {
      processes.add(new ProcessBuilder("xterm", "-display", display).start());
    }
  }

  public void stop() throws InterruptedException {
    for (Process process : processes) {
      process.destroy();
      process.waitFor(10000, TimeUnit.SECONDS);
      process.destroyForcibly();
      process.waitFor();
    }
  }

  public String getDisplay() {
    return this.display;
  }

  public static class XephyrRunnerBuilder {
    private boolean br;
    private boolean ac;
    private boolean noreset;
    private Boolean iglx;
    private boolean glamor;
    private boolean softCursor;
    private String screen;
    private String display$value;
    private boolean display$set;
    private int withXTerm;

    XephyrRunnerBuilder() {
    }

    public XephyrRunnerBuilder br(boolean br) {
      this.br = br;
      return this;
    }

    public XephyrRunnerBuilder ac(boolean ac) {
      this.ac = ac;
      return this;
    }

    public XephyrRunnerBuilder noreset(boolean noreset) {
      this.noreset = noreset;
      return this;
    }

    public XephyrRunnerBuilder iglx(Boolean iglx) {
      this.iglx = iglx;
      return this;
    }

    public XephyrRunnerBuilder glamor(boolean glamor) {
      this.glamor = glamor;
      return this;
    }

    public XephyrRunnerBuilder softCursor(boolean softCursor) {
      this.softCursor = softCursor;
      return this;
    }

    public XephyrRunnerBuilder screen(String screen) {
      this.screen = screen;
      return this;
    }

    public XephyrRunnerBuilder display(String display) {
      this.display$value = display;
      this.display$set = true;
      return this;
    }

    public XephyrRunnerBuilder withXTerm(int withXTerm) {
      this.withXTerm = withXTerm;
      return this;
    }

    public XephyrRunner build() {
      String display$value = this.display$value;
      if (!this.display$set) {
        display$value = getOpenDisplay();
      }
      return new XephyrRunner(this.br, this.ac, this.noreset, this.iglx, this.glamor, this.softCursor, this.screen, display$value, this.withXTerm);
    }
  }
}
