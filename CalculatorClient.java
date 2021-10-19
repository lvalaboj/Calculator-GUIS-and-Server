import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

/**
 * CalculatorClient
 * <p>
 * Description: Client
 *
 * @author Lakshmi Valaboju
 * @version July 21, 2021
 */

//Port Number: 8887

public class CalculatorClient {
    public static void main(String[] args) throws IOException {
        int port = 0;
        String portString = null;
        String hostName = null;
        Socket socket = null;
        String eq = null;
        String operation = null;
        ArrayList<Double> num = new ArrayList<>();
        int valid = -1;
        BufferedReader reader = null;
        PrintWriter writer = null;
        SocketAddress sA = null;

        JOptionPane.showMessageDialog(null, "Welcome to Calculator!", "Calculator",
                JOptionPane.INFORMATION_MESSAGE);

        int check = 0;

        boolean loop = true;

        while (loop) {
            do {
                hostName = JOptionPane.showInputDialog(null, "Enter your hostname", "Calculator",
                        JOptionPane.QUESTION_MESSAGE);
                if ((hostName == null) || (hostName.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Enter a valid hostname", "Calculator",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while ((hostName == null) || (hostName.isEmpty()));

            do {
                portString = JOptionPane.showInputDialog(null, "Enter port", "Calculator",
                        JOptionPane.QUESTION_MESSAGE);
                try {
                    port = Integer.parseInt(portString);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Enter valid port", "Calculator",
                            JOptionPane.ERROR_MESSAGE);
                    portString = null;
                    continue;
                }
            } while ((portString == null) || (portString.isEmpty()));

            int count = 0;

            try {
                sA = new InetSocketAddress(hostName, port);
                socket = new Socket();
                socket.connect(sA, 2000);

            } catch (ConnectException e) {
                JOptionPane.showMessageDialog(null, "Connection is not established", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                count = 1;
                loop = false;
            } catch (UnknownHostException e) {
                JOptionPane.showMessageDialog(null, "Connection is not established", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                count = 1;
                loop = false;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Connection is not established", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                count = 1;
                loop = false;
            } catch (NoRouteToHostException e) {
                JOptionPane.showMessageDialog(null, "Connection is not established", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                count = 1;
                loop = false;
            } catch (SocketTimeoutException e) {
                JOptionPane.showMessageDialog(null, "Connection is not established", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                count = 1;
                loop = false;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Connection is not established", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                count = 1;
                loop = false;
            }

            int round = 0;

            while (count == 0) {
                if (round == 0) {
                    JOptionPane.showMessageDialog(null, "Connection established", "Calculator",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                do {
                    valid = 0;
                    eq = JOptionPane.showInputDialog(null, "Enter an equation", "Calculator",
                            JOptionPane.QUESTION_MESSAGE);
                    if (eq == null || eq.isBlank()) {
                        valid = 1;
                    } else {
                        if (eq.charAt(eq.length() - 1) == ' ') {
                            valid = 1;
                        }

                        String[] equation = eq.split(" ");
                        if (equation.length == 3) {
                            if (equation[1].equals("+") || equation[1].equals("-") ||
                                    equation[1].equals("*") || equation[1].equals("/") ||
                                    equation[1].equals("%") || equation[1].equals("^")) {
                                operation = equation[1];
                            } else {
                                valid = 1;
                            }
                            try {
                                double op1;
                                double op2;
                                if (valid != 1) {
                                    if (equation[0].substring(0, 1).equals("-")) {
                                        equation[0] = equation[0].substring(1);
                                        op1 = Double.parseDouble(equation[0]);
                                        op1 = (-1) * op1;
                                    } else {
                                        op1 = Double.parseDouble(equation[0]);
                                    }
                                    if (equation[2].substring(0, 1).equals("-")) {
                                        equation[2] = equation[2].substring(1);
                                        op2 = Double.parseDouble(equation[2]);
                                        op2 = (-1) * op2;
                                    } else {
                                        op2 = Double.parseDouble(equation[2]);
                                    }
                                    num.add(op1);
                                    num.add(op2);
                                }
                            } catch (NumberFormatException e) {
                                valid = 1;
                            }
                        } else {
                            valid = 1;
                        }
                    }

                    if (valid == 1) {
                        JOptionPane.showMessageDialog(null, "Enter valid equation", "Calculator",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } while (valid == 1);

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());

                writer.write(operation + "\n");
                writer.flush();
                writer.write(num.get(0).toString());
                writer.println();
                writer.write(num.get(1).toString());
                writer.println();
                writer.flush();


                String result = reader.readLine();
                JOptionPane.showMessageDialog(null, "Your Result: " + result, "Calculator",
                        JOptionPane.INFORMATION_MESSAGE);

                count = JOptionPane.showConfirmDialog(null, "Do you want to enter a new equation?", "Calculator",
                        JOptionPane.YES_NO_OPTION);
                if (count == 1) {
                    JOptionPane.showMessageDialog(null, "Have a nice day!", "Calculator",
                            JOptionPane.INFORMATION_MESSAGE);
                    loop = false;
                    break;
                }

                round++;
                num.clear();
            }
        }
        if (writer != null) {
            writer.close();
            reader.close();
        }
    }
}