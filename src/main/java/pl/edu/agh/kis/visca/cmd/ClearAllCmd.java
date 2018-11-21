//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca.cmd;

import pl.edu.agh.kis.visca.control.CommandChain;

import java.util.Optional;

public final class ClearAllCmd extends Cmd implements CommandChain{
    private static final byte[] clearAllCommandData = new byte[]{1, 0, 1};
    private static final int destinationAddress = 8;

    public ClearAllCmd() {
    }

    public byte[] createCommandData() {
        byte[] cmdData = duplicatArray(clearAllCommandData);
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed) {
        if (commandName.toLowerCase().equals(ClearAllCmd.class.getSimpleName().toLowerCase())){
            ViscaCommand vCmd = new ViscaCommand();
            vCmd.commandData = createCommandData();
            vCmd.sourceAdr = 0;
            vCmd.destinationAdr = destinationAddress.isPresent() ? destinationAddress.get() :
                    ClearAllCmd.destinationAddress;
            return Optional.of(vCmd);
        }else{
            return Optional.empty();
        }
    }
}
