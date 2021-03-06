// HTTPThreadPoolQSleepServer.java
// usage: java HTTPThreadPoolQSleepServer -config <config_file_name>

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;
import java.util.concurrent.*;

public class HTTPThreadPoolQSleepServer extends HTTPServer {
  public static void main(String[] args) throws Exception {
    if (!init(args)) return;

    // Open listen socket.
    ServerSocket server = new ServerSocket(config.getPort(), 200);

    // Create the queue.
    LinkedBlockingQueue<Socket> queue = new LinkedBlockingQueue<Socket>();

    // Run threads.
    runThreads(queue, true);

    while (true) {
      Socket conn = server.accept();

      synchronized (queue) {
        queue.add(conn);
        queue.notifyAll();
      }
    }
  }
}