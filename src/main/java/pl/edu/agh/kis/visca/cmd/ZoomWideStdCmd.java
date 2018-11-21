//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca.cmd;

import pl.edu.agh.kis.visca.control.CommandChain;

import java.util.Optional;

public final class ZoomWideStdCmd extends Cmd implements CommandChain {
    private static final byte[] ptWideStdCommandData = new byte[]{1, 4, 7, 3};
    private final static int destinationAddress = 1;

    public ZoomWideStdCmd() {
    }

    public byte[] createCommandData() {
        byte[] cmdData = duplicatArray(ptWideStdCommandData);
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed) {
        if (commandName.toLowerCase().equals(ZoomWideStdCmd.class.getSimpleName().toLowerCase())){
            ViscaCommand vCmd = new ViscaCommand();
            vCmd.commandData = createCommandData();
            vCmd.sourceAdr = 0;
            vCmd.destinationAdr = destinationAddress.isPresent() ? destinationAddress.get() :
                    ZoomWideStdCmd.destinationAddress;
            return Optional.of(vCmd);
        }else{
            return Optional.empty();
        }
    }
}
