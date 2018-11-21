package pl.edu.agh.kis.visca.control;

import pl.edu.agh.kis.visca.cmd.ViscaCommand;

import java.util.Optional;

public interface CommandChain {
    Optional<ViscaCommand> getViscaCommand(String commandName, Optional<Byte> destinationAddress, Optional<Byte> speed);
}
