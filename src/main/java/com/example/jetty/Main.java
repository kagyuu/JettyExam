package com.example.jetty;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Startup class for Jetty Web Server.
 *
 * @author atsushihondoh
 */
@Slf4j
public class Main {

    private static final String ARG_PORT = "p";
    private static final String ARG_HELP = "h";
    private static final String ARG_SHUTDOWN_TOKEN = "t";
    
    private static final String DEFAULT_PORT = "8080";
    private static final String DEFAULT_SHUTDOWN_TOKEN = "admin";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ---------- 1. Definitions of command arguments ----------
        Options options = new Options();

        // Command Option "p" (port number)
        Option srcOption = Option.builder(ARG_PORT)
                .longOpt("port")
                .hasArg()
                .required(false)
                .argName("PORT")
                .desc(String.format("default : %s", DEFAULT_PORT))
                .build();

        options.addOption(srcOption);

        // Command Option "t" (port number)
        Option tokenOption = Option.builder(ARG_SHUTDOWN_TOKEN)
                .longOpt("token")
                .hasArg()
                .required(false)
                .argName("SHUTDOWN TOKEN")
                .desc(String.format("default : %s", DEFAULT_SHUTDOWN_TOKEN))
                .build();

        options.addOption(tokenOption);

        
        // Command Option "h" (help)
        Option helpOption = Option.builder(ARG_HELP)
                .longOpt("help")
                .argName("help")
                .desc("Show this help")
                .build();

        options.addOption(helpOption);

        // ---------- 2. Check Command Argument ----------
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException ex) {
            // If there are some errors in the argument, show help and end.
            HelpFormatter help = new HelpFormatter();
            help.printHelp(Main.class.getName(), options, true);
            return;
        }

        if (cmd.hasOption(ARG_HELP)) {
            // show help and end.
            HelpFormatter help = new HelpFormatter();
            help.printHelp(Main.class.getName(), options, true);
            return;
        }

        // ---------- 3.Read the command argument ----------        
        MyServer myserver = new MyServer();
        myserver.setPort(Integer.parseInt(cmd.getOptionValue(ARG_PORT, DEFAULT_PORT)));
        myserver.setShutdownToken(cmd.getOptionValue(ARG_SHUTDOWN_TOKEN, DEFAULT_SHUTDOWN_TOKEN));
        
        Thread th = new Thread(myserver);
        th.start();
        
        Thread sh = new Thread(()->{
            myserver.stop();
        });
            
        Runtime.getRuntime().addShutdownHook(sh);
    }
}
