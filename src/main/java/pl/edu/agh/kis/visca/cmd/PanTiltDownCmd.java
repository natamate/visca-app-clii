//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca.cmd;

import pl.edu.agh.kis.visca.control.CommandChain;

import java.util.Optional;

public final class PanTiltDownCmd extends Cmd implements CommandChain{
    private static final byte[] ptUpCommandData = new byte[]{1, 6, 1, 0, 0, 3, 2};
    private static final int destinationAddress = 1;

    public PanTiltDownCmd() {
    }

    public byte[] createCommandData() {
        byte[] cmdData = duplicatArray(ptUpCommandData);
        cmdData[3] = 1;
        cmdData[4] = 2;
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed) {
        if (commandName.toLowerCase().equals(PanTiltDownCmd.class.getSimpleName().toLowerCase())){
            ViscaCommand vCmd = new ViscaCommand();
            byte[] cmdData = createCommandData();
            if (speed.isPresent()){
                cmdData[4] = speed.get();
            }
            vCmd.commandData = cmdData;
            vCmd.sourceAdr = 0;
            vCmd.destinationAdr = destinationAddress.isPresent() ? destinationAddress.get() :
                    PanTiltDownCmd.destinationAddress;
            return Optional.of(vCmd);
        }else{
            return Optional.empty();
        }
    }
}
