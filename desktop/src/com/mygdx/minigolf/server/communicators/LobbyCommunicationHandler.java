package com.mygdx.minigolf.server.communicators;

import com.mygdx.minigolf.network.messages.Message.ClientLobbyCommand;
import com.mygdx.minigolf.network.messages.Message.ServerLobbyCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LobbyCommunicationHandler extends CommunicationHandler<ServerLobbyCommand, ClientLobbyCommand> {
    final ClientLobbyCommand exitCmd = ClientLobbyCommand.EXIT;
    public Thread runningThread;

    public LobbyCommunicationHandler(Socket socket, ObjectInputStream objIn, ObjectOutputStream objOut) throws IOException {
        super(socket, objIn, objOut);
    }

    @Override
    public void run() {
        runningThread = Thread.currentThread();
        super.run();
    }
}