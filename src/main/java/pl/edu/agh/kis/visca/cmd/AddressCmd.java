//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.edu.agh.kis.visca.cmd;

import pl.edu.agh.kis.visca.control.CommandChain;

import java.util.Optional;

public final class AddressCmd extends Cmd implements CommandChain{
    private static final byte[] adrCommmandData = new byte[]{48, 1};
    private static final int destinationAddress = 8;

    public AddressCmd() {
    }

    public byte[] createCommandData(){
        byte[] cmdData = duplicatArray(adrCommmandData);
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }

    @Override
    public Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed) {
        if (commandName.toLowerCase().equals(AddressCmd.class.getSimpleName().toLowerCase())){
            ViscaCommand vCmd = new ViscaCommand();
            vCmd.commandData = createCommandData();
            vCmd.sourceAdr = 0;
            vCmd.destinationAdr = destinationAddress.isPresent() ? destinationAddress.get() :
                    AddressCmd.destinationAddress;
            return Optional.of(vCmd);
        }else{
            return Optional.empty();
        }
    }
}
