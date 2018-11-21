//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca.cmd;

import pl.edu.agh.kis.visca.control.CommandChain;

import java.util.Optional;

public final class PanTiltLeftCmd extends Cmd implements CommandChain{
    private static final byte[] ptLeftCommandData = new byte[]{1, 6, 1, 0, 0, 1, 3};
    private static final int destinationAddress = 1;

    public PanTiltLeftCmd() {
    }

    public byte[] createCommandData() {
        byte[] cmdData = duplicatArray(ptLeftCommandData);
        cmdData[3] = 8;
        cmdData[4] = 1;
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed) {
        if (commandName.toLowerCase().equals(PanTiltLeftCmd.class.getSimpleName().toLowerCase())){
            ViscaCommand vCmd = new ViscaCommand();
            byte[] cmdData = createCommandData();
            if (speed.isPresent()){
                cmdData[4] = speed.get();
            }
            vCmd.commandData = cmdData;
            vCmd.sourceAdr = 0;
            vCmd.destinationAdr = destinationAddress.isPresent() ? destinationAddress.get() :
                    PanTiltLeftCmd.destinationAddress;
            return Optional.of(vCmd);
        }else{
            return Optional.empty();
        }
    }
}
