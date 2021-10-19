import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Math.pow;

/**
 * CalculatorServer
 * <p>
 * Description: Server
 *
 * @author Lakshmi Valaboju
 * @version July 21, 2021
 */

//Port Number: 8887

public class CalculatorServer {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8887);
        Socket socket = serverSocket.accept();
        PrintWriter writer = null;
        BufferedReader reader = null;

        boolean connect = true;

        while (connect) {
            double result = 0;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            String operation = reader.readLine();
            Double num1 = Double.parseDouble(reader.readLine());
            Double num2 = Double.parseDouble(reader.readLine());

            System.out.println(num1);

            if (operation != null) {
                if (operation.equals("+")) {
                    result = num1 + num2;
                } else if (operation.equals("-")) {
                    result = num1 - num2;
                } else if (operation.equals("/")) {
                    result = num1 / num2;
                } else if (operation.equals("*")) {
                    result = num1 * num2;
                } else if (operation.equals("%")) {
                    result = num1 % num2;
                } else if (operation.equals("^")) {
                    result = pow(num1, num2);
                }

                String strResult = String.format("%.2f", result);
                if (strResult.charAt(strResult.length() - 1) == '0') {
                    if (strResult.charAt(strResult.length() - 2) == '0') {
                        strResult = String.format("%.0f", result);
                    }
                }

                writer.write(strResult);
                writer.println();
                writer.flush();
            } else {
                break;
            }

        }
        writer.close();
        reader.close();


    }
}
