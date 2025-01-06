import huffmancode.HuffmanCode;
import org.apache.commons.cli.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        final int lastArgumentIndex = args.length-1;
        final String INFORMATION_MESSAGE=
                "\n\nUse \"-e\" to encode file\n" +
                "Use \"-d\" to decode file\n"+
                "Use \"-l\" to specify length of coding sequences\n"+
                "\n"+
                "Use \"-i\" to specify entry_file (required)\n"+
                "Use \"-o\" to specify result_file\n";
        File plikWejsciowy=null;
        File plikWyjsciowy=null;
        Options options = new Options();

        options.addOption("e", "encode", false, "Input file will be encoded");
        options.addOption("d", "decode", false, "Input file will be decoded");
        options.addOption("i", "input", true, "Path to input file");
        options.addOption("o", "output", true, "Path to result file");
        options.addOption("l", "length", true, "Length of sequence in tree");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (!cmd.hasOption("input")) {
                System.out.println("Input file has not been passed!"+INFORMATION_MESSAGE);
                return;
            }else{
                plikWejsciowy=new File(cmd.getOptionValue("input"));
            }
            if (cmd.hasOption("output")) {
                plikWyjsciowy= new File(cmd.getOptionValue("output"));
            }
            if ((cmd.hasOption("encode") && cmd.hasOption("decode")) || (!cmd.hasOption("encode") && !cmd.hasOption("decode"))) {
                System.out.println("Choose either encoding or decoding!"+INFORMATION_MESSAGE);
                return;
            }
            if(cmd.hasOption("encode")){
                if(plikWyjsciowy==null){
                    HuffmanCode.encode(plikWejsciowy);
                }else{
                    HuffmanCode.encode(plikWejsciowy,plikWyjsciowy);
                }
                return;
            }
            if(cmd.hasOption("decode")){
                if (plikWyjsciowy==null){
                    HuffmanCode.decode(plikWejsciowy);
                }else{
                    HuffmanCode.decode(plikWejsciowy,plikWyjsciowy);
                }

            }
        } catch (ParseException e) {
            System.err.println("Parsing arguments error: " + e.getMessage());
        }

    }
}

