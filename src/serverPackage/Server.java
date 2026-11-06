package serverPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Objet.Operation;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Je suis un serveur en attente de la connexion d'un client");
        Socket socket = serverSocket.accept();
        System.out.println("Un client est connecte");
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Operation operation = (Operation) in.readObject();
        int operande1 = operation.getOperande1();
        char operateur = operation.getOperateur();
        int operande2 = operation.getOperande2();
        System.out.println("Objet recu : " + operation);
        String resultat;
        switch (operateur) {
            case '+': resultat = String.valueOf(operande1 + operande2); break;
            case '-': resultat = String.valueOf(operande1 - operande2); break;
            case '*': resultat = String.valueOf(operande1 * operande2); break;
            case '/':
                resultat = (operande2 == 0) ? "Erreur : division par zero"
                        : String.valueOf((double) operande1 / operande2);
                break;
            default:  resultat = "Erreur : operateur inconnu";
        }
        out.println(resultat);
        System.out.println("Resultat : " + operation + " = " + resultat);
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
        System.out.println("Connexion terminee");
    }
}
