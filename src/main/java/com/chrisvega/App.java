package com.chrisvega;

import picocli.CommandLine;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "calc", mixinStandardHelpOptions = true, version = "0.0.1",
        description = "Simple calculator")
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--file"}, description = "File to read expressions from", required = false)
    File file;

    @Override
    public Integer call() throws Exception {
        if (file != null) {
            Parser p = new Parser(new IdLexer(Files.newBufferedReader(file.toPath())));
            System.out.println(p.parse().value);
        } else {
            Scanner scanner = new Scanner(System.in);
            String input = "";
            do {
                System.out.print("Enter an expression > ");
                input = scanner.nextLine();
                if (input.equals("exit")) {
                    break;
                }
                Parser p = new Parser(new IdLexer(new StringReader(input)));
                System.out.println(p.parse().value);
            } while (true);
        }
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
