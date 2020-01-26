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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ---------- 1. Definitions of command arguments ----------

        // コマンド引数全体を定義するオブジェクト
        Options options = new Options();

        // コマンド引数一つ分の定義(ソースファイル指定)
        Option srcOption = Option.builder("p")
                .longOpt("port")
                .hasArg()
                .required(false)
                .argName("PORT")
                .desc("the PORT number of the Web Server")
                .build();

        options.addOption(srcOption);

//        // コマンド引数一つ分の定義(結果ファイル指定)
//        Option destOption = Option.builder("d")
//                .longOpt("dest")
//                .hasArg()
//                .required()
//                .argName("結果")
//                .desc("結果ファイルを指定します")
//                .build();
//
//        options.addOption(destOption);
//
        // コマンド引数一つ分の定義(ヘルプ)
        Option helpOption = Option.builder("h")
                .longOpt("help")
                .argName("help")
                .desc("Show this help")
                .build();

        options.addOption(helpOption);

        // ---------- 2.コマンド引数のチェック ----------
        CommandLineParser parser = new DefaultParser();

        // 解析
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException ex) {
            // 必須オプションがない場合はヘルプを表示して終了
            HelpFormatter help = new HelpFormatter();
            help.printHelp(Main.class.getName(), options, true);
            return;
        }

        if (cmd.hasOption("h")) {
            // ヘルプオプションが指定されていた場合には
            // ヘルプを表示して終了
            HelpFormatter help = new HelpFormatter();
            help.printHelp(Main.class.getName(), options, true);
            return;
        }

        // ---------- 3.コマンド引数の解釈 ----------        
        MyServer myserver = new MyServer();
        // TODO: settings for server
        Thread th = new Thread(myserver);
        th.start();
        
        Thread sh = new Thread(()->{
            myserver.stop();
        });
            
        Runtime.getRuntime().addShutdownHook(sh);
    }
}
