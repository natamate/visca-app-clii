//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca.cmd;

import pl.edu.agh.kis.visca.control.CommandChain;

import java.util.Optional;

public final class PanTiltAbsolutePosCmd extends Cmd implements CommandChain{
    private static final byte[] ptAbsolutPosCommandData = new byte[]{1, 6, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final int destinationAddress = 1;

    public PanTiltAbsolutePosCmd() {
    }

    public byte[] createCommandData() {
        byte[] cmdData = duplicatArray(ptAbsolutPosCommandData);
        cmdData[3] = 1;
        cmdData[5] = 0;
        cmdData[6] = 3;
        cmdData[7] = 7;
        cmdData[8] = 5;
        cmdData[9] = 0;
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed) {
        if (commandName.toLowerCase().equals(PanTiltAbsolutePosCmd.class.getSimpleName().toLowerCase())){
            ViscaCommand vCmd = new ViscaCommand();
            vCmd.commandData = createCommandData();
            vCmd.sourceAdr = 0;
            vCmd.destinationAdr = destinationAddress.isPresent() ? destinationAddress.get() :
                    PanTiltAbsolutePosCmd.destinationAddress;
            return Optional.of(vCmd);
        }else{
            return Optional.empty();
        }
    }
}
