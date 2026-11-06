package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Objet.Operation;

public class Client {
    static int lireEntier(Scanner sc, String message) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.println("Entree invalide : un nombre entier est attendu.");
            sc.next();
            System.out.print(message);
        }
        return sc.nextInt();
    }
    static char lireOperateur(Scanner sc) {
        System.out.print("Operateur (+, -, *, /) : ");
        String s = sc.next();
        while (s.length() != 1 || "+-*/".indexOf(s.charAt(0)) < 0) {
            System.out.println("Operateur invalide (exemple refuse : **).");
            System.out.print("Operateur (+, -, *, /) : ");
            s = sc.next();
        }
        return s.charAt(0);
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int operande1 = lireEntier(sc, "Premier operande : ");
        char operateur = lireOperateur(sc);
        int operande2 = lireEntier(sc, "Deuxieme operande : ");
        while (operateur == '/' && operande2 == 0) {
            System.out.println("Division par zero interdite.");
            operande2 = lireEntier(sc, "Deuxieme operande : ");
        }
        Socket socket = new Socket("localhost", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.writeObject(new Operation(operande1, operateur, operande2));
        out.flush();
        System.out.println("Resultat recu : " + in.readLine());
        in.close();
        out.close();
        socket.close();
        sc.close();
    }
}
