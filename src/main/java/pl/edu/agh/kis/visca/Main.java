//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca;

import jssc.SerialPort;
import jssc.SerialPortException;
import pl.edu.agh.kis.visca.ViscaResponseReader.TimeoutException;
import pl.edu.agh.kis.visca.cmd.AddressCmd;
import pl.edu.agh.kis.visca.cmd.ClearAllCmd;
import pl.edu.agh.kis.visca.cmd.GetPanTiltMaxSpeedCmd;
import pl.edu.agh.kis.visca.cmd.PanTiltAbsolutePosCmd;
import pl.edu.agh.kis.visca.cmd.PanTiltDownCmd;
import pl.edu.agh.kis.visca.cmd.PanTiltHomeCmd;
import pl.edu.agh.kis.visca.cmd.PanTiltLeftCmd;
import pl.edu.agh.kis.visca.cmd.PanTiltRightCmd;
import pl.edu.agh.kis.visca.cmd.PanTiltUpCmd;
import pl.edu.agh.kis.visca.cmd.ViscaCommand;
import pl.edu.agh.kis.visca.cmd.ZoomTeleStdCmd;
import pl.edu.agh.kis.visca.cmd.ZoomWideStdCmd;
import pl.edu.agh.kis.visca.control.CommandChain;
import pl.edu.agh.kis.visca.control.DataStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static final String SPLIT_REGEX = " ";

    public Main() {
    }

    public static void runUserCommands(String userInput, SerialPort serialPort) {
        String[] tokens = userInput.split(";");
        byte[] response;

        try {
            for (String token : tokens) {
                if (token.startsWith("Wait ")) {
                    String firstNumber = token.replaceAll("^\\D*(\\d+).*", "$1");
                    int sleepFor = Integer.parseInt(firstNumber);
                    Thread.sleep((long) (sleepFor));
                    continue;
                } else {

                    if (token.contains(SPLIT_REGEX)) {
                        String[] params = token.split(SPLIT_REGEX);
                        Optional<Byte> destAddress = validateByteParam(params[1]);
                        Optional<Byte> speed = Optional.empty();
                        if (params.length > 2){
                            speed = validateByteParam(params[2]);
                        }
                        serialPort.writeBytes(getCommandData(params[0] + "Cmd", destAddress, speed));
                    }
                    else{
                        serialPort.writeBytes(getCommandData(token + "Cmd" , Optional.empty(), Optional.empty()));
                    }
                }
                try {
                    response = ViscaResponseReader.readResponse(serialPort);
                    System.out.println(">" + returnSerialPortStatus(response));
                } catch (TimeoutException var11) {
                    System.out.println("! TIMEOUT exception");
                }
            }
        } catch (SerialPortException var18) {
            System.out.println(var18);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Optional<Byte> validateByteParam(String param) {
        if (param.isEmpty()){
            return Optional.empty();
        }
        else{
            return convertToByte(param);
        }
    }

    private static Map<String, String> macros = new HashMap<>();


    public static void main(String[] args) throws SerialPortException {
        String commName = args[0];
        SerialPort serialPort = new SerialPort(commName);

        serialPort.openPort();
        serialPort.setParams(9600, 8, 1, 0);

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter commands: ");

            try {
                String commands = reader.readLine();
                String optionalMacro = macros.get(commands);

                if (commands.startsWith("macro:")) {
                    String macroTail = commands.substring(6);
                    String macroName = macroTail.substring(macroTail.indexOf("(") + 1, macroTail.indexOf(")"));
                    String macro = macroTail.substring(macroTail.indexOf(")") + 1, macroTail.length());

                    macros.put(macroName, macro);
                } else if (optionalMacro != null) {
                    runUserCommands(optionalMacro, serialPort);
                } else {
                    runUserCommands(commands, serialPort);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Optional<Byte> convertToByte(String number){
        Byte convertNumber;
        try {
            convertNumber = Byte.valueOf(number);
        }catch(NumberFormatException e){
            return Optional.empty();
        }
        return Optional.of(convertNumber);
    }

    private static void sleep(int timeSec) {
        try {
            Thread.sleep((long) (timeSec * 1000));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }

    private static String byteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        byte[] var5 = bytes;
        int var4 = bytes.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
    }

    private static byte[] getCommandData(String command, Optional<Byte> destAddress, Optional<Byte> speed){
        List<CommandChain> commandChain = new LinkedList<>(Arrays.asList(new ZoomWideStdCmd(),
                new ZoomTeleStdCmd(), new PanTiltUpCmd(), new PanTiltRightCmd(), new PanTiltLeftCmd(), new PanTiltDownCmd(),
                new PanTiltHomeCmd(), new PanTiltAbsolutePosCmd(), new GetPanTiltMaxSpeedCmd(), new ClearAllCmd(),
                new AddressCmd()));

        Optional<ViscaCommand> vscCommand;
        for (CommandChain cmdChain: commandChain){
            vscCommand = cmdChain.getViscaCommand(command, destAddress, speed);
            if (vscCommand.isPresent()){
                return vscCommand.get().getCommandData();
            }
        }
        return new byte[0];
    }

    private static DataStatus returnSerialPortStatus(byte[] commandData){
        if (commandData.length > 5){
            if (commandData[4] == 6){
                return DataStatus.ERROR;
            }
            else{
                return DataStatus.OK;
            }
        }
        return DataStatus.ERROR;
    }

}






