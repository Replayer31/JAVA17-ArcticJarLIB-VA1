package ArcticJarLIB.Voxis;

import ArcticJarLIB.App.App;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * VOXSystem Command interface
 * @param <R> What command returns?; You can write "Void" here
 */
public interface VOXCommand<R> {

    /**
     * Is VOXSystem Command need cheats to run()?
     */
    boolean isCheat();

    /**
     * What permission source runner need to run() this command?
     */
    VOXSource getPermission();

    /**
     * What is name of your command?
     */
    String getName();

    /**
     * Describe your command/Return name of your command/What if convert your command to string?
     */
    String toString(VOXCommandArgument[] args);


    /**
     * VOXSystem Command run() method; What does the command do?
     *
     * @param source   Permission of source
     * @param registry Registry of commands
     * @param byWho    Who is run this command
     * @param VOX      VOXSystem object
     * @param App      App object
     * @param args     VOXSystem Command arguments, <b>DO ONLY "OBJECT"</b>
     */
    R runCommand(VOXSource source, LinkedHashMap<String, Object> registry, String[] packages, Object byWho, VOXSystem VOX, App App, VOXCommandArgument[] args)
            throws RuntimeException, IOException;

    /**
     * Register command in LinkedHashMap
     * @param name Name of this command in registry
     * @param registry Your registry LinkedHashMap
     */
    default void register(String name, LinkedHashMap<String, Object> registry) { registry.put(name, this); }

    /**
     * Register command in LinkedHashMap
     * @param registry Your registry LinkedHashMap
     */
    default void register(LinkedHashMap<String, Object> registry) { registry.put(getName(), this); }

}
